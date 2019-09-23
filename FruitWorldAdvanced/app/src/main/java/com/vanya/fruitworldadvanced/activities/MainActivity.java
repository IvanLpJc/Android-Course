package com.vanya.fruitworldadvanced.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.vanya.fruitworldadvanced.R;
import com.vanya.fruitworldadvanced.adapters.MyAdapter;
import com.vanya.fruitworldadvanced.models.Fruits;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fruits> fruits ;

    private RecyclerView myRecyclerView ;
    private MyAdapter myAdapter ;
    private RecyclerView.LayoutManager myLayoutManager ;
    private int counter = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fruits = this.getAllFruits() ;

        myRecyclerView = findViewById(R.id.recyclerView) ;
        myLayoutManager = new LinearLayoutManager(this) ;

        myAdapter = new MyAdapter(fruits, R.layout.recycler_card_item, this, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Fruits fruit, int position) {
                fruit.addQuantity();
                myAdapter.notifyItemChanged(position);
            }
        });

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addButton:
                int position = fruits.size() ;
                this.fruits.add(new Fruits("New fruit " + (++counter), "New fruit description", R.drawable.plum_bg, R.mipmap.ic_plum, 0) );
                myAdapter.notifyItemInserted(position);
                myLayoutManager.scrollToPosition(position);
                return true ;
                default:
                    return super.onOptionsItemSelected(item);
        }


    }

    private List<Fruits> getAllFruits(){
        return new ArrayList<Fruits>() {{
            add(new Fruits("Strawberry", "Strawberry description", R.drawable.strawberry_bg, R.mipmap.ic_strawberry, 0));
            add(new Fruits("Orange", "Orange description", R.drawable.orange_bg, R.mipmap.ic_orange, 0));
            add(new Fruits("Apple", "Apple description", R.drawable.apple_bg, R.mipmap.ic_apple, 0));
            add(new Fruits("Banana", "Banana description", R.drawable.banana_bg, R.mipmap.ic_banana, 0));
            add(new Fruits("Cherry", "Cherry description", R.drawable.cherry_bg, R.mipmap.ic_cherry, 0));
            add(new Fruits("Pear", "Pear description", R.drawable.pear_bg, R.mipmap.ic_pear, 0));
            add(new Fruits("Raspberry", "Raspberry description", R.drawable.raspberry_bg, R.mipmap.ic_raspberry, 0));
        }};

    }
}
