package wonders.simulator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import wonders.simulator.wsnsimulation.Sensor;
import wonders.simulator.wsnsimulation.SimulationManager;

public class SensorList extends Simulator_main {
    private ListView mListView;
    private ArrayList<Sensor> sensors;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout.removeView(findViewById(R.id.color_picker));
        getLayoutInflater().inflate(R.layout.content_sensor_list,frameLayout);

        sensors = new ArrayList<>();
        sensors.addAll(SimulationManager.getLastSimulation().getSensorList());

        mListView = (ListView)findViewById(R.id.sensors_list_view);
        mListView.setAdapter(new SensorAdapter(this.getLayoutInflater(),sensors));

    }


}
