package com.example.mytimetable;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Entry.class}, version = 1)
public abstract class EntryDataBase extends RoomDatabase {
    private static EntryDataBase instance;

    public abstract EntryDao entryDao();

    public static synchronized EntryDataBase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EntryDataBase.class,
                    "entry_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EntryDao entryDao;

        private PopulateDbAsyncTask(EntryDataBase db) {
            entryDao = db.entryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
