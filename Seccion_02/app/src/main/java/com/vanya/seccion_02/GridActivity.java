package com.vanya.seccion_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private List<String> names ;
    private GridView gridView ;

    private MyAdapter myAdapter ;

    private int counter = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gridView = findViewById(R.id.gridView) ;

        //Datos a mostrar
        names = new ArrayList<>() ;
        names.add("Alejandro");
        names.add("Fernando");
        names.add("Ruben");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GridActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_SHORT).show() ;
            }
        });

        // Enlazamos con nuestro adaptador personalizado
        myAdapter = new MyAdapter(this, R.layout.grid_item, names) ;
        gridView.setAdapter(myAdapter);

        registerForContextMenu(gridView);
    }
    //Esto lo necesitamos para inflar el boton en el tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }
    //Manejamos eventos click en el menú de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_item:
                //Añadimos nuevo nombre
                this.names.add("Added nº " +(++counter)) ;
                //Ahora tenemos que avisar al adaptador para que se refresque
                this.myAdapter.notifyDataSetChanged();
                return true ;
             default:
                 return super.onOptionsItemSelected(item) ;
        }
    }

    //Inflamos el layout del context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater() ;

        //Tenemos que hacer referencia al menu
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo ;
        menu.setHeaderTitle(this.names.get(info.position));
        inflater.inflate(R.menu.context_menu, menu);


    }
    //Manejamos eventos del context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //Tenemos que hacer referencia al menu
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ;

        switch (item.getItemId()){
            case R.id.deleteItem:
                //Eliminamos el elemento
                this.names.remove(info.position) ;
                Toast.makeText(GridActivity.this, "Item removed", Toast.LENGTH_SHORT).show();
                this.myAdapter.notifyDataSetChanged();
                return true ;
            default:
                return super.onContextItemSelected(item) ;
        }
    }
}
