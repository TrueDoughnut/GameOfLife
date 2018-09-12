package com.cfs.main;

import com.cfs.data.Data;
import com.cfs.data.DimensionsData;
import com.cfs.data.LiveCellsData;
import com.cfs.runner.GUI;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Data data = new LiveCellsData();
        data.run();
    }
}
