package com.cfs.data;

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
        HashMap<String, Integer> map = new HashMap<>();
        if(!(x == 0 || y == 0)){
            runner.setDimensions(x, y);
            runner.setPrint(false);
            map = runner.getDimensionData();
        } else {
            map = runner.getNeighborData();
        }
        while (true) {
            if (!runner.run()) {
                break;
            }
        }
        map.put("cycles", runner.getCycles());
        multipleTestNeighbors.add(map);

    }
}
