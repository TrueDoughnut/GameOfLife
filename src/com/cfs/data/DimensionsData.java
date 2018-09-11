package com.cfs.data;

import java.util.Random;

public class DimensionsData extends Data {

    @Override
    public void run() throws InterruptedException {
        Thread[] arr = new Thread[20];
        for(int i = 0; i < arr.length; i++){
            Random rand = new Random();
            Thread thread = new Thread(new Tester(rand.nextInt(50)+1, rand.nextInt(50)+1));
            thread.start();
            arr[i] = thread;
        }
        for(Thread thread : arr) {
            thread.join();
        }
        this.createGraph("Dimensions vs cycles", "Dimensions", "Cycles");
    }
}
