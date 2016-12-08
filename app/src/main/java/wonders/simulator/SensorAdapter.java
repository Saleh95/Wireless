package wonders.simulator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import wonders.simulator.wsnsimulation.Sensor;

public class SensorAdapter extends BaseAdapter{

    private List<Sensor> listData;
    private LayoutInflater layoutInflater;
    private Context mContext;


    public SensorAdapter(Context context, List<Sensor> listData) {
        mContext = context;
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Sensor sensor = (Sensor) getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item,parent,false);
            holder = new ViewHolder();
            holder.ID = (TextView) convertView.findViewById(R.id.sensors_id);
            holder.alpha = (TextView) convertView.findViewById(R.id.sensors_alpha);
            holder.hval = (TextView) convertView.findViewById(R.id.sensors_hval);
            holder.nval = (TextView) convertView.findViewById(R.id.sensors_nval);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ID.setText("Sensor id: "+Integer.toString(sensor.getID()));
        holder.alpha.setText("Alpha: "+sensor.getAlpha().toFormattedString());
        holder.hval.setText("HVal: "+sensor.getHVal().toFormattedString());
        holder.nval.setText("NVal: "+sensor.getNVal().toFormattedString());
        return convertView;
    }
}
