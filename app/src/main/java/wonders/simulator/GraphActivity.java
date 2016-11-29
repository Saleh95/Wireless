package wonders.simulator;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
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
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    private LineChart mChart;


    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    private int runtime = 10;

    private double[] ThetaHat = new double[runtime];

    private double[] GaussianCurve = new double[runtime];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        LineChart chart = (LineChart) findViewById(R.id.chart);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SimulationManager.updateSimulation(pref);

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

        mSeekBarX.setProgress(45);
        mSeekBarY.setProgress(100);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

//        SimulationManager.runSimulation();

//        SimulationManager.getSimulationSetup().setObservation(SimulationSetup.DEFAULT_OBSERVATION);
//        SimulationManager.getSimulationSetup().setSensorCount(SimulationSetup.DEFAULT_SENSOR_COUNT);
//        SimulationManager.getSimulationSetup().setTheta(SimulationSetup.DEFAULT_THETA);
//        SimulationManager.getSimulationSetup().setPower(SimulationSetup.DEFAULT_POWER);
//        SimulationManager.getSimulationSetup().setVarianceN(SimulationSetup.DEFAULT_N);
//        SimulationManager.getSimulationSetup().setVarianceV(SimulationSetup.DEFAULT_V);
//        SimulationManager.getSimulationSetup().setK(SimulationSetup.DEFAULT_K);
//        SimulationManager.getSimulationSetup().setRician(SimulationSetup.DEFAULT_RICIAN);
//        SimulationManager.getSimulationSetup().setUniform(SimulationSetup.DEFAULT_UNIFORM);

        for(int i = 0; i<runtime;i++){
            SimulationManager.runSimulation();
            ThetaHat[i]=SimulationManager.getLastSimulation().getThetaHat().getReal();
            GaussianCurve[i]=(SimulationManager.getSimulationSetup().getC()/SimulationManager.getSimulationSetup().getSensorCount());
        }

//        Toast.makeText(getApplicationContext(),
//                Double.toString(SimulationManager.getLastSimulation().getThetaHat().getMagnitude()),
//                Toast.LENGTH_LONG).show();


//        GraphData[] dataObjects = genrateGraph();
//
//        List<Entry> entries = new ArrayList<Entry>();
//
//        for (GraphData data : dataObjects) {
//
//            // turn your data into Entry objects
//            entries.add(new Entry((float) data.getX(),(float) data.getY()));
//        }
//
//        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
//        dataSet.setColor(Color.GREEN);
//        dataSet.setValueTextColor(Color.BLACK); // styling, ...
//        dataSet.setDrawValues(true);
//        LineData lineData = new LineData(dataSet);
//        chart.setData(lineData);
//        chart.invalidate();

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.BLACK);


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
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
//        y.setTypeface(mTfLight);
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        mChart.getAxisRight().setEnabled(false);

        // add data
        setData(45, 100);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();

    }

    private GraphData[] genrateGraph() {
        GraphData[] data = new GraphData[runtime];;
        for(int i=0;i<runtime;i++){
            data[i]= new GraphData();
            data[i].setX(ThetaHat[i]);
            data[i].setY(GaussianCurve[i]);
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

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());

        // redraw
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        GraphData[] dataObjects = genrateGraph();

//        for (int i = 0; i < count; i++) {
//            float mult = (range + 1);
//            float val = (float) (Math.random() * mult) + 20;// + (float)
//            // ((mult *
//            // 0.1) / 10);
//            yVals.add(new Entry(i, val));
//        }

        for (GraphData data : dataObjects) {

            // turn your data into Entry objects
            yVals.add(new Entry((float) data.getX(),(float) data.getY()));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return -10;
                }
            });

            // create a data object with the datasets
            LineData data = new LineData(set1);
//            data.setValueTypeface(mTfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            mChart.setData(data);
        }
    }


}
