package wonders.simulator;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import wonders.simulator.wsnsimulation.SimulationManager;

public class GraphGenerator {

    private double[] gaussianSamples;
    private double[] distrVals;
    private double[] samples;
    private static int color;
    private static int bgcolor;
    private static GraphGenerator gen;
    private static int maximumSamples = 100;
    private static int rounds = 1;


    private GraphGenerator() {

    }

    public int getColor() {
        return color;
    }

    public  int getBgcolor() {
        return bgcolor;
    }

    public int getRounds() {
        return rounds;
    }

    public static GraphGenerator getInstance(){
        if(gen==null)
            gen= new GraphGenerator();
        return gen;
    }

    public void setMaximumSamples(int maximumSamples) {
        GraphGenerator.maximumSamples = maximumSamples;
    }

    public void setRounds(int rounds) {
        GraphGenerator.rounds = rounds;
    }

    public void setColor(int color) {
        GraphGenerator.color = color;
    }


    public void setBgcolor(int bgcolor) {
        GraphGenerator.bgcolor = bgcolor;
    }

    public synchronized void simRunner(){

        for(int i=0;i<rounds;i++)
            SimulationManager.runSimulation();

    }

    public synchronized LineData generateGraph() {
        double mean = SimulationManager.getSimulationSetup().getTheta();
        double variance = (SimulationManager.getSimulationSetup().getC()/SimulationManager.getSimulationSetup()
                .getSensorCount());

        Random ran = new Random();
        gaussianSamples = new double[maximumSamples];
        for (int i=0;i<maximumSamples;i++) {
            gaussianSamples[i] = ((ran.nextGaussian()));
        }

        distrVals = new double[maximumSamples];
        samples = new double[maximumSamples];
        for (int i=0;i<maximumSamples;i++) {
            if (i<maximumSamples/2)
                samples[i] = (mean-(Math.sqrt(variance)*gaussianSamples[i]));
            else
                samples[i] = (mean+(Math.sqrt(variance)*gaussianSamples[i]));

            // This is the renormalized Gaussian formula, specific for this

            distrVals[i] = (Math.pow(Math.exp(-(((samples[i] - mean) *
                    (samples[i] - mean)) / ((2 * variance)))), 1 / (Math.sqrt(variance) *
                    Math.sqrt(2 * Math.PI))));
        }

        List<Entry> entries = new ArrayList<Entry>();
        for (int i=0;i<maximumSamples;i++) {
            entries.add(new Entry((float) (0+samples[i]),(float) distrVals[i]));
        }

        Collections.sort(entries, new EntryXComparator());
        LineDataSet distributionData = new LineDataSet(entries, "Default Distribution");

        distributionData.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        distributionData.setCubicIntensity(0.2f);
        distributionData.setDrawCircles(false);
        distributionData.setLineWidth(1.8f);
        distributionData.setCircleColor(color);
        distributionData.setColor(color);
        distributionData.setFillColor(color);
        return new LineData(distributionData);
    }


}
