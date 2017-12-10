package android.lovemesomedatacom.notes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.lovemesomedatacom.R;
import android.lovemesomedatacom.adapters.NotesAdapter;
import android.lovemesomedatacom.business.MenuActivity;
import android.lovemesomedatacom.db.NotesDBHelper;
import android.lovemesomedatacom.db.NotesTable;
import android.lovemesomedatacom.entities.Note;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesActivity extends MenuActivity {

    private final String TAG = "NotesActivity";
    private NotesDBHelper mHelper;
    private ListView mNoteListView;
    private NotesAdapter mAdapter;
    private  ArrayList<Note> notesList = new ArrayList<>();
    private boolean dialog_showing = false;
    private String note_title;
    private String note_message;
    private  EditText noteEditTextTitle;
    private  EditText noteEditTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){

            if (savedInstanceState.getBoolean("dialog_showing", false)){
                note_title = savedInstanceState.getString(getResources().getString(R.string.new_note_str), "");
                note_message = savedInstanceState.getString(getResources().getString(R.string.new_note_body),"" );
                showAddNoteDialog();
            }
        }

        setContentView(R.layout.activity_notes);
        mNoteListView = (ListView) findViewById(R.id.list_notes);
        mAdapter = new NotesAdapter(this, notesList);
        mNoteListView.setAdapter(mAdapter);
        this.setTitle(R.string.notes_activity_title);
    }

    /**
     * custom button in the menu bar and creates a small dialog for the user
     * to enter a note.
     *
     * inserts into the DB and calls updateUI once again
     *
     * this code snippet was taken from the tutorial
     * https://www.sitepoint.com/starting-android-development-creating-todo-app/
     * however I did have to make changes since in this tutorial there is only 1
     * line for them to enter text while mine is multi line if you increase the length of the
     * string
     */
    private void showAddNoteDialog() {
        dialog_showing = true;
        Log.d(TAG, "action_add_task");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        noteEditTextTitle = new EditText(this);
        noteEditTextMessage = new EditText(this);

        if (note_title != null && note_title.length() > 0)
            noteEditTextTitle.setText(note_title);

        if (note_message != null && note_message.length() >0)
            noteEditTextMessage.setText(note_message);

        noteEditTextMessage.setScroller(new Scroller(getApplicationContext()));
        noteEditTextMessage.setVerticalScrollBarEnabled(true);
        layout.addView(noteEditTextTitle);
        layout.addView(noteEditTextMessage);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.new_note_str))
                .setMessage(getResources().getString(R.string.new_note_body))
                .setView(layout)
                // on click listener for little dialog and save text to db
                .setPositiveButton(R.string.add_str, new DialogInterface.OnClickListener() {
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
                        dialog_showing = false;
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }


    /**
     * overriden to add + button the toolbar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.action_add_task:
                showAddNoteDialog();


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save the state so that it can be restored in onCreate or onRestoreInstanceState
        state.putBoolean("dialog_showing", dialog_showing);

        if (noteEditTextTitle != null && noteEditTextMessage != null){
            state.putString(getResources().getString(R.string.new_note_str),
                    String.valueOf(noteEditTextTitle.getText().toString()));
            state.putString(getResources().getString(R.string.new_note_body),
                    String.valueOf(noteEditTextMessage.getText().toString()));
        }

    }

    /**
     * this code is a little hacky on how I retrieve the title going through the parent
     * of the linear layout.  However it retrieves the title for the currently selected note
     * and deletes it using the delete button
     * @param view
     */
    public void deleteNote(View view) {
        View parent = (View) view.getParent();
        TextView tv = (TextView)((LinearLayout) parent).getChildAt(0);
        TextView notesTextView = (TextView) tv.findViewById(R.id.note_title);
        ViewParent vp = parent.getParent();
        LinearLayout ll = (LinearLayout)vp;
        TextView tvv = (TextView)ll.getChildAt(0);
        String note_title = tvv.getText().toString();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Log.d(TAG, "db opened");
        db.delete(NotesTable.NotesEntry.TABLE,
                NotesTable.NotesEntry.COL_NOTES_TITLE+ " = ?",
                new String[]{note_title});
        db.close();
        Log.d(TAG, "db closed");
        updateUI();
    }


    /**
     * gets readable db and fetches both columns to display notes
     * updates adapter and closes connection
     */
    private void updateUI() {

        mAdapter.clear();
        mHelper = new NotesDBHelper(this);

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(NotesTable.NotesEntry.TABLE,
                new String[]{NotesTable.NotesEntry._ID, NotesTable.NotesEntry.COL_NOTES_TITLE,
                        NotesTable.NotesEntry.COL_NOTES_TEXT},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(NotesTable.NotesEntry.COL_NOTES_TITLE);
            Note n = new Note();
            n.setTitle(cursor.getString(idx));
            idx = cursor.getColumnIndex(NotesTable.NotesEntry.COL_NOTES_TEXT);
            String text = cursor.getString(idx);
            n.setText(text);
            notesList.add(n);
        }


        mAdapter.addAll(notesList);
        mAdapter.notifyDataSetChanged();
        mNoteListView.setAdapter(mAdapter);

        mNoteListView.invalidateViews();

        cursor.close();
        db.close();
    }
}
