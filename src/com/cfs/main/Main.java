package com.cfs.main;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args){
        GUI gui = new GUI();
        gui.start();
    }

}

class Runner{

    int[][] board;
    private int cycles;

    Runner(){
        createRandom();
    }

    public Runner(int[][] board){
        //must be rectangular matrix
        for(int i = 0; i < board.length-1; i++){
            if(board[i].length != board[i+1].length){
                System.out.println("Must be rectangular matrix");
                System.exit(0);
            }
        }
        this.board = board;
    }

    boolean run(){
        ArrayList<ArrayList<Integer>> change = check();
        if(change == null){
            System.out.println(cycles);
            return false;
        } else {
            change(change);
            return true;
        }
    }

    private void createRandom(){
        board = new int[20][20];
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
            System.out.println(cycles);
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
                    if(board[i][j] == 1){
                        count++;
                    }
                }
            }
        } catch(IndexOutOfBoundsException ignore){}
        return count;
    }

    public int[][] getBoard(){
        return board;
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
