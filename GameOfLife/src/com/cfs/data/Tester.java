package com.cfs.data;

import com.cfs.runner.Runner;

import java.util.ArrayList;
import java.util.HashMap;

public class Tester implements Runnable {

    static volatile ArrayList<HashMap<String, Integer>> multipleTestNeighbors = new ArrayList<>();
    private int x, y;
    private String testType;

    Tester(){
        x = 0;
        y = 0;
    }

    Tester(int x, int y){
        this.x = x;
        this.y = y;
    }

    Tester(String testType){
        this.testType = testType;
    }

    @Override
    public void run() {
        Runner runner = new Runner(false);
        HashMap<String, Integer> map = null;
        if(!(x == 0 || y == 0)){
            runner.setDimensions(x, y);
            runner.setPrint(false);
            map = runner.getDimensionData();
        } else {
            if(testType.equals("cell")){
                map = runner.getCellData();
            } else if(testType.equals("neighbor")) {
                map = runner.getNeighborData();
            }
        }
        if(map == null){
            System.err.println("not a valid test type");
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
