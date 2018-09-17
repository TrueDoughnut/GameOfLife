package com.cfs.bruteforce;

import com.cfs.runner.Runner;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.*;

public class BruteForce {

    private int x, y;

    public BruteForce(int x, int y){
        this.x = x;
        this.y = y;
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
        Thread[] threads = new Thread[20];
        for(Thread thread : threads){
            thread = new Thread(new Cycles(queue.poll()));
        }
        for(Thread thread : threads){
            thread.start();
        }

        doThreads(threads, queue);

        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(Cycles.getCycles()));
    }

    private void doThreads(Thread[] threads, Queue<int[][]> queue){
        while(running(threads)){
            for(Thread thread : threads){
                if(!thread.isAlive()){
                    int[][] matrix = queue.poll();
                    if(matrix == null){
                        return;
                    }
                    thread = new Thread(new Cycles(queue.poll()));
                    thread.start();
                }
            }
        }
    }

    private boolean running(Thread[] threads){
        for(Thread thread : threads){
            if(thread.isAlive()){
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
}

class Cycles implements Runnable {

    private Runner runner;

    static volatile HashMap<int[][], Integer> cycles;

    static {
        cycles = new HashMap<>();
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
        System.out.println(runner.getCycles());
        cycles.put(board, runner.getCycles());
    }

    static HashMap<String, Integer> getCycles(){
        HashMap<String, Integer> map = new HashMap<>();
        for(int[][] key : cycles.keySet()){
            map.put(printArray(key), cycles.get(key));
        }
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