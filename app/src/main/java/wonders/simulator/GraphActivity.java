package wonders.simulator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.Toast;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wonders.simulator.wsnsimulation.Complex;
import wonders.simulator.wsnsimulation.Sensor;
import wonders.simulator.wsnsimulation.Simulation;
import wonders.simulator.wsnsimulation.SimulationManager;
import wonders.simulator.wsnsimulation.SimulationSetup;


public class GraphActivity extends AppCompatActivity implements OnChartGestureListener,OnChartValueSelectedListener,
        SeekBar.OnSeekBarChangeListener
{

    private XYPlot plot;
    private Simulation sim;
    private SimulationSetup setup;
    private SimulationManager manager;

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    private int runtime = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        LineChart chart = (LineChart) findViewById(R.id.chart);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SimulationManager.updateSimulation(pref);

//        SimulationManager.getSimulationSetup().setObservation(SimulationSetup.DEFAULT_OBSERVATION);
//        SimulationManager.getSimulationSetup().setSensorCount(SimulationSetup.DEFAULT_SENSOR_COUNT);
//        SimulationManager.getSimulationSetup().setTheta(SimulationSetup.DEFAULT_THETA);
//        SimulationManager.getSimulationSetup().setPower(SimulationSetup.DEFAULT_POWER);
//        SimulationManager.getSimulationSetup().setVarianceN(SimulationSetup.DEFAULT_N);
//        SimulationManager.getSimulationSetup().setVarianceV(SimulationSetup.DEFAULT_V);
//        SimulationManager.getSimulationSetup().setK(SimulationSetup.DEFAULT_K);
//        SimulationManager.getSimulationSetup().setRician(SimulationSetup.DEFAULT_RICIAN);
//        SimulationManager.getSimulationSetup().setUniform(SimulationSetup.DEFAULT_UNIFORM);

//        SimulationManager.runSimulation();

        GraphData[] dataObjects = genrateGraph();

        List<Entry> entries = new ArrayList<Entry>();

        for (GraphData data : dataObjects) {

            // turn your data into Entry objects
            entries.add(new Entry((float) data.getX(),(float) data.getY()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.GREEN);
        dataSet.setValueTextColor(Color.BLACK); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    private GraphData[] genrateGraph() {
        GraphData[] data = new GraphData[SimulationManager.getLastSimulation().getSensorList().size()];
        int i = 0;
        for (Sensor s:SimulationManager.getLastSimulation().getSensorList()
             ) {
            data[i].setX(s.getHVal().getMagnitude());
            data[i++].setY(s.getNVal().getMagnitude());

        }
        return data;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
