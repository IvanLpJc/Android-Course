 package com.vanya.seccion_03.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.vanya.seccion_03.adapters.MyAdapter;
import com.vanya.seccion_03.R;
import com.vanya.seccion_03.models.Movie;

import java.util.ArrayList;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

    private List<Movie> movies;

    private RecyclerView myRecyclerView ;
    private RecyclerView.Adapter myAdapter ;
    private RecyclerView.LayoutManager myLayoutManager ;

    private int counter = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = this.getAllMovies() ;

        myRecyclerView = findViewById(R.id.recyclerView) ;

        myLayoutManager = new LinearLayoutManager(this) ;

        myAdapter = new MyAdapter(movies, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie, int position) {
                //Toast.makeText(MainActivity.this, name + " " + position, Toast.LENGTH_LONG).show() ;
                removeMovie(position);
            }
        });

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu) ;
        return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_name:
                this.addMovie(0) ;
                return true ;
                default:
                    return super.onOptionsItemSelected(item);
        }

     }

     private List<Movie> getAllMovies(){
        return new ArrayList<Movie>(){{
            add(new Movie("Ben hur",R.drawable.benhur));
            add(new Movie("Deadpool",R.drawable.deadpool));
            add(new Movie("Guardians of the Galaxy",R.drawable.guardians));
            add(new Movie("Warcraft",R.drawable.warcraft));
        }};
    }

    private void addMovie(int position) {
        movies.add(position, new Movie("New movie " + (++counter), R.drawable.newmovie)) ;
        myAdapter.notifyItemInserted(position);
        myLayoutManager.scrollToPosition(position);
    }

    private void removeMovie(int position){
        movies.remove(position) ;
        myAdapter.notifyItemRemoved(position);
    }


}
