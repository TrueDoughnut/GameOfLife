package com.cfs.main;

import com.cfs.bruteforce.BruteForce;
import com.cfs.data.Data;
import com.cfs.data.LiveCellsData;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        bruteForce();
    }
    private static void bruteForce() throws InterruptedException {
        BruteForce bruteForce = new BruteForce(5, 5);
        bruteForce.run();
    }
    private static void GUI(){
        int[][] board = {{1, 1}, {1, 1}};
        GUI gui = new GUI(board);
        gui.setDelay(50);
        gui.start();
    }
}
