package com.vanya.seccion_04_realm.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.vanya.seccion_04_realm.adapters.BoardAdapter;
import com.vanya.seccion_04_realm.models.Board;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class BoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>, AdapterView.OnItemClickListener {

    // Creamos una instancia de Realm
    private Realm realm ;


    private FloatingActionButton fab ;
    private BoardAdapter myAdapter ;
    private ListView listView ;
    private RealmResults<Board> boards ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB Realm
        realm = Realm.getDefaultInstance() ;

        boards = realm.where(Board.class).findAll() ;
        boards.addChangeListener(this) ;

        listView = findViewById(R.id.listViewBoard) ;
        myAdapter = new BoardAdapter(this, boards, R.layout.list_view_board_item) ;
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);


        fab = findViewById(R.id.fabAddBoard) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingBoard("Add new Board" , "Set a title for your new board");
            }
        });

        registerForContextMenu(listView);

    }

    /* Events */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_board_activity,menu);

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
        menu.setHeaderTitle(boards.get(info.position).getTitle()) ;
        getMenuInflater().inflate(R.menu.context_menu_board_activity,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ;

        switch (item.getItemId()) {
            case R.id.delete_board:
                deleteBoard(boards.get(info.position)) ;
                return true;
            case R.id.edit_board:
                showAlertForEditingBoard("Edit board","Change de name of the board", boards.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //** CRUD ACTIONS **//
    private void createNewBoard(final String boardName) {

        //

        //Todo cambio en la DB lo hacemos con transacciones, de este modo

        //si hay algún tipo de problema con los datos ese lote no se procesa

        //y evitamos guardar datos corruptos o corromper los existentes

        //

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Creamos un board
                Board board = new Board(boardName) ;
                realm.copyToRealm(board) ;
            }
        });

        /* También podemos hacerlo llamando a

            realm.beginTransaction() ;
            ... Código a ejecutar ...
            realm.commitTransaction() ;

            este código lo podemos usar para pequeños accesos/modificaciones
         */

    }

    private void deleteBoard(final Board board){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                board.deleteFromRealm();
            }
        });
    }

    private void editBoard(final String newName, final Board board){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                board.setTitle(newName);
                realm.copyToRealmOrUpdate(board) ;
            }
        });
    }

    private void deleteAll() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    //** Dialogs **//
    private void showAlertForCreatingBoard(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;

        if(title != null) builder.setTitle(title) ;
        if(message != null) builder.setMessage(message) ;

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_layout_board,null) ;
        builder.setView(viewInflated) ;

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard) ;

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim() ;
                if(boardName.length() > 0) createNewBoard(boardName) ;
                else Toast.makeText(getApplicationContext(), "The name is required to create a board", Toast.LENGTH_LONG).show();
            }
        }) ;
        builder.create().show();
    }

    private void showAlertForEditingBoard(String title, String message,final Board board){

        AlertDialog.Builder builder = new AlertDialog.Builder(this) ;

        if(title != null) builder.setTitle(title) ;
        if(message != null) builder.setMessage(message) ;

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_layout_board,null) ;
        builder.setView(viewInflated) ;

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard) ;
        input.setText(board.getTitle());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim() ;
                if(boardName.length() == 0)
                    Toast.makeText(getApplicationContext(), "The name is required to edit the current board", Toast.LENGTH_LONG).show();

                else if(boardName.equals(board.getTitle()))
                    Toast.makeText(getApplicationContext(), "The name is the same that before", Toast.LENGTH_LONG).show();

                else
                    editBoard(boardName,board);

            }
        }) ;
        builder.create().show();
    }
    @Override
    public void onChange(RealmResults<Board> boards) {
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(BoardActivity.this, NoteActivity.class) ;
        intent.putExtra("id", boards.get(position).getId()) ;
        startActivity(intent);
    }
}
