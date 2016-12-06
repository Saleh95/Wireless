package wonders.simulator;


import android.graphics.Color;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;

public class AppManager {

    private GraphGenerator g;
    private static AppManager app;

    protected AppManager() {
        g = GraphGenerator.getInstance();
    }

    public static AppManager getApp(){
        if(app==null)
            app = new AppManager();
        return app;
    }

    public void setColor(int color){
        g.setColor(color);
    }

    public void setRound(int round){
        g.setRounds(round);
    }       // send time to here

    public void setMax(int max){
        g.setMaximumSamples(max);
    }

    public void genChart(final Chart chart){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                g.simRunner();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                chart.setData( g.generateGraph());
            }
        });

        t1.start();
        t2.start();
        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        chart.invalidate();
    }


}
