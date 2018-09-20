package com.cfs.data;

public class LiveCellsData extends Data {


    @Override
    public void run() throws InterruptedException {
        Thread[] arr = new Thread[20];
        for(int i = 0; i < arr.length; i++){
            Thread thread = new Thread(new Tester("cell"));
            thread.start();
            arr[i] = thread;
        }
        for(Thread thread : arr) {
            thread.join();
        }
        this.createGraph("Live/Dead Cells vs cycles", "Cells", "Cycles");
    }
}
