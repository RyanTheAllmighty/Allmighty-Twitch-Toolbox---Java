package me.ryandowling.allmightytwitchtoolbox.gui;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.Series;
import com.xeiam.xchart.SeriesMarker;
import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.ChartData;
import me.ryandowling.allmightytwitchtoolbox.events.adapters.ViewerCountAdapter;
import me.ryandowling.allmightytwitchtoolbox.events.managers.ViewerCountManager;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;

public class ViewerCountPanel extends JPanel {
    private Chart chart;
    private Series series;
    private ChartData data;

    public ViewerCountPanel() {
        super();
        this.setLayout(new BorderLayout());

        this.data = App.NOTIFIER.getViewerCountChartData();

        setupChart();

        ViewerCountManager.addListener(new ViewerCountAdapter() {
            @Override
            public void onViewerCountDataAdded(int viewerCount) {
                updateData();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.data == null || this.data.getxAxis().size() == 0) {
            return;
        }

        this.chart.paint((Graphics2D) g, this.getWidth(), this.getHeight());
    }

    private void setupChart() {
        this.chart = new Chart(500, 400);
        this.chart.setChartTitle("Viewer Count");
        this.chart.setXAxisTitle("Time");
        this.chart.setYAxisTitle("Viewers");
        this.chart.getStyleManager().setDatePattern("HH:mm:ss");
        this.chart.getStyleManager().setDecimalPattern("###,###");
        this.chart.getStyleManager().setLegendVisible(false);

        this.series = this.chart.addSeries("Viewer Count", this.data.getxAxis(), this.data.getyAxis());
        this.series.setMarker(SeriesMarker.CIRCLE);
    }

    private void updateData() {
        this.data = App.NOTIFIER.getViewerCountChartData();

        this.series.replaceXData(this.data.getxAxis());
        this.series.replaceYData(this.data.getyAxis());
    }
}
