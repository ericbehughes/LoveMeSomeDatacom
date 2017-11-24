package android.lovemesomedatacom.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ehugh on 11/24/2017.
 */

public class NotesDBHelper extends SQLiteOpenHelper {


        public NotesDBHelper(Context context) {
            super(context, NotesTable.DB_NAME, null, NotesTable.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + NotesTable.NotesEntry.TABLE + " ( " +
                    NotesTable.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NotesTable.NotesEntry.COL_NOTES_TITLE + " TEXT NOT NULL,"+
                    NotesTable.NotesEntry.COL_NOTES_TEXT + " TEXT NOT NULL);";

            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + NotesTable.NotesEntry.TABLE);
            onCreate(db);
        }
    }


