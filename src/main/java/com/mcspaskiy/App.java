package com.mcspaskiy;

import java.util.concurrent.TimeUnit;

public class App {
    /**
     * 1000 голды всего. изеначально 5 майнеров майнят по 3 голды в секунду. есть бараки,которые
     * продуцируют еще одного рабочего каждые 10 секунд. в логах выводить каждую секунду
     * статус всех майнеров (имя и сколько голды уже намайнил) и сколько осталось в шахте.
     * программа полностью завершается,когда золота в шахте не остается
     */

    public static void main(String[] args) {
        //Common resource
        GoldMine goldMine = new GoldMine();

        //Init
        Barrack barrack = new Barrack(goldMine);

        for (int i = 0; i < 5; i++) {
            barrack.createWorker();
        }

        barrack.start();

        //Logging
        while (true) {
            System.out.println("---------------------------");
            System.out.println("In mine " + goldMine.getGoldInStore() + " gold");
            System.out.println("---------------------------");
            for (Worker worker : barrack.getWorkers()) {
                System.out.println(worker.getName() + " mined " + worker.getGold() + " gold");
            }
            if (goldMine.getGoldInStore() == 0) {
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("---------------------------");
        System.out.println("!!! Mission completed !!!");
        barrack.stop();
    }
}
