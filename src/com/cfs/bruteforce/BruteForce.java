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
        ArrayList<Thread> threads = new ArrayList<>();
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
            Permutations<Integer> permutations = new Permutations<Integer>(arr);
            while(permutations.hasNext()) {
                int[][] board1 = new int[y][x];
                threads.add(new Thread());
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
        for(Thread thread : threads){
            thread.start();
        }
        for(Thread thread : threads){
            thread.join();
        }

        System.out.println(Cycles.getCycles());
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

class Permutations<E> implements  Iterator<E[]>{

    private E[] arr;
    private int[] ind;
    private boolean has_next;

    public E[] output;//next() returns this array, make it public

    Permutations(E[] arr){
        this.arr = arr.clone();
        ind = new int[arr.length];
        //convert an array of any elements into array of integers - first occurrence is used to enumerate
        Map<E, Integer> hm = new HashMap<E, Integer>();
        for(int i = 0; i < arr.length; i++){
            Integer n = hm.get(arr[i]);
            if (n == null){
                hm.put(arr[i], i);
                n = i;
            }
            ind[i] = n.intValue();
        }
        Arrays.sort(ind);//start with ascending sequence of integers


        //output = new E[arr.length]; <-- cannot do in Java with generics, so use reflection
        output = (E[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
        has_next = true;
    }

    public boolean hasNext() {
        return has_next;
    }

    /**
     * Computes next permutations. Same array instance is returned every time!
     * @return
     */
    public E[] next() {
        if (!has_next)
            throw new NoSuchElementException();

        for(int i = 0; i < ind.length; i++){
            output[i] = arr[ind[i]];
        }


        //get next permutation
        has_next = false;
        for(int tail = ind.length - 1;tail > 0;tail--){
            if (ind[tail - 1] < ind[tail]){//still increasing

                //find last element which does not exceed ind[tail-1]
                int s = ind.length - 1;
                while(ind[tail-1] >= ind[s])
                    s--;

                swap(ind, tail-1, s);

                //reverse order of elements in the tail
                for(int i = tail, j = ind.length - 1; i < j; i++, j--){
                    swap(ind, i, j);
                }
                has_next = true;
                break;
            }

        }
        return output;
    }

    private void swap(int[] arr, int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    @Override
    public void remove() {

    }
}