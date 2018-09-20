package com.cfs.main;

import com.cfs.runner.Runner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener, Runnable {

    private Runner runner;

    public GUI(){
        runner = new Runner();
    }
    public GUI(int[][] board){
        runner = new Runner(board);
    }
    public GUI(int x, int y){
        runner = new Runner(x, y);
    }

    private JFrame frame = new JFrame("Game of Life");
    private int delay = 100;
    private Timer timer = new Timer(delay, this);

    public void start(){
        createAndShowGUI();
        timer.start();
    }

    private void createAndShowGUI(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(runner.getBoard().length, runner.getBoard()[0].length, 5, 5));
        cycle();
        frame.pack();
        frame.setVisible(true);
    }

    private void cycle(){
        for(int i = 0; i < runner.getBoard().length; i++){
            for(int j = 0; j < runner.getBoard()[i].length; j++){
                JPanel panel = new JPanel();
                if(runner.getBoard()[i][j] == 0){
                    panel.setBackground(Color.WHITE);
                } else {
                    panel.setBackground(Color.BLACK);
                }
                frame.add(panel);
            }
        }
    }

    @Override
    public void run(){
        this.start();
    }

    public void setDelay(int delay){
        this.delay = delay;
        timer = new Timer(delay, this);
    }

    public int neighbors(){
        return this.runner.getNeighbors(0, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        frame.getContentPane().removeAll();
        frame.revalidate();
        boolean run = runner.run();
        if(!run){
            timer.stop();
        }
        cycle();
        frame.pack();
        frame.repaint();
    }
}
