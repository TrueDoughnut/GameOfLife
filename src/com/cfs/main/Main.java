package com.cfs.main;

public class Main {
    public static void main(String[] args){
        try {
            Data data = new Data();
            data.run();
        } catch(InterruptedException ignore){}
    }
}
