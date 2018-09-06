package com.cfs.main;

import java.util.ArrayList;
import java.util.HashMap;

public class Tester implements Runnable {

    static volatile ArrayList<HashMap<String, Integer>> multipleTestNeighbors = new ArrayList<>();

    public void run() {
        Runner runner = new Runner(false);
        while (true) {
            if (!runner.run()) {
                break;
            }
        }
        multipleTestNeighbors.add(runner.getDataMap());
    }

}
