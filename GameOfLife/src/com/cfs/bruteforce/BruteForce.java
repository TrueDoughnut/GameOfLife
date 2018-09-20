package com.cfs.bruteforce;

import com.cfs.runner.Runner;
import com.google.gson.GsonBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BruteForce {

    private int x, y;

    private boolean print;
    private int numberOfThreads;

    public BruteForce(int x, int y){
        this.x = x;
        this.y = y;
        this.numberOfThreads = 20;
        this.print = true;
    }

    public void run() throws InterruptedException {
        Queue<int[][]> queue = new LinkedList<>();
        //create all combinations of arrays
        int a = 0, b = 0;
        for(long i = 0; i < Math.pow(2, x*y); i++){
            int[][] board = new int[y][x];
            for(int j = 0; j < a; j++){
                for(int k = 0; k < board[j].length; k++){
                    board[j][k] = 1;
                }
            }
            for(int k = 0; k < b; k++){
                board[a][k] = 1;
            }
            //create all permutations
            Integer[] arr = new Integer[x*y];
            int j = 0;
            for(int[] array : board){
                for(int x : array){
                    arr[j++] = x;
                }
            }
            Permutations<Integer> permutations = new Permutations<>(arr);
            while(permutations.hasNext()) {
                int[][] matrix = new int[y][x];
                int c = 0;
                Integer[] permutation = permutations.next();
                for(int[] array : matrix){
                    for(int k = 0; k < array.length; k++){
                        array[k] = permutation[c++];
                    }
                }
                queue.add(matrix);
            }
            if(b >= board[0].length){
                b = 0;
                a++;
            }
            if(a >= board.length){
                break;
            }
            b++;
        }

        Thread[] threads = new Thread[numberOfThreads];
        doThreads(threads, queue);

        if(print) {
            System.out.println(new GsonBuilder()
                    .setPrettyPrinting().create().
                            toJson(Cycles.getCycles()));
            System.out.println(Cycles.getCycles().size());
        }
    }

    private void doThreads(Thread[] threads, Queue<int[][]> queue){
        while(running(threads)){
            for(Thread thread : threads){
                if(thread == null || !thread.isAlive()){
                    int[][] matrix = queue.poll();
                    if(matrix == null){
                        return;
                    }
                    thread = new Thread(new Cycles(matrix));
                    thread.start();
                }
            }
        }
    }

    private boolean running(Thread[] threads){
        for(Thread thread : threads){
            if(thread == null || thread.isAlive()){
                return true;
            }
        }
        return false;
    }

    private ArrayList<int[][]> getInfinite(){
        ArrayList<int[][]> list = new ArrayList<>();
        for(int[][] arr : Cycles.cycles.keySet()){
            if(Cycles.cycles.get(arr) == Runner.getMax()){
                list.add(arr);
            }
        }
        return list;
    }

    public void setNumberOfThreads(int x){
        this.numberOfThreads = x;
    }
    public int getNumberOfThreads(){
        return numberOfThreads;
    }
    public void setPrint(boolean print){
        this.print = print;
    }
    public boolean getPrint() {
        return print;
    }
}

class Cycles implements Runnable {

    private Runner runner;

    static volatile ConcurrentHashMap<int[][], Integer> cycles;

    static {
        cycles = new ConcurrentHashMap<>();
    }

    Cycles(int[][] board){
        runner = new Runner(board);
        runner.setPrint(false);
    }

    @Override
    public void run(){
        int[][] board = new int[runner.getBoard().length][];
        for(int i = 0; i < board.length; i++){
            board[i] = runner.getBoard()[i].clone();
        }
        while(runner.run()){}
        cycles.put(board, runner.getCycles());
    }

    static Map<String, Integer> getCycles(){
        Map<String, Integer> map = new ConcurrentHashMap<>();
        for(int[][] key : cycles.keySet()){
            map.put(printArray(key), cycles.get(key));
        }
        map = new TreeMap<>(map);
        return map;
    }

    private static String printArray(int[][] arr){
        StringBuilder stringBuilder = new StringBuilder();
        for(int[] array : arr){
            for(int x : array){
                stringBuilder.append(x).append(" ");
            }
        }
        return stringBuilder.toString();
    }
}