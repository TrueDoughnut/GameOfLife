package com.cfs.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(Tester.multipleTestNeighbors));

        ArrayList<JFreeChart> charts = new ArrayList<>();
        for(String key : Tester.multipleTestNeighbors.get(0).keySet()){
            XYDataset dataset = createDataset(key);
            JFreeChart chart = ChartFactory.createScatterPlot(
                    key + " vs cycles", key, "cycles", dataset);
            charts.add(chart);
        }
        for(JFreeChart chart : charts){
            showGraph(chart);
        }

    }

    private static XYDataset createDataset(String key){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(key);
        for(HashMap<String, Integer> data : Tester.multipleTestNeighbors){
            series.add(data.get(key), data.get("cycles"));
        }
        System.out.println(series.getItemCount());
        dataset.addSeries(series);
        return dataset;
    }

    private static void showGraph(JFreeChart chart){
        SwingUtilities.invokeLater(() -> {
            Graph graph = new Graph(chart);
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
