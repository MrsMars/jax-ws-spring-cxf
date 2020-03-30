package com.aoher.jaxws.cxf.service;

import com.aoher.jaxws.cxf.model.Player;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface TeamService {

    List<Player> getTeam();
    List<Player> getPlayers(int... numbers);
    boolean updatePlayerByNumber(int number, Player player);
    boolean deletePlayer(int number);
    void foo();
}
