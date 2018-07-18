package com.example.hsn07.calarsaat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Anasayfa extends AppCompatActivity {
    NotificationManager notificationManager;
    ImageView startAlarmBtn,stopAlarmbtn,deleteAlarmbtn;
    EditText etid;
    ListView lvalarmlistesii;
    ArrayList<Alarm> liste;
    AlarmAdapter adapter;
    Button btn,btn2,btn3,btn4;
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;
    public int _iddd;
    RadioButton rd1,rd2;
    Uri uri;
    /////////////////////////////////////////////////////////
    private SharedPreferences sharedPreferences;
    private final String preferencesFilename = "com.example.hsn07.calarsaat";
    int dk,ses;
    ////////////////////////////////////////////////////////
    int alarmid;
    String alarmnot;
    ////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        startAlarmBtn=(ImageView)findViewById(R.id.imgbekle);
        stopAlarmbtn=(ImageView)findViewById(R.id.imgstop);
        deleteAlarmbtn=(ImageView)findViewById(R.id.imgdelete);
        lvalarmlistesii=(ListView)findViewById(R.id.lvalarmlistesi);
        btn2=(Button)findViewById(R.id.btnbildirim);
        rd1=(RadioButton)findViewById(R.id.radioButton);
        rd2=(RadioButton)findViewById(R.id.radioButton2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btnset);
        //////////////////////////////////////////////////////////////////////////////////
        sharedPreferences = getSharedPreferences(preferencesFilename, Context.MODE_PRIVATE);
        ses=sharedPreferences.getInt("alarmseviye",2);
        dk=sharedPreferences.getInt("erteledk",5);
        //////////////////////////////////////////////////////////////////////////////////
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, ses, 0);
        audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, ses, 0);
        audio.setStreamVolume(AudioManager.STREAM_VOICE_CALL, ses, 0);
        ///////////////////////////////////////////////////////////////////////////////////
        boolean alarmdurum = sharedPreferences.getBoolean("alarmdurum", false);
        alarmid=sharedPreferences.getInt("alarmid",0);
        alarmnot=sharedPreferences.getString("alarmnot","ALARM");
        //////////////////////////////////////////////////////////////////
        /*SharedPreferences getAlarms = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String alarms = getAlarms.getString("zilsesi", "default ringtone");
        Toast.makeText(this, "alarm..: "+alarms, Toast.LENGTH_SHORT).show();
        uri = Uri.parse("alarms");*/
        //////////////////////////////////////////////////////////////////
        if (alarmdurum==true)
        {
            showDialog(0);
        }
        ///////////////////////////////////////////////////////////////////
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showDialog(0);
               Intent bildirim=new Intent(Anasayfa.this,Bildirimler.class);
               startActivity(bildirim);

            }
        });
        ///////////////////////////////////////////////////////////////////
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent git=new Intent(Anasayfa.this,Ayarlar.class);
                startActivity(git);
            }
        });
        ///////////////////////////////////////////////////////////////////
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Settings.class);
                startActivity(intent);
            }
        });
        ///////////////////////////////////////////////////////////////////
        etid=(EditText)findViewById(R.id.etid);
        btn=(Button)findViewById(R.id.btn);
        kontrol();
        yenile();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarmbtn.setVisibility(View.VISIBLE);
                deleteAlarmbtn.setVisibility(View.VISIBLE);
                etid.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
            }
        });
        lvalarmlistesii.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (liste.size()>0) {
                    _iddd = liste.get(position).getId();
                }
                return false;
            }
        });
        startAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickerDialog(false);
            }
        });
        stopAlarmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //canselid(_iddd);
            }
        });
        deleteAlarmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etid.getText().length()>0){
                    delete(Integer.parseInt(etid.getText().toString()));
                    //adapter.notifyDataSetChanged();
                    etid.setText("");
                }else {
                    etid.setError("İd boş olamaz !!!");
                }

            }
        });
        registerForContextMenu(lvalarmlistesii);
    }
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(this, "onKeyDown", Toast.LENGTH_SHORT).show();
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Toast.makeText(this, "onKeyUp", Toast.LENGTH_SHORT).show();
        return super.onKeyUp(keyCode, event);
    }*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("İşlem seçiniz...");
        menu.add(0,v.getId(),0,"Sil");
        menu.add(0,v.getId(),0,"Göster");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle()=="Sil") {
            canselid(_iddd);
            delete(_iddd);
            //Toast.makeText(this, _iddd + "nolu alarm sılındı", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }


    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(Anasayfa.this
                , onTimeSetListener
                , calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            int lastId = getLastId() +1;
            int boyut;
            if (rd1.isChecked())
                boyut=1;
            else
                boyut=3;
            for (int i=0;i<boyut;i++){

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();
                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calSet.set(Calendar.MINUTE, minute + i);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);
                if(calSet.compareTo(calNow) <= 0){
                    calSet.add(Calendar.DATE, 1);
                }
                ////////////////////////
                ekle(calSet.getTime().getHours()+" : "+calSet.getTime().getMinutes(),"ALARM",1);
                ///////////////////////////////
                setAlarm(calSet,lastId + i);
            }
            //showDialog(0);
            yenile();
            //Toast.makeText(Anasayfa.this, calSet.getTime().getHours()+ ":" + calSet.getTime().getMinutes() + "\nId = " + (lastId + 1), Toast.LENGTH_SHORT).show();
        }
    };

    private void setAlarm(Calendar alarmCalender ,int alarmId){

        Intent intent = new Intent(getApplicationContext(), Alarmreciver.class);
        //final int _id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), alarmId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);
        adapter.notifyDataSetChanged();
    }

    public void canselid(int alarmId){
        Intent intent = new Intent(getApplicationContext(), Alarmreciver.class);
        PendingIntent peddingintent = PendingIntent.getBroadcast(getApplicationContext(), alarmId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.cancel(peddingintent);
    }
    public void ekle(String zaman,String not,int durum) {
        final SQLiteDatabase db=openOrCreateDatabase("Bilgi", Context.MODE_PRIVATE,null);
        try {
            db.execSQL("Insert into belge1(zaman,not1,durum) Values ('"+zaman+"','"+not+"','"+durum+"');");
            Toast.makeText(this, "Ekleme işlemi başarılı", Toast.LENGTH_SHORT).show();
            /*if (etid.getText().length()>0)
            {
                db.execSQL("Insert into belge1(zaman,not1,durum) Values ('"+zaman+"','"+not+"','"+durum+"');");
                Toast.makeText(this, "Ekleme işlemi başarılı", Toast.LENGTH_SHORT).show();
            }
            else
                etid.setError("id boş olamaz...");*/
        }catch (Exception e){
            Toast.makeText(this, "Ekleme işlemi başarısız...", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void kontrol(){
        final SQLiteDatabase db=openOrCreateDatabase("Bilgi", Context.MODE_PRIVATE,null);
        try{
            db.execSQL("Create table if not exists belge1 (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,zaman Varchar,not1 Varchar,durum INTEGER); ");
           // Toast.makeText(this, "Datebase ile bağlantı kuruldu...", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Toast.makeText(this, "Datebase ile bağlantı kurulamadı... Uygulamayı tekrar başlatınız...", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public void yenile(){
        final SQLiteDatabase db=openOrCreateDatabase("Bilgi", Context.MODE_PRIVATE,null);
        liste=new ArrayList<Alarm>();
        Cursor bak=db.rawQuery("select *from belge1",null);
        while (bak.moveToNext())
        {
            liste.add(new Alarm(bak.getInt(0),bak.getString(1),bak.getString(2),bak.getInt(3)));
        }
        db.close();
        adapter=new AlarmAdapter(Anasayfa.this,liste);
        lvalarmlistesii.setAdapter(adapter);
    }
    public void delete(int id){
        final SQLiteDatabase db=openOrCreateDatabase("Bilgi", Context.MODE_PRIVATE,null);
        db.execSQL("Delete from belge1 where id="+id);
        db.close();
        yenile();
    }
    public int getLastId(){
        final SQLiteDatabase db=openOrCreateDatabase("Bilgi", Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("SELECT * FROM belge1 WHERE id=(SELECT MAX(id) FROM belge1)",null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0)
            return -1;
        else {
            int item = cursor.getInt(cursor.getColumnIndex("id"));
            Log.d("", "getLastId: " + item);
            return item;
        }
    }
   /* public void bildirim() {
        final Notification.Builder builder=new Notification.Builder(Anasayfa.this);
        notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        builder.setContentTitle("ALARM !!!");
        builder.setContentText("Merhaba");
        builder.setSmallIcon(R.drawable.saat);
        builder.setAutoCancel(true);
        builder.setTicker("Alarm çalıyor...");
        Intent intent=new Intent(Anasayfa.this,Bildirimler.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(Anasayfa.this,1,intent,0);
        builder.setContentIntent(pendingIntent);

        Notification notification=builder.getNotification();
        notificationManager.notify(1,notification);
    }*/


    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog1=new Dialog(Anasayfa.this);
        dialog1.setTitle("Hatırlatma");

        dialog1.setContentView(R.layout.costumalertdialog);
        TextView tv=(TextView)dialog1.findViewById(R.id.textView2);
        Button evet=(Button)dialog1.findViewById(R.id.btnevet);
        Button hayir=(Button)dialog1.findViewById(R.id.btnhayir);
        tv.setText(alarmnot+alarmid);
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.information,0,0,0);

        evet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Anasayfa.this, "btnevet...", Toast.LENGTH_SHORT).show();
                dialog1.dismiss();
            }
        });
        hayir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Anasayfa.this, "btnhayir", Toast.LENGTH_SHORT).show();
                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();
                calSet.set(Calendar.HOUR_OF_DAY, calNow.getTime().getHours());
                calSet.set(Calendar.MINUTE, calNow.getTime().getMinutes() + dk);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);
                if(calSet.compareTo(calNow) <= 0){
                    calSet.add(Calendar.DATE, 1);
                }
                setAlarm(calSet,getLastId() +1);
                dialog1.cancel();
            }
        });
        SharedPreferences.Editor editor1=sharedPreferences.edit();
        editor1.putBoolean("alarmdurum",false);
        editor1.commit();
        return dialog1;
    }
}
