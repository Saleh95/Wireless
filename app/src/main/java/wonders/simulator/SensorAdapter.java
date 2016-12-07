package wonders.simulator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import wonders.simulator.wsnsimulation.Sensor;

public class SensorAdapter extends BaseAdapter{

    private ArrayList<Sensor> listData;
    private LayoutInflater layoutInflater;

    public SensorAdapter(LayoutInflater layoutInflater, ArrayList<Sensor> listData) {
        this.layoutInflater = layoutInflater;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item,null);
            holder = new ViewHolder();
            holder.ID = (TextView) convertView.findViewById(R.id.sensors_id);
            holder.alpha = (TextView) convertView.findViewById(R.id.sensors_alpha);
            holder.hval = (TextView) convertView.findViewById(R.id.sensors_hval);
            holder.nval = (TextView) convertView.findViewById(R.id.sensors_nval);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ID.setText("Sensor id: "+Integer.toString(listData.get(position).getID()));
        holder.alpha.setText("Alpha: "+listData.get(position).getAlpha().toFormattedString());
        holder.hval.setText("HVal: "+listData.get(position).getHVal().toFormattedString());
        holder.nval.setText("NVal: "+listData.get(position).getNVal().toFormattedString());
        return convertView;
    }
}
