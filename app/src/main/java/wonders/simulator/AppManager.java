package wonders.simulator;


import android.graphics.Color;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;

public class AppManager {

    private GraphGenerator g;
    private static AppManager app;
    private static int windowColor=0;

    private AppManager() {
        g = GraphGenerator.getInstance();
    }

    public static AppManager getApp(){
        if(app==null)
            app = new AppManager();
        return app;
    }

    public  int getWindowColor() {
        return windowColor;
    }



    public  void setWindowColor(int windowColor) {
        AppManager.windowColor = windowColor;
    }

    public void setColor(int color){
        g.setColor(color);
    }

    public int getColor(){return g.getColor();}

    public int getBgColor(){return g.getBgcolor();}

    public void setRound(int round){
        g.setRounds(round);
    }       // send time to here

    public int getRound(){return g.getRounds();}

    public void setMax(int max){
        g.setMaximumSamples(max);
    }

    public void setBgColor(int bgColor){g.setBgcolor(bgColor);}

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
