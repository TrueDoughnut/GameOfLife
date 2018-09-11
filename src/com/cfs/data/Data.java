package com.cfs.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Data {

    private boolean output;

    Data(){
        output = true;
    }
    Data(boolean output){
        this.output = output;
    }

    private boolean getOutput(){
        return output;
    }
    private XYSeries createDataset(String key){
        XYSeries series = new XYSeries(key);
        for(HashMap<String, Integer> data : Tester.multipleTestNeighbors) {
            series.add(data.get(key), data.get("cycles"));
        }
        return series;
    }

    void createGraph(String title, String xAxisLabel, String yAxisLabel){
        if(this.getOutput()) {
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
                    title, xAxisLabel, yAxisLabel, dataset);
            Graph.showGraph(chart);
        }
    }

    public abstract void run() throws InterruptedException;
}
