package wonders.simulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StatisticsActivity extends Simulator_main {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.frameLayout.removeAllViews();
        getLayoutInflater().inflate(R.layout.activity_statistics,frameLayout);

    }
}
