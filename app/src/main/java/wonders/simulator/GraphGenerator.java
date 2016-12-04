package wonders.simulator;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class GraphGenerator extends Service {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class StatisticsActivity extends Simulator_main{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.frameLayout.removeAllViews();
            getLayoutInflater().inflate(R.layout.activity_specs,frameLayout);
            setContentView(R.layout.activity_statistics);
        }
    }
}
