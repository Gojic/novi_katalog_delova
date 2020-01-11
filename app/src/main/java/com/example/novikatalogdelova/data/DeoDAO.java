package com.example.novikatalogdelova.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.novikatalogdelova.model.Deo;

import java.util.List;

@Dao
public interface DeoDAO {

    @Insert
    void insert(Deo deo);

    @Update
    void update(Deo deo);

    @Delete
    void delete(Deo deo);

    @Query("DELETE FROM deo_db")
    void deleteAll();

    @Query("SELECT * FROM deo_db")
    LiveData<List<Deo>> listaSvihDelova();

}
