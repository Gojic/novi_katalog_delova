package com.example.novikatalogdelova.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "deo_db")
public class Deo {

    private String imeDela;
    private int cenaDela;
    private String putanjaSlike;//slika dela
    private String komentar;
    @PrimaryKey(autoGenerate = true)
    private int id;


    public Deo(String imeDela, int cenaDela, String putanjaSlike, String komentar) {
        this.imeDela = imeDela;
        this.cenaDela = cenaDela;
        this.putanjaSlike = putanjaSlike;
        this.komentar = komentar;
    }

    public String getImeDela() {
        return imeDela;
    }

    public int getCenaDela() {
        return cenaDela;
    }


    public String getPutanjaSlike() {
        return putanjaSlike;
    }

    public String getKomentar() {
        return komentar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
