package com.cfs.data;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;

class Graph extends JFrame {

    private JFreeChart chart;

    private Graph(JFreeChart chart) {
        this.chart = chart;
    }

    private void run() {
        ChartPanel panel = new ChartPanel(chart);
        this.setContentPane(panel);
    }

    static void showGraph(JFreeChart chart) {
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
