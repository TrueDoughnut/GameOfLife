package com.cfs.main;

import com.cfs.bruteforce.BruteForce;
import com.cfs.data.Data;
import com.cfs.data.DimensionsData;
import com.cfs.data.LiveCellsData;
import com.cfs.data.NeighborsData;
import com.cfs.runner.GUI;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BruteForce bruteForce = new BruteForce(3, 3);
        bruteForce.run();
    }
}
