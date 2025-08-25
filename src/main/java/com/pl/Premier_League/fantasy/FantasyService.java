package com.pl.Premier_League.fantasy;

import com.pl.Premier_League.player.Player;
import com.pl.Premier_League.ui.PriceUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class FantasyService {
    private final FantasySquadRepo squadRepo;

    public FantasyService(FantasySquadRepo squadRepo) {
        this.squadRepo = squadRepo;
    }

    @Transactional
    public FantasySquad saveSquad(String username, List<Player> players) {
        FantasySquad s = new FantasySquad();
        s.setUsername(username);
        BigDecimal total = players.stream().map(PriceUtil::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        s.setTotalCost(total);
        s.setCreatedAt(Instant.now());
        for (Player p : players) {
            FantasyPick pick = new FantasyPick();
            pick.setSquad(s);
            pick.setPlayer(p);
            pick.setPosition(p.getPos());
            pick.setPrice(PriceUtil.price(p));
            s.getPicks().add(pick);
        }
        return squadRepo.save(s);
    }
}
