package com.pl.Premier_League.fantasy;

import com.pl.Premier_League.player.Player;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "fantasy_pick")
public class FantasyPick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "squad_id")
    private FantasySquad squad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_name", referencedColumnName = "player_name")
    private Player player;

    private String position;

    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public FantasySquad getSquad() {
        return squad;
    }

    public Player getPlayer() {
        return player;
    }

    public String getPosition() {
        return position;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSquad(FantasySquad squad) {
        this.squad = squad;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
