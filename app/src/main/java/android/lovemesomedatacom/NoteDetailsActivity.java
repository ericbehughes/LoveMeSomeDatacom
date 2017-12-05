package android.lovemesomedatacom;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.lovemesomedatacom.db.NotesDBHelper;
import android.lovemesomedatacom.db.NotesTable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NoteDetailsActivity extends MenuActivity {

    private TextView tvNoteDetailsTitle;
    private TextView tvNoteDetailsText;

    private NotesDBHelper mHelper;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-11-24 19:05:17 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvNoteDetailsTitle = (TextView)findViewById( R.id.etNoteDetailsTitle );
        tvNoteDetailsText = (TextView)findViewById( R.id.etNoteDetailsText );
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        findViews();

        Intent intent = getIntent();

        tvNoteDetailsTitle.setText(intent.getStringExtra("noteTitle"));
        tvNoteDetailsText.setText(intent.getStringExtra("noteText"));



    }

    @Override
    protected void onStop() {
        super.onStop();
        updateNote();
    }

    private void updateNote(){
        String note_title = tvNoteDetailsTitle.getText().toString();
        String note_text = tvNoteDetailsText.getText().toString();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(NotesTable.NotesEntry.TABLE,
                NotesTable.NotesEntry.COL_NOTES_TITLE+ " = ?",
                new String[]{note_title});

        ContentValues cv = new ContentValues();
        cv.put(NotesTable.NotesEntry.COL_NOTES_TITLE, ""); //These Fields should be your String values of actual column names
        cv.put(NotesTable.NotesEntry.COL_NOTES_TEXT,"19");



//        db.update(TableName, cv, "_id="+id, null);
//        db.close();
    }

}
