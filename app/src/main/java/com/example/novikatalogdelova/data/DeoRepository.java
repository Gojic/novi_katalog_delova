package com.example.novikatalogdelova.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.novikatalogdelova.model.Deo;

import java.util.List;

public class DeoRepository {

    private DeoDAO deoDAO;
    private LiveData<List<Deo>> sviDelovi;

    public DeoRepository(Application application) {

        DeoDataBase deoDataBase = DeoDataBase.getInstance(application);
        deoDAO = deoDataBase.deoDAO();
        sviDelovi = deoDAO.listaSvihDelova();
    }

    public void insert(Deo deo) {
        InsertAsyncTask insertAsync = new InsertAsyncTask(deoDAO);
        insertAsync.execute(deo);
    }

    public void update(Deo deo) {
        new UpdateAsyncTask(deoDAO).execute(deo);
    }

    public void delete(Deo deo) {
        new DeleteAsyncTask(deoDAO).execute(deo);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(deoDAO).execute();
    }

    public LiveData<List<Deo>> getSveDelovi() {
        return sviDelovi;
    }

    public static class InsertAsyncTask extends AsyncTask<Deo, Void, Void> {

        private DeoDAO deoDAO;

        public InsertAsyncTask(DeoDAO deoDAO) {
            this.deoDAO = deoDAO;
        }

        @Override
        protected Void doInBackground(Deo... deos) {
            deoDAO.insert(deos[0]);
            return null;
        }
    }

    public static class UpdateAsyncTask extends AsyncTask<Deo, Void, Void> {
        private DeoDAO deoDAO;

        public UpdateAsyncTask(DeoDAO deoDAO) {
            this.deoDAO = deoDAO;
        }

        @Override
        protected Void doInBackground(Deo... deos) {
            deoDAO.update(deos[0]);
            return null;
        }
    }

    public static class DeleteAsyncTask extends AsyncTask<Deo, Void, Void> {
        private DeoDAO deoDAO;

        public DeleteAsyncTask(DeoDAO deoDAO) {
            this.deoDAO = deoDAO;
        }

        @Override
        protected Void doInBackground(Deo... deos) {
            deoDAO.delete(deos[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask<Deo, Void, Void> {
        private DeoDAO deoDAO;

        public DeleteAllAsyncTask(DeoDAO deoDAO) {
            this.deoDAO = deoDAO;
        }

        @Override
        protected Void doInBackground(Deo... deos) {
            deoDAO.deleteAll();
            return null;
        }
    }

}
