package android.lovemesomedatacom.db;

import android.provider.BaseColumns;

/**
 * Created by ehugh on 11/24/2017.
 *
 * alot of this code comes from the tutorial by Aldo Ziflaj
 * https://www.sitepoint.com/starting-android-development-creating-todo-app/
 *
 *
 */

public class NotesTable {

    public static final String DB_NAME = "android.lovemesomedatacom.noteslist.db";
    public static final int DB_VERSION = 1;

    public class NotesEntry implements BaseColumns {
        public static final String TABLE = "Notes";

        public static final String COL_NOTES_TITLE = "title";
        public static final String COL_NOTES_TEXT = "text";
    }
}
