package com.cfs.bruteforce;

import com.cfs.runner.Runner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class BruteForce {

    private int x, y;

    public BruteForce(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void run(){
        ArrayList<Thread> threads = new ArrayList<>();
        //create all combinations of arrays
        BigInteger combinations = BigInteger.ONE;
        combinations = combinations.multiply(new BigInteger("2").pow(x*y));
        for(BigInteger i = BigInteger.ZERO; i < combinations; i)
        System.out.println(threads.size());
        for(Thread thread : threads){
            thread.start();
        }
        for(Thread thread : threads){
            try {
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(getInfinite());
    }

    private ArrayList<int[][]> getInfinite(){
        ArrayList<int[][]> list = new ArrayList<>();
        for(int[][] arr : Cycles.cycles.keySet()){
            if(Cycles.cycles.get(arr) == 10000){
                list.add(arr);
            }
        }
        return list;
    }
}

class Cycles implements Runnable {

    private Runner runner;

    static volatile HashMap<int[][], Integer> cycles;

    Cycles(int[][] board){
        runner = new Runner(board);
        runner.setPrint(false);
        cycles = new HashMap<>();
    }

    @Override
    public void run(){
        while(runner.run()){}
        cycles.put(runner.getBoard(), runner.getCycles());
    }
}