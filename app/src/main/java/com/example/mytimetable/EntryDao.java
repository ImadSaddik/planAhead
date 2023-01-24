package com.example.mytimetable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

    @Update
    void update(Entry entry);

    @Delete
    void delete(Entry entry);

    @Query("DELETE FROM moduleElement")
    void deleteAllEntries();

    @Query("SELECT * FROM moduleElement ORDER BY dayRanking ASC")
    LiveData<List<Entry>> getAllEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'MONDAY' ORDER BY type")
    LiveData<List<Entry>> getAllMondayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'TUESDAY'")
    LiveData<List<Entry>> getAllTuesdayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'WEDNESDAY'")
    LiveData<List<Entry>> getAllWednesdayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'THURSDAY'")
    LiveData<List<Entry>> getAllThursdayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'FRIDAY'")
    LiveData<List<Entry>> getAllFridayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'SATURDAY'")
    LiveData<List<Entry>> getAllSaturdayEntries();
}
