package com.pl.Premier_League.ui;

import com.pl.Premier_League.fantasy.FantasyService;
import com.pl.Premier_League.player.Player;
import com.pl.Premier_League.player.PlayerRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pl.Premier_League.ui.PriceUtil.*;

@PageTitle("Fantasy")
@Route(value = "fantasy", layout = MainLayout.class)
public class FantasyView extends VerticalLayout {
    private static final BigDecimal BUDGET = BigDecimal.valueOf(100_000_000);

    private final Span budgetSpan = new Span();

    public FantasyView(PlayerRepo repo, SelectionService selection, FantasyService fantasyService) {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        add(new H2("Your XI on the pitch"));

        var chosenNames = selection.getSelectedNames();
        var chosen = repo.findAll().stream()
                .filter(p -> chosenNames.contains(p.getName()))
                .sorted(Comparator.comparing(Player::getPos).thenComparing(Player::getName))
                .collect(Collectors.toList());

        updateBudget(chosen);

        var gk = chosen.stream().filter(p -> eq(p.getPos(),"GK")).limit(1).toList();
        var df = chosen.stream().filter(p -> eq(p.getPos(),"DF")).limit(4).toList();
        var mf = chosen.stream().filter(p -> eq(p.getPos(),"MF")).limit(4).toList();
        var fw = chosen.stream().filter(p -> eq(p.getPos(),"FW")).limit(2).toList();

        var pitch = new VerticalLayout();
        pitch.setSizeFull();
        pitch.getStyle()
                .set("background", "linear-gradient(#075e54,#128c7e)")
                .set("border-radius", "16px")
                .set("padding", "16px")
                .set("box-shadow", "0 2px 8px rgb(0 0 0 / 15%)");

        pitch.add(row("GK", gk));
        pitch.add(row("DF", df));
        pitch.add(row("MF", mf));
        pitch.add(row("FW", fw));
        pitch.getChildren().forEach(c -> pitch.setFlexGrow(1, c));

        TextField username = new TextField("Username");
        Button validateBtn = new Button("Validate squad", e -> {
            var err = validate(chosen);
            Notification.show(err == null ? "Squad OK" : err, 3000, Notification.Position.MIDDLE);
        });
        Button saveBtn = new Button("Save squad", e -> {
            var err = validate(chosen);
            if (err != null) {
                Notification.show(err, 3000, Notification.Position.MIDDLE);
                return;
            }
            if (username.getValue() == null || username.getValue().isBlank()) {
                Notification.show("Enter username", 2000, Notification.Position.MIDDLE);
                return;
            }
            var saved = fantasyService.saveSquad(username.getValue().trim(), chosen);
            Notification.show("Saved squad #" + saved.getId(), 3000, Notification.Position.MIDDLE);
        });

        add(budgetSpan, pitch, new Div(username, validateBtn, saveBtn));
        expand(pitch);
    }

    private Div row(String label, List<Player> players){
        var lane = new Div();
        lane.getStyle()
                .set("display","grid")
                .set("grid-template-columns", label.equals("GK") ? "1fr" : "repeat(4, 1fr)")
                .set("gap","12px")
                .set("margin","12px 0");
        int need = switch (label) { case "GK" -> 1; case "FW" -> 2; default -> 4; };
        for (int i=0; i<need; i++){
            Player p = i < players.size() ? players.get(i) : null;
            lane.add(card(label, p));
        }
        return lane;
    }

    private Div card(String label, Player p){
        var box = new Div();
        box.getStyle()
                .set("background","rgba(255,255,255,0.9)")
                .set("border-radius","12px")
                .set("padding","10px")
                .set("text-align","center");
        if (p == null){
            box.add(new Span("Empty " + label));
            return box;
        }
        var name = new Span(p.getName());
        name.getStyle().set("font-weight","600");
        var info = new Span(p.getTeam()+" · "+p.getPos());
        var priceTag = new Span(fmtM(price(p)));
        priceTag.getStyle().set("font-weight","600");
        var stack = new VerticalLayout(name, info, priceTag);
        stack.setPadding(false);
        stack.setSpacing(false);
        stack.setAlignItems(Alignment.CENTER);
        box.add(stack);
        return box;
    }

    private void updateBudget(List<Player> chosen){
        var spent = chosen.stream().map(PriceUtil::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        var remain = BUDGET.subtract(spent);
        budgetSpan.setText("Budget €100M | Spent " + fmtM(spent) + " | Remaining " + fmtM(remain));
    }

    private String validate(List<Player> chosen){
        Map<String, Long> c = chosen.stream().collect(Collectors.groupingBy(Player::getPos, Collectors.counting()));
        if (c.getOrDefault("GK",0L)!=1) return "Exactly 1 GK required";
        if (c.getOrDefault("DF",0L)!=4) return "Exactly 4 DF required";
        if (c.getOrDefault("MF",0L)!=4) return "Exactly 4 MF required";
        if (c.getOrDefault("FW",0L)!=2) return "Exactly 2 FW required";
        var spent = chosen.stream().map(PriceUtil::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (spent.compareTo(BUDGET) > 0) return "Budget exceeded";
        if (chosen.size() != 11) return "Pick 11 players";
        return null;
    }

    private static boolean eq(Object a, String b){
        return a != null && a.toString().equalsIgnoreCase(b);
    }
}
