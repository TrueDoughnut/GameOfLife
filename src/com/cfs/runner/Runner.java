package com.cfs.runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Runner {

    private int[][] board;
    private int cycles;
    private HashMap<String, Integer> data;
    private boolean print;

    public Runner(){
        print = true;
        createRandom();
        data = new HashMap<>();
        getData();
    }
    public Runner(int[][] board){
        print = true;
        //must be rectangular matrix
        for(int i = 0; i < board.length-1; i++){
            if(board[i].length != board[i+1].length){
                System.out.println("Must be rectangular matrix");
            }
        }
        this.board = board;
        data = new HashMap<>();
        getData();
    }
    public Runner(boolean print){
        this();
        this.print = print;
    }
    public Runner(int[][] board, boolean print){
        this(board);
        this.print = print;
    }
    public Runner(int x, int y){
        print = true;
        createRandom(x, y);
        data = new HashMap<>();
        getData();
    }

    public boolean run(){
        ArrayList<ArrayList<Integer>> change = check();
        if(change == null || cycles >= 1000){
            if(print) {
                System.out.println(cycles);
                System.out.println(data);
            }
            data.put("cycles", cycles);
            return false;
        } else {
            change(change);
            return true;
        }
    }

    private void createRandom(){
        this.createRandom(20, 20);
    }
    private void createRandom(int x, int y){
        board = new int[y][x];
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = new Random().nextInt(2); // 1 (alive) or 0 (dead)
            }
        }
    }

    private ArrayList<ArrayList<Integer>> check(){
        ArrayList<ArrayList<Integer>> change = new ArrayList<>();
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == 1) {
                    if(getNeighbors(i, j) < 2) {
                        change.add(coords(i, j));
                    } else if(getNeighbors(i, j) > 3) {
                        change.add(coords(i, j));
                    }
                } else {
                    if(getNeighbors(i, j) == 3){
                        change.add(coords(i, j));
                    }
                }
            }
        }
        cycles++;
        if(change.isEmpty()){
            return null;
        }
        return change;
    }

    private void change(ArrayList<ArrayList<Integer>> change){
        for(ArrayList<Integer> list : change){
            int x = list.get(0);
            int y = list.get(1);
            if(board[x][y] == 0){
                board[x][y] = 1;
            } else{
                board[x][y] = 0;
            }
        }
    }

    private ArrayList<Integer> coords(int x, int y){
        ArrayList<Integer> coords = new ArrayList<>();
        coords.add(x);
        coords.add(y);
        return coords;
    }

    private int getNeighbors(int x, int y){
        int count = 0;
        try {
            for(int i = x-1; i <= x+1; i++){
                for(int j = y-1; j <= y+1; j++){
                    if(i == x && j == y){
                        continue;
                    }
                    if(board[i][j] == 1){
                        count++;
                    }
                }
            }
        } catch(IndexOutOfBoundsException ignore){}
        return count;
    }

    private int getAmount(int x){
        int count = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(getNeighbors(i, j) == x){
                    count++;
                }
            }
        }
        return count;
    }

    private void getData(){
        data.put("none", getAmount(0));
        data.put("singles", getAmount(1));
        data.put("doubles", getAmount(2));
        data.put("triples", getAmount(3));
        data.put("quadruples", getAmount(4));
        data.put("quintuples", getAmount(5));
        data.put("sextuples", getAmount(6));
        data.put("septuples", getAmount(7));
        data.put("octopules", getAmount(8));
    }

    public int[][] getBoard(){
        return board;
    }

    public HashMap<String, Integer> getDataMap(){
        return data;
    }

    public void setDimensions(int x, int y){
        createRandom(x, y);
        data = new HashMap<>();
        getData();
    }

    @Override
    public String toString(){
        String str = "";

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                str += board[i][j] + " ";
            }
            str += "\n";
        }

        return str + "\n";
    }
}
