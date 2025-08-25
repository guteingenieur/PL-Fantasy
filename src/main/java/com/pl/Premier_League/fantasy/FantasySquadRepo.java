package com.pl.Premier_League.fantasy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FantasySquadRepo extends JpaRepository<FantasySquad, Long> {
    Optional<FantasySquad> findTopByUsernameOrderByCreatedAtDesc(String username);
}
