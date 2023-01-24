package com.example.mytimetable;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryRepository {
    private EntryDao entryDao;
    private LiveData<List<Entry>> allEntries;
    private LiveData<List<Entry>> mondayEntries;
    private LiveData<List<Entry>> tuesdayEntries;
    private LiveData<List<Entry>> wednesdayEntries;
    private LiveData<List<Entry>> thursdayEntries;
    private LiveData<List<Entry>> fridayEntries;
    private LiveData<List<Entry>> saturdayEntries;

    public EntryRepository(Application application) {
        EntryDataBase dataBase = EntryDataBase.getInstance(application);
        entryDao = dataBase.entryDao();
        allEntries = entryDao.getAllEntries();
        mondayEntries = entryDao.getAllMondayEntries();
        tuesdayEntries = entryDao.getAllTuesdayEntries();
        wednesdayEntries = entryDao.getAllWednesdayEntries();
        thursdayEntries = entryDao.getAllThursdayEntries();
        fridayEntries = entryDao.getAllFridayEntries();
        saturdayEntries = entryDao.getAllSaturdayEntries();
    }

    public void insert(Entry entry) {
        new InsertEntryAsyncTask(entryDao).execute(entry);
    }

    public void update(Entry entry) {
        new UpdateEntryAsyncTask(entryDao).execute(entry);
    }

    public void delete(Entry entry) {
        new DeleteEntryAsyncTask(entryDao).execute(entry);
    }

    public void deleteAllEntries() {
        new DeleteAllEntryAsyncTask(entryDao).execute();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }

    public LiveData<List<Entry>> getMondayEntries() {
        return mondayEntries;
    }

    public LiveData<List<Entry>> getTuesdayEntries() {
        return tuesdayEntries;
    }

    public LiveData<List<Entry>> getWednesdayEntries() {
        return wednesdayEntries;
    }

    public LiveData<List<Entry>> getThursdayEntries() {
        return thursdayEntries;
    }

    public LiveData<List<Entry>> getFridayEntries() {
        return fridayEntries;
    }

    public LiveData<List<Entry>> getSaturdayEntries() {
        return saturdayEntries;
    }

    private static class InsertEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao entryDao;

        private InsertEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }
        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.insert(entries[0]);
            return null;
        }
    }

    private static class UpdateEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao entryDao;

        private UpdateEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }
        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.update(entries[0]);
            return null;
        }
    }

    private static class DeleteEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao entryDao;

        private DeleteEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }
        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.delete(entries[0]);
            return null;
        }
    }

    private static class DeleteAllEntryAsyncTask extends AsyncTask<Entry, Void, Void> {
        private EntryDao entryDao;

        private DeleteAllEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }
        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.deleteAllEntries();
            return null;
        }
    }
}
