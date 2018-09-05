package com.cfs.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    private Runner runner;

    public GUI(){
        runner = new Runner();
    }
    public GUI(int[][] board){
        runner = new Runner(board);
    }

    private JFrame frame = new JFrame("Game of Life");
    private Timer timer = new Timer(100, this);

    public void start(){
        createAndShowGUI();
        timer.start();
    }

    private void createAndShowGUI(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(runner.getBoard().length, runner.getBoard()[0].length, 5, 5));
        run();
        frame.pack();
        frame.setVisible(true);
    }

    private void run(){
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
    public void actionPerformed(ActionEvent e){
        frame.getContentPane().removeAll();
        frame.revalidate();
        boolean run = runner.run();
        if(!run){
            timer.stop();
        }
        run();
        frame.pack();
        frame.repaint();
    }
}
