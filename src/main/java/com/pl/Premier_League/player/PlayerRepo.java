package com.pl.Premier_League.player;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlayerRepo extends JpaRepository<Player, String> {
    void deleteByName(String playerName);
    Optional<Player> findByName(String name);
}
