package com.example.hsn07.calarsaat;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Hsn07 on 6.6.2017.
 */

public class AlarmAdapter extends BaseAdapter {
    private ArrayList<Alarm> alarmlistesi;
    private LayoutInflater inflater;

    public AlarmAdapter(Activity activity, ArrayList<Alarm> alarmlistesi ){
        this.alarmlistesi=alarmlistesi;
        inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//ac
    }
    @Override
    public int getCount() {
        return alarmlistesi.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmlistesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View alarmsatirView, ViewGroup parent) {

        alarmsatirView=inflater.inflate(R.layout.alarmsatir,null);//satır düzenini belirtiyoruz

        TextView tvzaman=(TextView) alarmsatirView.findViewById(R.id.tvzaman);
        TextView tvnot=(TextView) alarmsatirView.findViewById(R.id.tvnot);
        TextView tvno=(TextView)alarmsatirView.findViewById(R.id.tvno);


        Alarm alarm=alarmlistesi.get(position);
        tvno.setText(String.valueOf(alarm.getId()));
        tvzaman.setText(alarm.getZaman());
        tvnot.setText(alarm.getNot());


        return alarmsatirView;
    }
}
