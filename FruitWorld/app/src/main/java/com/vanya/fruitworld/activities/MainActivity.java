package com.vanya.fruitworld.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.vanya.fruitworld.R;

import java.util.ArrayList;
import java.util.List;

import adapters.FruitAdapter;
import models.Fruit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //Elemntos UI
    private ListView listView ;
    private GridView gridView ;
    private FruitAdapter listAdapter ;
    private FruitAdapter gridAdapter ;

    private List<Fruit> fruits ;

    private MenuItem itemListView ;
    private MenuItem itemGridView ;

    private int counter = 0 ;

    private final int SWITCH_TO_LIST_VIEW = 0 ;
    private final int SWITCH_TO_GRID_VIEW = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.enforceIconBar() ;

        this.fruits = getAllFruits() ;

        listView = findViewById(R.id.listView) ;
        gridView = findViewById(R.id.gridView) ;

        listAdapter = new FruitAdapter(this, R.layout.list_item, fruits) ;
        gridAdapter = new FruitAdapter(this, R.layout.grid_item, fruits) ;

        listView.setAdapter(listAdapter);
        gridView.setAdapter(gridAdapter);

        registerForContextMenu(listView);
        registerForContextMenu(gridView);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
        Fruit fruit = fruits.get(position) ;

        if(fruit.getOrigin() == "Unknown"){
            Toast.makeText(this, "Sorry, we don't have info about "+ fruit.getName(), Toast.LENGTH_SHORT).show() ;
        }else {
            Toast.makeText(this, "The best fruit from " + fruit.getOrigin() + " is " + fruit.getName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.action_bar_menu, menu);

        itemListView = menu.findItem(R.id.switchViewToList) ;
        itemGridView = menu.findItem(R.id.switchViewToGrid) ;

        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                fruits.add(new Fruit("Added nº"+(++counter),R.drawable.ic_new_fruit, "Unknown")) ;
                gridAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                return true ;
            case R.id.switchViewToGrid:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                return true ;
            case R.id.switchViewToList:
                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                return true ;
            default:
                return super.onOptionsItemSelected(item) ;

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater = getMenuInflater() ;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo ;

        menu.setHeaderTitle(this.fruits.get(info.position).getName()) ;

        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ;

        switch (item.getItemId()){
            case R.id.deleteItem:
                fruits.remove(info.position) ;
                listAdapter.notifyDataSetChanged();
                gridAdapter.notifyDataSetChanged();
                return  true ;
            default:
                return super.onContextItemSelected(item) ;
        }
    }

    private void enforceIconBar(){
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private List<Fruit> getAllFruits() {
        List<Fruit> list = new ArrayList<Fruit>() {{
            add(new Fruit("Banana", R.drawable.ic_banana, "Gran Canaria"));
            add(new Fruit("Strawberry", R.drawable.ic_strawberry, "Huelva"));
            add(new Fruit("Orange", R.drawable.ic_orange, "Sevilla"));
            add(new Fruit("Apple", R.drawable.ic_apple, "Madrid"));
            add(new Fruit("Cherry", R.drawable.ic_cherry, "Galicia"));
            add(new Fruit("Pear", R.drawable.ic_pear, "Zaragoza"));
            add(new Fruit("Raspberry", R.drawable.ic_raspberry, "Barcelona"));
        }};
        return list;
    }

    private void switchListGridView(int option){
        if(option == SWITCH_TO_LIST_VIEW){
            //Si queremos cambiar a list view, y el list view está en modo invisible...
            if(listView.getVisibility() == View.INVISIBLE){
                //...escondemos el grid view y enseñamos su botón en el menu opciones
                gridView.setVisibility(View.INVISIBLE);
                itemGridView.setVisible(true) ;

                //no olvidamos enseñar el list view, y esconder su boton del menú
                listView.setVisibility(View.VISIBLE);
                itemListView.setVisible(false) ;
            }
        }else if(option == SWITCH_TO_GRID_VIEW){
            if(gridView.getVisibility() == View.INVISIBLE) {
                listView.setVisibility(View.INVISIBLE);
                itemListView.setVisible(true) ;
                listView.setVisibility(View.INVISIBLE);
                itemGridView.setVisible(false) ;
            }
        }
    }
}
