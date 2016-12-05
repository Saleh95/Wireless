package wonders.simulator;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import wonders.simulator.wsnsimulation.SetupListener;
import wonders.simulator.wsnsimulation.Simulation;
import wonders.simulator.wsnsimulation.SimulationManager;
import wonders.simulator.wsnsimulation.SimulationSetup;

//Design it so it puts the graph options

public class ConfigurationActivity extends Simulator_main implements SetupListener{

    SimulationSetup setup;
    //private AppManager manager;
    /* These buttons hold the values that get changed with listeners and the onRadioButtonClicked
        The code crashes now when I try to click anything on the simulation navigation menu, it didn't
        crash until I included theta and started calling setupChanged()
     */
    int runtime, sensors = 2;
    double theta = 1.4;
    boolean awgn, optimum = true;
    boolean rician, uniform = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout.removeAllViews();
        getLayoutInflater().inflate(R.layout.content_specs,frameLayout);

        // create objects for text views to display picker values
        final TextView runtime_view = (TextView) findViewById(R.id.runtime_input);
        final TextView sensors_view = (TextView) findViewById(R.id.sensors_input);
        // grab number picker for sensors and runtime, create object
        NumberPicker run_picker = (NumberPicker)findViewById(R.id.runtime_picker);
        run_picker.setMinValue(0);
        run_picker.setMaxValue(400);
        NumberPicker sensor_picker = (NumberPicker)findViewById(R.id.sensors_picker);
        sensor_picker.setMinValue(0);
        sensor_picker.setMaxValue(20);


        //handle changing of number picker
        run_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker numberPicker, int default_val, int input) {
                // display, then set runtime value to selected value
                runtime_view.setText("Runtime: " + String.valueOf(input));
                //runtime = input;
            }
        });

        //handle changing of sensors picker
        sensor_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker numberPicker, int default_val, int input) {
                // display, then set runtime value to selected value
                sensors_view.setText("Sensors: " + String.valueOf(input));
                setup.setSensorCount(input);
            }
        });

        // handle user input for theta
        EditText theta_input = (EditText) findViewById(R.id.theta_input);
        if(!theta_input.getText().toString().equals("")){
        theta = Double.parseDouble(theta_input.getText().toString());}

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
        SimulationManager.getSimulationSetup().setObservation(SimulationSetup.DEFAULT_OBSERVATION);
        SimulationManager.getSimulationSetup().setSensorCount(sensors);
        SimulationManager.getSimulationSetup().setTheta(SimulationSetup.DEFAULT_THETA);
        SimulationManager.getSimulationSetup().setPower(SimulationSetup.DEFAULT_POWER);
        SimulationManager.getSimulationSetup().setVarianceN(SimulationSetup.DEFAULT_N);
        SimulationManager.getSimulationSetup().setVarianceV(SimulationSetup.DEFAULT_V);
        SimulationManager.getSimulationSetup().setK(SimulationSetup.DEFAULT_K);
        SimulationManager.getSimulationSetup().setRician(rician);
        SimulationManager.getSimulationSetup().setUniform(uniform);
        SimulationManager.getSimulationSetup().setAWGN(awgn);
        SimulationManager.getSimulationSetup().setOptimum(optimum);
        //manager.setRound(runtime);
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // detect and record checked button
        if(checked) {
            switch (view.getId()) {
                case R.id.awgn_button:
                    setup.setAWGN(true);
                    break;
                case R.id.rician_button:
                    setup.setRician(true);
                    break;
                case R.id.optimum_button:
                    setup.setOptimum(true);
                    break;
                case R.id.uniform_button:
                    setup.setUniform(true);
                    break;
            }
        }
    }

}
