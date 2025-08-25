package com.pl.Premier_League.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepo playerRepo;

    @Autowired
    public PlayerService(PlayerRepo playerRepo){
        this.playerRepo = playerRepo;
    }

    public List<Player> getPlayers(){
        return playerRepo.findAll();
    }

    public List<Player> getPlayerFromTeam(String teamName){
        return playerRepo.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByName(String searchText){
        return playerRepo.findAll().stream()
                .filter(player -> player.getName().toLowerCase().equals(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPos(String searchText){
        return playerRepo.findAll().stream()
                .filter(player -> player.getPos().toLowerCase().equals(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String searchText){
        return playerRepo.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().equals(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByTeamAndPosition(String team, String position){
        return playerRepo.findAll().stream()
                .filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos()))
                .collect(Collectors.toList());
    }

    public Player addPlayer (Player player){
        playerRepo.save(player);
        return player;
    }

    public Player updatePlayer (Player updatedPlayer){
        Optional<Player> existingPlayer = playerRepo.findByName(updatedPlayer.getName());

        if (existingPlayer.isPresent()){
            Player playerToUpdate = existingPlayer.get();

            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeam(updatedPlayer.getTeam());
            playerToUpdate.setPos(updatedPlayer.getPos());
            playerToUpdate.setNation(updatedPlayer.getNation());

            playerRepo.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }

    @Transactional
    public void deletePlayer(String playerName){
        playerRepo.deleteByName(playerName);
    }
}
