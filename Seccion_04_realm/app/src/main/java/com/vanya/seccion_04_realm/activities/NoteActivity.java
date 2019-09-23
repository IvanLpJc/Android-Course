package com.vanya.seccion_04_realm.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vanya.seccion_04_realm.R;
import com.vanya.seccion_04_realm.adapters.NoteAdapter;
import com.vanya.seccion_04_realm.models.Board;
import com.vanya.seccion_04_realm.models.Note;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private ListView listView;
    private FloatingActionButton fab;

    private NoteAdapter myAdapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardID;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        realm = Realm.getDefaultInstance();

        if (getIntent().getExtras() != null) {
            boardID = getIntent().getExtras().getInt("id");
        }

        board = realm.where(Board.class).equalTo("id", boardID).findFirst();
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getTitle());

        listView = findViewById(R.id.listViewNote);
        fab = findViewById(R.id.fabAddNote);
        myAdapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);

        listView.setAdapter(myAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingNote("New note", "Write your new note for "+board.getTitle()+"." );
            }
        });

        registerForContextMenu(listView);

    }

    /* Events */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_note__activity,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all:
                deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo ;
        getMenuInflater().inflate(R.menu.context_menu_note_activity,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ;

        switch (item.getItemId()) {
            case R.id.delete_note:
                deleteNote(notes.get(info.position)); ;
                return true;
            case R.id.edit_note:
                showAlertForEditingNote("Edit note","Change de description of the Note", notes.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //** Dialogs **//
    private void showAlertForCreatingNote(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String note = input.getText().toString().trim();
                if (note.length() > 0) createNewNote(note);
                else
                    Toast.makeText(getApplicationContext(), "Text is required for create a new note", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    private void showAlertForEditingNote(String title, String message,final Note note){

        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;

        if(title != null) builder.setTitle(title) ;
        if(message != null) builder.setMessage(message) ;

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_layout_board,null) ;
        builder.setView(viewInflated) ;

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard) ;
        input.setText(note.getDescription());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteDesccription = input.getText().toString().trim() ;
                if(noteDesccription.length() == 0)
                    Toast.makeText(getApplicationContext(), "The description is required to edit the current note", Toast.LENGTH_LONG).show();

                else if(noteDesccription.equals(board.getTitle()))
                    Toast.makeText(getApplicationContext(), "The description is the same that before", Toast.LENGTH_LONG).show();

                else
                    editNote(noteDesccription,note);

            }
        }) ;
        builder.create().show();
    }

    //** CRUD Actions **//
    private void createNewNote(final String note) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note _note = new Note(note) ;
                realm.copyToRealm(_note) ;
                board.getNotes().add(_note) ;
            }
        });
    }

    private void deleteNote(final Note note){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                note.deleteFromRealm();
            }
        });
    }

    private void editNote(final String newDescription, final Note note){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                note.setDescription(newDescription);
                realm.copyToRealmOrUpdate(note) ;
            }
        });
    }

    private void deleteAll(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                board.getNotes().deleteAllFromRealm() ;
            }
        });
    }

    @Override
    public void onChange(Board board) {
        myAdapter.notifyDataSetChanged();
    }
}