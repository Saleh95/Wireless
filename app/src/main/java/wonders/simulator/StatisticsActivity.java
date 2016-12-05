package wonders.simulator;

import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticsActivity extends Simulator_main {
    ConfigurationActivity config = new ConfigurationActivity();
    double thetaHat,obs, power, nVar,vVar = 1.0;          // need to be set still
    double sensors = config.sensors;
    String yVal,alpha,channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.frameLayout.removeAllViews();
        getLayoutInflater().inflate(R.layout.activity_statistics,frameLayout);

        final TextView theta_view = (TextView) findViewById(R.id.thetahat_stats);
        final TextView obs_view = (TextView) findViewById(R.id.obs_stats);
        final TextView yVal_view = (TextView) findViewById(R.id.yVal_stats);
        final TextView nVar_view = (TextView) findViewById(R.id.nVar_stats);
        final TextView power_view = (TextView) findViewById(R.id.power_stats);
        final TextView vVar_view = (TextView) findViewById(R.id.vVar_stats);
        final TextView alpha_view = (TextView) findViewById(R.id.alpha_stats);
        final TextView channel_view = (TextView) findViewById(R.id.channel_stats);
        final TextView sensors_view = (TextView) findViewById(R.id.sensors_stats);

//        alpha_view.append(config.getBool("alpha"));
//        channel_view.append(config.getBool("channel"));
//        sensors_view.append(String.valueOf(sensors));

    }
}
