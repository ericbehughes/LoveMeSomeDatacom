package android.lovemesomedatacom;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.lovemesomedatacom.db.NotesDBHelper;
import android.lovemesomedatacom.db.NotesTable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesActivity extends MenuActivity {

    private static final String TAG = "NotesActivity";
    private NotesDBHelper mHelper;
    private ListView mNoteListView;
    private NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mHelper = new NotesDBHelper(this);
        mNoteListView = (ListView) findViewById(R.id.list_notes);
        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
        });



        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText noteEditTextTitle = new EditText(this);
                final EditText noteEditTextMessage =new EditText(this);
                layout.addView(noteEditTextTitle);
                layout.addView(noteEditTextMessage);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("New Note")
                        .setMessage("Please add a note here")
                        .setView(layout)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String note_title = String.valueOf(noteEditTextTitle.getText());
                                String note_text = String.valueOf(noteEditTextMessage.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(NotesTable.NotesEntry.COL_NOTES_TITLE, note_title);
                                values.put(NotesTable.NotesEntry.COL_NOTES_TEXT, note_text);
                                db.insertWithOnConflict(NotesTable.NotesEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteNote(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.note_title);
        String note_title = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(NotesTable.NotesEntry.TABLE,
                NotesTable.NotesEntry.COL_NOTES_TITLE+ " = ?",
                new String[]{note_title});
        db.close();
        updateUI();
    }

    @Override
    public VoiceInteractor getVoiceInteractor() {
        return super.getVoiceInteractor();
    }

    private void updateUI() {
        ArrayList<String> notesList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NotesTable.NotesEntry.TABLE,
                new String[]{NotesTable.NotesEntry._ID, NotesTable.NotesEntry.COL_NOTES_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(NotesTable.NotesEntry.COL_NOTES_TITLE);
            notesList.add(cursor.getString(idx));
        }



        if (mAdapter == null) {
            mAdapter = new NotesAdapter(this, notesList);
            mNoteListView.setAdapter(mAdapter);


        } else {
            //mAdapter.clear();
            mAdapter.addAll(notesList);
            mAdapter.notifyDataSetChanged();
        }



        cursor.close();
        db.close();
    }
}
