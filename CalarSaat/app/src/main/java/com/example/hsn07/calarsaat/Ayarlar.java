package com.example.hsn07.calarsaat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Ayarlar extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String dosyaadi="com.example.hsn07.calarsaat";
    Switch sbildirim;
    TextView tvses,tvdk;
    SeekBar sbseviye;
    EditText etdk;
    Button btnkaydet;
    int ses,dk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Ayarlar");
        ab.setSubtitle("Alt Başlık...");
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.arkaplan));
        //ab.hide();
        //ab.show();
        /////////////////////////////////////////////////////////
        sbildirim=(Switch)findViewById(R.id.secenek);
        tvses=(TextView)findViewById(R.id.tvsesduzeyi);
        sbseviye=(SeekBar)findViewById(R.id.sbduzey);
        etdk=(EditText)findViewById(R.id.etdk);
        btnkaydet=(Button)findViewById(R.id.btnkaydet);
        tvdk=(TextView)findViewById(R.id.tvdk);
        //////////////////////////////////////////////////////
        sharedPreferences=getSharedPreferences(dosyaadi,MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        ////////////////////////////////////////////////////////////////
        getir();
        ////////////////////////////////////////////////////////////////
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etdk.getText().length()>0){
                    dk=Integer.parseInt(etdk.getText().toString());
                }else{
                    dk=Integer.parseInt(tvdk.getText().toString());
                }
                ses=sbseviye.getProgress();
                editor.putInt("erteledk",dk);
                editor.putInt("alarmseviye",ses);
                editor.putBoolean("sbildirim",sbildirim.isChecked());
                editor.commit();
                etdk.setText(null);
                getir();

            }
        });
        ///////////////////////////////////////////////////////

        /////////////////////////////////////////////////////
        sbseviye.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ses=progress;
                guncelle();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //////////////////////////////////////////////////////


    }

    private void getir() {
        Boolean durum=sharedPreferences.getBoolean("sbildirim",true);
        ses=sharedPreferences.getInt("alarmseviye",5);
        dk=sharedPreferences.getInt("erteledk",5);
        tvdk.setText(String.valueOf(dk));
        sbseviye.setProgress(ses);
        sbildirim.setChecked(durum);
    }


    private void guncelle() {
        tvses.setText(String.valueOf(ses));
    }

}
