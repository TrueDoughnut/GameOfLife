package com.cfs.random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {

    private boolean output;

    Data(){
        output = true;
    }
    Data(boolean output){
        this.output = output;
    }

    void run() throws InterruptedException {
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
        if(output) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(Tester.multipleTestNeighbors));


            ArrayList<XYSeries> series = new ArrayList<>();
            for (String key : Tester.multipleTestNeighbors.get(0).keySet()) {
                if (key.equals("cycles")) {
                    continue;
                }
                series.add(createDataset(key));
            }
            XYSeriesCollection dataset = new XYSeriesCollection();
            for (XYSeries x : series) {
                dataset.addSeries(x);
            }
            JFreeChart chart = ChartFactory.createScatterPlot(
                    "Compare data to cycles", "Amount",
                    "Cycles", dataset);
            showGraph(chart);
        }
    }

    private XYSeries createDataset(String key){
        XYSeries series = new XYSeries(key);
        for(HashMap<String, Integer> data : Tester.multipleTestNeighbors) {
            series.add(data.get(key), data.get("cycles"));
        }
        return series;
    }

    private void showGraph(JFreeChart chart){
        SwingUtilities.invokeLater(() -> {
            Graph graph = new Graph(chart);
            graph.run();
            graph.setSize(800, 400);
            graph.setLocationRelativeTo(null);
            graph.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            graph.setVisible(true);
        });
    }
}

class Graph extends JFrame {

    private JFreeChart chart;

    Graph(JFreeChart chart){
        this.chart = chart;
    }

    void run(){
        ChartPanel panel = new ChartPanel(chart);
        this.setContentPane(panel);
    }
}
