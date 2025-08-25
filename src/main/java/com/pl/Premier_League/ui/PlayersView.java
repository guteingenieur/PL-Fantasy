package com.pl.Premier_League.ui;

import com.pl.Premier_League.player.Player;
import com.pl.Premier_League.player.PlayerRepo;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.*;
import java.util.stream.Collectors;

import static com.pl.Premier_League.ui.PriceUtil.fmtM;
import static com.pl.Premier_League.ui.PriceUtil.price;

@PageTitle("Players")
@Route(value = "players", layout = MainLayout.class)
public class PlayersView extends VerticalLayout {

    private final Grid<Player> grid = new Grid<>(Player.class, false);
    private final TextField nameFilter = new TextField("Name");
    private final TextField teamFilter = new TextField("Team");
    private final ComboBox<String> posFilter = new ComboBox<>("Position");
    private final TextField nationFilter = new TextField("Nation");
    private final Button clearBtn = new Button("Clear");

    private final MultiSelectComboBox<Player> pickBox = new MultiSelectComboBox<>("Pick players");

    private final List<String> forcedTeamAliases = new ArrayList<>();
    private final List<Player> allPlayers;
    private final ListDataProvider<Player> dataProvider;

    public PlayersView(PlayerRepo repo, SelectionService selection) {
        setSizeFull(); setPadding(true); setSpacing(true);

        allPlayers = repo.findAll();
        dataProvider = new ListDataProvider<>(allPlayers);

        posFilter.setItems("GK","DF","MF","FW");
        nameFilter.setPlaceholder("e.g. Saka");
        teamFilter.setPlaceholder("e.g. Arsenal");
        nationFilter.setPlaceholder("e.g. ENG");
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        teamFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nationFilter.setValueChangeMode(ValueChangeMode.EAGER);

        nameFilter.addValueChangeListener(e -> applyFilters());
        teamFilter.addValueChangeListener(e -> {
            if (e.isFromClient()) {
                forcedTeamAliases.clear();
            }
            applyFilters();
        });
        nationFilter.addValueChangeListener(e -> applyFilters());
        posFilter.addValueChangeListener(e -> applyFilters());

        clearBtn.addClickListener(e -> {
            nameFilter.clear();
            teamFilter.clear();
            nationFilter.clear();
            posFilter.clear();
            forcedTeamAliases.clear();
            applyFilters();
        });

        var filters = new HorizontalLayout(nameFilter, teamFilter, posFilter, nationFilter, clearBtn);
        filters.setWidthFull();
        add(filters);

        FlexLayout logos = new FlexLayout();
        logos.getStyle().set("gap", "12px");
        logos.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        TeamAssets.TEAM_LOGOS.forEach((slug, url) -> {
            Image img = new Image(url, slug);
            img.setWidth(48, Unit.PIXELS);
            img.setHeight(48, Unit.PIXELS);
            img.getStyle().set("border-radius","8px").set("border","1px solid var(--lumo-contrast-20pct)");
            Button b = new Button(img, e -> {
                forcedTeamAliases.clear();
                forcedTeamAliases.addAll(TeamNames.aliasesFor(slug));
                nameFilter.clear();
                nationFilter.clear();
                posFilter.clear();
                teamFilter.setValue(TeamNames.primary(slug));
                applyFilters();
            });
            b.getElement().setProperty("title", slug);
            logos.add(b);
        });
        add(new Span("Filter by club:"), logos);

        pickBox.setPlaceholder("Type to search players…");
        pickBox.setClearButtonVisible(true);
        pickBox.setItemLabelGenerator(p -> p.getName()+" ("+p.getTeam()+", "+p.getPos()+", "+fmtM(price(p))+")");
        pickBox.setItems(query -> {
            String q = normalize(query.getFilter().orElse(""));
            return allPlayers.stream().filter(p ->
                    normalize(p.getName()).contains(q) ||
                            normalize(p.getTeam()).contains(q) ||
                            normalize(p.getNation()).contains(q) ||
                            normalize(p.getPos()).contains(q)
            );
        });
        pickBox.addValueChangeListener(e -> {
            selection.clear();
            selection.addAll(e.getValue().stream().map(Player::getName).collect(Collectors.toList()));
        });

        Button addSelectedFromGrid = new Button("Add selected rows → Fantasy", e -> {
            var names = grid.getSelectedItems().stream().map(Player::getName).toList();
            selection.addAll(names);
            var chosen = allPlayers.stream().filter(p -> selection.getSelectedNames().contains(p.getName())).toList();
            pickBox.setValue(new LinkedHashSet<>(chosen));
            Notification.show("Added "+names.size()+" player(s).");
        });

        var pickerRow = new HorizontalLayout(pickBox, addSelectedFromGrid);
        pickerRow.setWidthFull();
        add(pickerRow);

        grid.addColumn(Player::getName).setHeader("Name").setAutoWidth(true).setSortable(true);
        grid.addColumn(Player::getTeam).setHeader("Team").setAutoWidth(true).setSortable(true);
        grid.addColumn(Player::getNation).setHeader("Nation");
        grid.addColumn(Player::getPos).setHeader("Pos");
        grid.addColumn(Player::getAge).setHeader("Age");
        grid.addColumn(p -> fmtM(price(p))).setHeader("Price");
        grid.addColumn(Player::getMp).setHeader("MP");
        grid.addColumn(Player::getStarts).setHeader("Starts");
        grid.addColumn(Player::getMin).setHeader("Min");
        grid.addColumn(Player::getGls).setHeader("Gls");
        grid.addColumn(Player::getAst).setHeader("Ast");
        grid.addColumn(Player::getPk).setHeader("PK");
        grid.addColumn(Player::getCrdy).setHeader("Yel");
        grid.addColumn(Player::getCrdr).setHeader("Red");
        grid.addColumn(Player::getXg).setHeader("xG");
        grid.addColumn(Player::getXag).setHeader("xAG");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setSizeFull();
        grid.setItems(dataProvider);
        add(grid); expand(grid);

        applyFilters();
    }

    private void applyFilters() {
        String nameQ   = normalize(nameFilter.getValue());
        String teamQ   = normalize(teamFilter.getValue());
        String posQ    = normalize(posFilter.getValue());
        String nationQ = normalize(nationFilter.getValue());

        List<String> aliasNorms = forcedTeamAliases.stream().map(this::normalize).toList();

        dataProvider.clearFilters();
        dataProvider.setFilter(p -> {
            String nName   = normalize(p.getName());
            String nTeam   = normalize(p.getTeam());
            String nPos    = normalize(p.getPos());
            String nNation = normalize(p.getNation());

            boolean okName = nameQ.isEmpty() || nName.contains(nameQ);

            boolean okTeam;
            if (!aliasNorms.isEmpty()) {
                okTeam = aliasNorms.stream().anyMatch(alias -> nTeam.contains(alias) || alias.contains(nTeam));
            } else {
                okTeam = teamQ.isEmpty() || nTeam.contains(teamQ);
            }

            boolean okPos    = posQ.isEmpty() || nPos.equals(posQ) || nPos.contains(posQ);
            boolean okNation = nationQ.isEmpty() || nNation.contains(nationQ);

            return okName && okTeam && okPos && okNation;
        });
        dataProvider.refreshAll();
    }

    private String normalize(Object v) {
        String s = v == null ? "" : v.toString();
        s = s.toLowerCase(Locale.ROOT);
        s = s.replace("&", "and");
        s = s.replaceAll("\\s+", " ");
        s = s.replaceAll("[-_]", " ");
        s = s.replaceAll("\\bthe\\b", " ");
        s = s.replaceAll("\\s+", "");
        return s.trim();
    }
}
