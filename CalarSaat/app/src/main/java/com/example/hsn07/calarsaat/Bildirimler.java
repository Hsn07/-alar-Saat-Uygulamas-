package com.example.hsn07.calarsaat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Bildirimler extends AppCompatActivity {
    ArrayList<String> list;
    ListView lvbil;
    private final String preferencesFilename = "com.example.hsn07.calarsaat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bildirimler);
        ////////////////////////////////////////

        ///////////////////////////////////////
        list=new ArrayList<String>();
        lvbil =(ListView)findViewById(R.id.lvbil);
        String metin=getir();
        for (String m : metin.split(",")){
            list.add(m);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Bildirimler.this,R.layout.basitsatir,R.id.tvbil,list);
        lvbil.setAdapter(adapter);

    }

    public String getir(){
        SharedPreferences preferences = getSharedPreferences(preferencesFilename, Context.MODE_PRIVATE);
        String get = preferences.getString("notification_text","");
        return get;
    }

}
