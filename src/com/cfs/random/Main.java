package com.cfs.random;

import com.cfs.runner.GUI;

public class Main {
    public static void main(String[] args){
        /*try {
            Data data = new Data();
            data.run();
        } catch(InterruptedException ignore){}*/
        GUI gui = new GUI(90, 40);
        gui.start();
    }
}
