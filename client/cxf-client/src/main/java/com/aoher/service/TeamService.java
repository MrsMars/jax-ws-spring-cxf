package com.aoher.service;

import com.aoher.model.Player;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface TeamService {

    List<Player> getTeam();
    List<Player> getPlayers(int... numbers);
    boolean updatePlayerByNumber(int number, Player player);
    boolean deletePlayer(int number);
}
