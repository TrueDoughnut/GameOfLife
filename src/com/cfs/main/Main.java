package com.cfs.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        /*GUI gui = new GUI();
        gui.start();*/
        Thread[] arr = new Thread[20];
        for(int i = 0; i < arr.length; i++){
            Thread thread = new Thread(new Tester());
            thread.start();
            arr[i] = thread;
        }
        for(Thread thread : arr) {
            thread.join();
        }
    }
}
