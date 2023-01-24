package com.example.mytimetable;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository repository;
    private LiveData<List<Entry>> allEntries;
    private LiveData<List<Entry>> mondayEntries;
    private LiveData<List<Entry>> tuesdayEntries;
    private LiveData<List<Entry>> wednesdayEntries;
    private LiveData<List<Entry>> thursdayEntries;
    private LiveData<List<Entry>> fridayEntries;
    private LiveData<List<Entry>> saturdayEntries;

    public EntryViewModel(@NonNull Application application) {
        super(application);
        repository = new EntryRepository(application);
        allEntries = repository.getAllEntries();
        mondayEntries = repository.getMondayEntries();
        tuesdayEntries = repository.getTuesdayEntries();
        wednesdayEntries = repository.getWednesdayEntries();
        thursdayEntries = repository.getThursdayEntries();
        fridayEntries = repository.getFridayEntries();
        saturdayEntries = repository.getSaturdayEntries();
    }

    public void insert(Entry entry) {
        repository.insert(entry);
    }

    public void update(Entry entry) {
        repository.update(entry);
    }

    public void delete(Entry entry) {
        repository.delete(entry);
    }

    public void deleteAllEntries() {
        repository.deleteAllEntries();
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
}
