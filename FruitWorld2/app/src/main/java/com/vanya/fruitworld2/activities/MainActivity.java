package com.vanya.fruitworld2.activities;

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

import com.vanya.fruitworld2.R;
import com.vanya.fruitworld2.adapters.MyFruitAdapter;
import com.vanya.fruitworld2.models.Fruit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //UI Elements
    private ListView listView ;
    private GridView gridView ;
    private MyFruitAdapter adapterListView;
    private MyFruitAdapter adapterGridView;

    //Fruits
    private List<Fruit> fruits ;

    private MenuItem itemListView ;
    private MenuItem itemGridView;

    private int counter = 0 ;

    private final int SWITCH_TO_LIST_VIEW = 0 ;
    private final int SWITH_TO_GRID_VIEW = 1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.forceIcon();

        this.fruits = loadFruits() ;

        this.listView = findViewById(R.id.listViewMode) ;
        this.gridView = findViewById(R.id.gridViewMode) ;

        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        adapterListView = new MyFruitAdapter(this, R.layout.list_item, fruits) ;
        adapterGridView = new MyFruitAdapter(this, R.layout.grid_item,fruits) ;

        listView.setAdapter(adapterListView);
        gridView.setAdapter(adapterGridView);

        registerForContextMenu(listView);
        registerForContextMenu(gridView);
    }

    private void forceIcon() {

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.clickFruit(fruits.get(position));
    }

    private void clickFruit(Fruit fruit){
        //Diferenciamos entre frutas conocidas y no conocidas
        if(fruit.getOrigin().equals("Unknown")){
            Toast.makeText(this, "Sorry, we don't have information about " + fruit.getName(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "The best fruit from " + fruit.getOrigin() + " is " + fruit.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflamos el option menu con nuestro layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        // Después de inflar, recogemos las referencias a los botones que nos interesan
        this.itemListView = menu.findItem(R.id.listView);
        this.itemGridView = menu.findItem(R.id.gridView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Eventos para los botones del option menu
        switch (item.getItemId()){
            case R.id.add_fruit:
                this.addFruit(new Fruit("Added nº " + (++counter), R.mipmap.ic_new_fruit, "Unknown"));
                return true ;
            case R.id.listView:
                this.switchListGridMode(this.SWITCH_TO_LIST_VIEW) ;
                return true ;
            case R.id.gridView:
                this.switchListGridMode(this.SWITH_TO_GRID_VIEW) ;
                return true;
            default:
                return super.onOptionsItemSelected(item) ;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Inflamos el context menu con nuestro layout
        MenuInflater inflater = getMenuInflater() ;
        // Antes de inflar, añadimos el header en función del objeto pinchado
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo ;
        menu.setHeaderTitle(this.fruits.get(info.position).getName()) ;
        // Inflamos
        inflater.inflate(R.menu.context_menu_fruits, menu) ;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo() ;
        switch(item.getItemId()){
            case R.id.delete_fruit:
                this.deleteFruit(info.position);
                return true ;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void switchListGridMode(int mode){
        if(mode == SWITCH_TO_LIST_VIEW){
            if(this.listView.getVisibility() == View.INVISIBLE){
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);

                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false) ;
            }
        }else if(mode == SWITH_TO_GRID_VIEW){
            if(this.gridView.getVisibility() == View.INVISIBLE){
                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true) ;

                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false) ;
            }
        }
    }

    private List<Fruit> loadFruits(){
        List<Fruit> list = new ArrayList<Fruit>(){{
            add(new Fruit("Banana", R.mipmap.ic_banana, "Gran Canaria"));
            add(new Fruit("Strawberry", R.mipmap.ic_strawberry, "Huelva"));
            add(new Fruit("Orange", R.mipmap.ic_orange, "Sevilla"));
            add(new Fruit("Apple", R.mipmap.ic_apple, "Madrid"));
            add(new Fruit("Cherry", R.mipmap.ic_cherry, "Galicia"));
            add(new Fruit("Pear", R.mipmap.ic_pear, "Zaragoza"));
            add(new Fruit("Raspberry", R.mipmap.ic_raspberry, "Barcelona"));
        }};
        return list ;
    }

    private void addFruit(Fruit fruit){
        this.fruits.add(fruit) ;
        this.adapterGridView.notifyDataSetChanged();
        this.adapterListView.notifyDataSetChanged();
    }

    private void deleteFruit(int position){
        this.fruits.remove(position) ;
        this.adapterGridView.notifyDataSetChanged();
        this.adapterListView.notifyDataSetChanged();
    }
}
