package com.cfs.data;

public class NeighborsData extends Data {

    @Override
    public void run() throws InterruptedException {
        Thread[] arr = new Thread[20];
        for(int i = 0; i < arr.length; i++){
            Thread thread = new Thread(new Tester());
            thread.start();
            arr[i] = thread;
        }
        for(Thread thread : arr) {
            thread.join();
        }
        this.createGraph("Neighbors vs Cycles", "Amount of certain number of neighbors",
                "Amount of cycles");
    }
}

