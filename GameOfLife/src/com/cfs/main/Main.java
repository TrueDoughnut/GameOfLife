package com.cfs.main;

import com.cfs.bruteforce.BruteForce;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Best amount of threads: " + testThreadDimensonsAverage());
    }
    private static void bruteForce() throws InterruptedException {
        BruteForce bruteForce = new BruteForce(4, 5);
        bruteForce.run();
    }
    private static double testThreadDimensonsAverage() throws InterruptedException {
        int total = 0;
        for(int i = 0; i < 10; i++){
            total += testThreadDimensions();
        }
        return total / 10.0;
    }
    private static double testThreadDimensions() throws InterruptedException {
        HashMap<String, Integer> best = new HashMap<>();
        for(int x = 1; x <= 3; x++){
            for(int y = 1; y <= 3; y++){
                int[] arr = {x, y};
                StringBuilder str = new StringBuilder();
                for(int a : arr){
                    str.append(a).append(" ");
                }
                best.put(str.toString(), testThreads(x, y));
            }
        }
        //System.out.println(mapToString(best));
        int total = 0;
        for(String str : best.keySet()){
            total += best.get(str);
        }
        return (double)total / best.size();
    }
    private static int testThreads(int x, int y) throws InterruptedException {
        BruteForce bruteForce = new BruteForce(x, y);
        bruteForce.setPrint(false);
        HashMap<Integer, Double> map = new HashMap<>();
        for(int i = 1; i < 40; i++){
            bruteForce.setNumberOfThreads(i);
            long startTime = System.nanoTime();
            bruteForce.run();
            long endTime = System.nanoTime();
            double runTime = (endTime - startTime) / 1000000.0;
            map.put(i, runTime);
        }
        //System.out.println(mapToString(map));
        double min = Double.MAX_VALUE;
        int bestThreads = 1;
        for(int i : map.keySet()){
            if(map.get(i) < min){
                min = map.get(i);
                bestThreads = i;
            }
        }

        //System.out.println("The best amount of threads is " + bestThreads);
        return bestThreads;
    }

    private static String mapToString(HashMap map){
        return new GsonBuilder().setPrettyPrinting().create().toJson(map);
    }

    private static void GUI(){
        int[][] board = {{1, 1}, {1, 1}};
        GUI gui = new GUI(board);
        gui.setDelay(50);
        gui.start();
    }
}
