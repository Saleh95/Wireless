package wonders.simulator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import wonders.simulator.wsnsimulation.Complex;
import wonders.simulator.wsnsimulation.Sensor;
import wonders.simulator.wsnsimulation.Simulation;
import wonders.simulator.wsnsimulation.SimulationManager;
import wonders.simulator.wsnsimulation.SimulationSetup;


public class GraphActivity extends Simulator_main implements OnChartGestureListener,OnChartValueSelectedListener

{

    private LineChart mChart;
    private Intent intent;


    double[] gaussianSamples;
    double[] distrVals;
    double[] samples;
    Bundle data;


    private int maximumSamples = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_graph,frameLayout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SimulationManager.updateSimulation(pref);

        data = getIntent().getExtras();

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(data.getInt("bgColor"));
        mChart.setClickable(true);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.setMaxHighlightDistance(300);

        XAxis x = mChart.getXAxis();
        x.setEnabled(true);

        YAxis y = mChart.getAxisLeft();
//        y.setTypeface(mTfLight);
        y.setLabelCount(10);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.BLACK);
        x.setLabelCount(10);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(true);
        x.setAxisLineColor(Color.BLACK);
        x.setTextColor(Color.BLACK);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData();

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();

//        colorPicker();

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
        Toast toast = Toast.makeText(getApplicationContext(),"X:"+Float.toString(e.getX())
                +"Y:"+Float.toString(e.getY()),Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onNothingSelected() {

    }




    private void colorPicker(){

        ColorPickerDialogBuilder
                .with(getApplicationContext())
                .setTitle("Choose color")
                .initialColor(Color.BLACK)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                       Toast toast=  Toast.makeText(getApplicationContext(),
                               "onColorSelected: 0x" + Integer.toHexString(selectedColor),
                               Toast.LENGTH_LONG
                               );
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        mChart.setBackgroundColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void setData() {
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
        distributionData.setCircleColor(data.getInt("Color"));
        distributionData.setColor(data.getInt("Color"));
        distributionData.setFillColor(data.getInt("Color"));

        LineData data = new LineData(distributionData);

        mChart.setData(data);
        mChart.invalidate();

        }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
