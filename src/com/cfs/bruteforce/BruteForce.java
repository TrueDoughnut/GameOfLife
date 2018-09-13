package com.cfs.bruteforce;

import com.cfs.runner.Runner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        int a = 0, b = 0;
        for(long i = 0; i < Math.pow(2, x*y); i++){
            int[][] board = new int[y][x];
            for(int j = 0; j < a; j++){
                for(int k = 0; k < board[j].length; j++){
                    board[j][k] = 1;
                }
            }
            System.out.println(this.printArray(board));
            threads.add(new Thread(new Cycles(board)));
        }
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

    private String printArray(int[][] arr){
        StringBuilder stringBuilder = new StringBuilder();
        for(int[] array : arr){
            for(int x : array){
                stringBuilder.append(x).append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
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
        System.out.println(runner.getCycles());
        cycles.put(runner.getBoard(), runner.getCycles());
    }

    static HashMap<int[][], Integer> getCycles() {
        return cycles;
    }
}