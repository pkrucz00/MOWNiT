package GUI;

import Simulation.StatisticsObserver;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;

public class XYLineChart extends ApplicationFrame {
    public XYLineChart(String applicationTitle, String chartTitle, StatisticsObserver observer, int dimX, int dimY) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Iteration", "Number of occurences",
                createDataset(observer),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(dimX, dimY));
        final XYPlot plot = xylineChart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.ORANGE);
        renderer.setSeriesPaint(2, Color.RED);
        for (int i = 0; i < 3; i++) {
            renderer.setSeriesStroke(i, new BasicStroke(3.0f));
        }

        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    private XYDataset createDataset(StatisticsObserver observer) {
        final XYSeries susceptible = new XYSeries("Susceptible");
        final XYSeries infected = new XYSeries("Infected");
        final XYSeries removed = new XYSeries("Removed");

        int n = observer.getNoIterations();
        ArrayList<Integer> sus = observer.getNoSusceptibleTable();
        ArrayList<Integer> inf = observer.getNoInfectedTable();
        ArrayList<Integer> rem = observer.getNoRemovedTable();

        for (int i = 0; i < n; i++){
            susceptible.add(i, sus.get(i));
            infected.add(i, inf.get(i));
            removed.add(i, rem.get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(susceptible);
        dataset.addSeries(infected);
        dataset.addSeries(removed);
        return dataset;
    }
}
