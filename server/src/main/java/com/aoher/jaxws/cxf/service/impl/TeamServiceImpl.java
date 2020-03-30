package com.aoher.jaxws.cxf.service.impl;

import com.aoher.jaxws.cxf.model.Player;
import com.aoher.jaxws.cxf.service.TeamService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service(value = "teamService")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class TeamServiceImpl implements TeamService {

    private static final Logger log = Logger.getLogger(TeamServiceImpl.class);

    private final List<Player> team;

    public TeamServiceImpl() {
        this.team = new LinkedList<>();
        team.add(new Player(1, "David De Gea", 22));
        team.add(new Player(2, "Rafael", 22));
        team.add(new Player(3, "Patrice Evra", 31));
        team.add(new Player(4, "Phil Jones", 21));
        team.add(new Player(5, "Rio Ferdinand", 34));
        team.add(new Player(7, "Antonio Valencia", 27));
        team.add(new Player(8, "Anderson", 24));
        team.add(new Player(10, "Wayne Rooney", 27));
    }

    @Override
    public List<Player> getTeam() {
        log.info("team requested");
        return team;
    }

    @Override
    public List<Player> getPlayers(int... numbers) {
        List<Player> result = new LinkedList<>();

        if (numbers != null) {
            Player player;
            for (int number : numbers) {
                player = findById(number);
                if (player != null) {
                    result.add(player);
                }
            }
        }
        log.info("returning " + result.size() + " players");
        return result;
    }

    @Override
    public boolean updatePlayerByNumber(int number, Player player) {
        log.info("updating player " + number);

        Player playerOld = findById(number);
        if (playerOld != null) {
            playerOld.setAge(player.getAge());
            playerOld.setNumber(player.getNumber());
            playerOld.setName(player.getName());
            return true;
        } else {
            log.warn("player " + number + " not found");
            return false;
        }
    }

    @Override
    public boolean deletePlayer(int number) {
        log.info("deleting player " + number);

        Player player = findById(number);
        if (player != null) {
            team.remove(player);
            return true;
        } else {
            log.warn("player " + number + " not found");
            return false;
        }
    }

    @Override
    public void foo() {
        log.info("Hello world!!!");
    }

    private Player findById(int id) {
        return team.stream().filter(p -> p.getNumber() == id).findFirst().orElse(null);
    }
}
