package com.aoher.client;

import com.aoher.model.Player;
import com.aoher.service.TeamService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;

public class Main {

    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));

        // client
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(TeamService.class);
        jaxWsProxyFactoryBean.setAddress(properties.getProperty("endpoint"));

        TeamService teamServiceClient = (TeamService) jaxWsProxyFactoryBean.create();

        // test
        logger.info("getTeam");
        List<Player> team = teamServiceClient.getTeam();
        team.stream()
                .map(player -> "       " + player.getNumber() + " : " + player.getName() + " (" + player.getAge() + ")")
                .forEach(logger::info);

        logger.info("\n updatePlayerByNumber");
        teamServiceClient.updatePlayerByNumber(1, new Player(1, "Anders Lindegaard", 28));

        logger.info("\n deletePlayer");
        teamServiceClient.deletePlayer(6);

        logger.info("\n getPlayers");
        team = teamServiceClient.getPlayers(1, 3, 6);
        team.stream()
                .map(player -> "       " + player.getNumber() + " : " + player.getName() + " (" + player.getAge() + ")")
                .forEach(logger::info);
    }
}
