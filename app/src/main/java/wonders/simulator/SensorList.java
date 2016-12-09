package wonders.simulator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import wonders.simulator.wsnsimulation.Sensor;
import wonders.simulator.wsnsimulation.SimulationManager;

public class SensorList extends Simulator_main {
    private ListView mListView;
    SensorAdapter sensorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout.removeView(findViewById(R.id.color_picker));
        getLayoutInflater().inflate(R.layout.content_sensor_list,frameLayout);
        changeWindow();

        Toast.makeText(getApplicationContext(),Integer.toString(SimulationManager.getLastSimulation().getSensorList().size()),
                Toast.LENGTH_LONG).show();

        mListView = (ListView)findViewById(R.id.sensors_list_view);
        sensorAdapter = new SensorAdapter(this,
                SimulationManager.getLastSimulation().getSensorList());
        mListView.setAdapter(sensorAdapter);

    }


}
