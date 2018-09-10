package com.cfs.random;

import com.cfs.runner.Runner;

import java.util.ArrayList;
import java.util.HashMap;

public class Tester implements Runnable {

    static volatile ArrayList<HashMap<String, Integer>> multipleTestNeighbors = new ArrayList<>();
    private int x, y;

    Tester(){
        x = 0;
        y = 0;
    }

    Tester(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        Runner runner = new Runner(false);
        if(!(x == 0 || y == 0)){
            runner.setDimensions(x, y);
        }
        while (true) {
            if (!runner.run()) {
                break;
            }
        }
        multipleTestNeighbors.add(runner.getDataMap());
    }
}
