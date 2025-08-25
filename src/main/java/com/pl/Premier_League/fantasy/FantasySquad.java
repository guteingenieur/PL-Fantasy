package com.pl.Premier_League.fantasy;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fantasy_squad")
public class FantasySquad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private BigDecimal totalCost;

    private Instant createdAt;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<FantasyPick> picks = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<FantasyPick> getPicks() {
        return picks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setPicks(Set<FantasyPick> picks) {
        this.picks = picks;
    }
}
