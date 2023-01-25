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

    @Query("SELECT * FROM moduleElement ORDER BY dayRanking ASC, type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'MONDAY' ORDER BY type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllMondayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'TUESDAY' ORDER BY type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllTuesdayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'WEDNESDAY' ORDER BY type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllWednesdayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'THURSDAY' ORDER BY type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllThursdayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'FRIDAY' ORDER BY type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllFridayEntries();

    @Query("SELECT * FROM moduleElement WHERE day = 'SATURDAY' ORDER BY type ASC, startingFrom ASC")
    LiveData<List<Entry>> getAllSaturdayEntries();
}
