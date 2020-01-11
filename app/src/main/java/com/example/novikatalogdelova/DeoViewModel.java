package com.example.novikatalogdelova;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.novikatalogdelova.data.DeoRepository;
import com.example.novikatalogdelova.model.Deo;

import java.util.List;

public class DeoViewModel extends AndroidViewModel {
    private DeoRepository deoRepository;
    private LiveData<List<Deo>> listaSvihDelova;

    public DeoViewModel(@NonNull Application application) {
        super(application);
        deoRepository = new DeoRepository(application);
        listaSvihDelova = deoRepository.getSveDelovi();
    }

    public void insert(Deo deo){
        deoRepository.insert(deo);
    }
    public void update(Deo deo){
        deoRepository.update(deo);
    }
    public void delete(Deo deo){
        deoRepository.delete(deo);
    }
    public void deleteAll(){
        deoRepository.deleteAll();
    }
    public LiveData<List<Deo>> dohvatiSveDelove() {
        return listaSvihDelova;
    }
}
