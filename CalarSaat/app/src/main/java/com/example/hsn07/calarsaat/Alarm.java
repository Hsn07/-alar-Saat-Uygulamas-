package com.example.hsn07.calarsaat;

/**
 * Created by Hsn07 on 6.6.2017.
 */

public class Alarm {

    int id;
    int durum;
    String not;
    String zaman;
    public Alarm(int id,String zaman,String not,int durum){
        this.id = id;
        this.zaman = zaman;
        this.durum = durum;
        this.not = not;

    }
    public Alarm(){
        id=0;
        durum=1;
        not="Alarm";
        zaman="00:00";

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDurum() {
        return durum;
    }

    public void setDurum(int durum) {
        this.durum = durum;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }


}
