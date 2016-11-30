package wonders.simulator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import wonders.simulator.wsnsimulation.SetupListener;
import wonders.simulator.wsnsimulation.SimulationManager;
import wonders.simulator.wsnsimulation.SimulationSetup;

//Design it so it puts the graph options

public class SpecsActivity extends Simulator_main implements SetupListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_specs,frameLayout);
    }


    public void RunDefaultSetup(){
        SimulationManager.getSimulationSetup().setObservation(SimulationSetup.DEFAULT_OBSERVATION);
        SimulationManager.getSimulationSetup().setSensorCount(SimulationSetup.DEFAULT_SENSOR_COUNT);
        SimulationManager.getSimulationSetup().setTheta(SimulationSetup.DEFAULT_THETA);
        SimulationManager.getSimulationSetup().setPower(SimulationSetup.DEFAULT_POWER);
        SimulationManager.getSimulationSetup().setVarianceN(SimulationSetup.DEFAULT_N);
        SimulationManager.getSimulationSetup().setVarianceV(SimulationSetup.DEFAULT_V);
        SimulationManager.getSimulationSetup().setK(SimulationSetup.DEFAULT_K);
        SimulationManager.getSimulationSetup().setRician(SimulationSetup.DEFAULT_RICIAN);
        SimulationManager.getSimulationSetup().setUniform(SimulationSetup.DEFAULT_UNIFORM);
    }

    @Override
    public void setupChanged() {

    }
}
