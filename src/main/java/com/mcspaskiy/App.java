package com.mcspaskiy;

import java.util.ArrayList;
import java.util.List;

/**
 * создать имитацию подключения 10 игроков к cерверу Дота 2
 * через Callable and Feature. Сервер стартует (запись в логах),
 * когда все 10 игроков законнектились в течение 15 секунд
 * (время коннекта рандомное для каждого игрока от 5 до 20 секунд.
 * Если хотя бы один игрок потратил на коннект
 * более 15 секунд - сервер в логах пишет что "Connection lost.
 * This game is safe to leave"
 */
public class App {


    public static void main(String[] args) {
        Server server = new Server();

        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            players.add(new Player());
        }

        server.addPlayers(players);

        server.startServer();
    }
}
