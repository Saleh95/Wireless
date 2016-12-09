package wonders.simulator;

import android.content.Intent;
import android.support.annotation.StringDef;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import wonders.simulator.wsnsimulation.Simulation;
import wonders.simulator.wsnsimulation.SimulationManager;
import wonders.simulator.wsnsimulation.SimulationSetup;

public class StatisticsActivity extends Simulator_main {
    ConfigurationActivity config = new ConfigurationActivity();
    double thetaHat,obs, power, nVar,vVar = 1.0;
    double sensors = config.sensors;
    String yVal,alpha,channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.frameLayout.removeAllViews();
        getLayoutInflater().inflate(R.layout.activity_statistics,frameLayout);
        changeWindow();

        // setup to write data for statistics
        final TextView theta_view = (TextView) findViewById(R.id.thetahat_stats);
        final TextView obs_view = (TextView) findViewById(R.id.obs_stats);
        final TextView yVal_view = (TextView) findViewById(R.id.yVal_stats);
        final TextView nVar_view = (TextView) findViewById(R.id.nVar_stats);
        final TextView power_view = (TextView) findViewById(R.id.power_stats);
        final TextView vVar_view = (TextView) findViewById(R.id.vVar_stats);
        final TextView alpha_view = (TextView) findViewById(R.id.alpha_stats);
        final TextView channel_view = (TextView) findViewById(R.id.channel_stats);
        final TextView sensors_view = (TextView) findViewById(R.id.sensors_stats);

        if(SimulationManager.getSimulationSetup().isAWGN()){
            channel_view.append("AWGN");
        }else{
            channel_view.append("Rician with K");
        }

        if(SimulationManager.getSimulationSetup().isOptimum()){
            alpha_view.append("Optimum");
        }else{
            alpha_view.append("Uniform");
        }

        theta_view.append(SimulationManager.getLastSimulation().getThetaHat().toFormattedString());
        obs_view.append(SimulationManager.getSimulationSetup().getObservation());
        nVar_view.append(String.valueOf(SimulationManager.getSimulationSetup().getVarianceN()));
        vVar_view.append(String.valueOf(SimulationManager.getSimulationSetup().getVarianceV()));
        power_view.append(String.valueOf(SimulationManager.getSimulationSetup().getPower()));
        sensors_view.append(String.valueOf(SimulationManager.getSimulationSetup().getSensorCount()));
        yVal_view.append(SimulationManager.getLastSimulation().getYVal().toFormattedString());

    }

    public void showList(View view){
        intent = new Intent(this,SensorList.class);
        startActivity(intent);
    }

}
