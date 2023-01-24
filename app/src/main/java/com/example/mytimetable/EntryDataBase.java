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
            entryDao.insert(new Entry(12, 1, 2, "08:30",
                    Utils.VM, Utils.TYPE_ONE, Utils.MONDAY, Utils.dayToInt(Utils.MONDAY)));
            entryDao.insert(new Entry(8, 1, 2, "14:30",
                    Utils.COMPT, Utils.TYPE_ONE, Utils.TUESDAY, Utils.dayToInt(Utils.TUESDAY)));
            entryDao.insert(new Entry(12, 10, 3, "08:30",
                    Utils.TT, Utils.TYPE_TWO, Utils.SATURDAY, Utils.dayToInt(Utils.SATURDAY)));
            entryDao.insert(new Entry(11, 10, 3, "13:30",
                    Utils.VM, Utils.TYPE_TWO, Utils.MONDAY, Utils.dayToInt(Utils.MONDAY)));
            entryDao.insert(new Entry(14, 13, 3, "13:30",
                    Utils.SP, Utils.TYPE_TWO, Utils.MONDAY, Utils.dayToInt(Utils.MONDAY)));
            entryDao.insert(new Entry(10, 8, 3, "16:30",
                    Utils.GP, Utils.TYPE_TWO, Utils.MONDAY, Utils.dayToInt(Utils.MONDAY)));
            entryDao.insert(new Entry(14, 14, 3, "16:30",
                    Utils.TURBO, Utils.TYPE_TWO, Utils.TUESDAY, Utils.dayToInt(Utils.TUESDAY)));
            entryDao.insert(new Entry(12, 12, 3, "13:30",
                    Utils.SP, Utils.TYPE_TWO, Utils.WEDNESDAY, Utils.dayToInt(Utils.WEDNESDAY)));
            entryDao.insert(new Entry(9, 8, 3, "16:30",
                    Utils.MACHINES, Utils.TYPE_TWO, Utils.WEDNESDAY, Utils.dayToInt(Utils.WEDNESDAY)));
            entryDao.insert(new Entry(11, 8, 3, "13:30",
                    Utils.INFO, Utils.TYPE_TWO, Utils.THURSDAY, Utils.dayToInt(Utils.THURSDAY)));
            entryDao.insert(new Entry(9, 9, 3, "16:30",
                    Utils.TURBO, Utils.TYPE_TWO, Utils.THURSDAY, Utils.dayToInt(Utils.THURSDAY)));
            entryDao.insert(new Entry(9, 1, 2, "10:30",
                    Utils.BD, Utils.TYPE_ONE, Utils.MONDAY, Utils.dayToInt(Utils.MONDAY)));
            entryDao.insert(new Entry(7, 1, 2, "16:30",
                    Utils.GP, Utils.TYPE_ONE, Utils.MONDAY, Utils.dayToInt(Utils.MONDAY)));
            entryDao.insert(new Entry(9, 1, 2, "08:30",
                    Utils.MAINT, Utils.TYPE_ONE, Utils.TUESDAY, Utils.dayToInt(Utils.TUESDAY)));
            entryDao.insert(new Entry(12, 1, 2, "10:30",
                    Utils.SP, Utils.TYPE_ONE, Utils.TUESDAY, Utils.dayToInt(Utils.TUESDAY)));
            entryDao.insert(new Entry(8, 1, 2, "14:30",
                    Utils.COMPT, Utils.TYPE_ONE, Utils.TUESDAY, Utils.dayToInt(Utils.TUESDAY)));
            entryDao.insert(new Entry(9, 1, 2, "16:30",
                    Utils.TURBO, Utils.TYPE_ONE, Utils.TUESDAY, Utils.dayToInt(Utils.TUESDAY)));
            entryDao.insert(new Entry(12, 1, 2, "08:30",
                    Utils.FR, Utils.TYPE_ONE, Utils.WEDNESDAY, Utils.dayToInt(Utils.WEDNESDAY)));
            entryDao.insert(new Entry(12, 1, 2, "10:30",
                    Utils.EN, Utils.TYPE_ONE, Utils.WEDNESDAY, Utils.dayToInt(Utils.WEDNESDAY)));
            entryDao.insert(new Entry(9, 1, 2, "08:30",
                    Utils.INFO, Utils.TYPE_ONE, Utils.THURSDAY, Utils.dayToInt(Utils.THURSDAY)));
            entryDao.insert(new Entry(9, 1, 2, "10:30",
                    Utils.MACHINES, Utils.TYPE_ONE, Utils.THURSDAY, Utils.dayToInt(Utils.THURSDAY)));
            entryDao.insert(new Entry(6, 1, 2, "14:30",
                    Utils.TT, Utils.TYPE_ONE, Utils.THURSDAY, Utils.dayToInt(Utils.THURSDAY)));
            entryDao.insert(new Entry(14, 1, 2, "08:30",
                    Utils.TT, Utils.TYPE_ONE, Utils.FRIDAY, Utils.dayToInt(Utils.FRIDAY)));
            entryDao.insert(new Entry(7, 1, 2, "10:30",
                    Utils.ECO, Utils.TYPE_ONE, Utils.FRIDAY, Utils.dayToInt(Utils.FRIDAY)));
            entryDao.insert(new Entry(11, 8, 2, "10:30",
                    Utils.GP, Utils.TYPE_ONE, Utils.FRIDAY, Utils.dayToInt(Utils.FRIDAY)));
            entryDao.insert(new Entry(13, 2, 3, "15:00",
                    Utils.IA, Utils.TYPE_ONE, Utils.FRIDAY, Utils.dayToInt(Utils.FRIDAY)));
            return null;
        }
    }
}
