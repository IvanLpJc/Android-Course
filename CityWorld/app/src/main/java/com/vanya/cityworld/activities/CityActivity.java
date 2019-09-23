package com.vanya.cityworld.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.vanya.cityworld.R;
import com.vanya.cityworld.adapters.CityAdapter;
import com.vanya.cityworld.models.City;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CityActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<City>> {

    private Realm realm ;
    private FloatingActionButton fab ;

    private RealmResults<City> cities ;

    private RecyclerView myRecyclerView ;
    private RecyclerView.Adapter myCityAdapter ;
    private RecyclerView.LayoutManager myLayoutManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance() ;

        cities = realm.where(City.class).findAll() ;
        cities.addChangeListener(this);

        cities = realm.where(City.class).findAll() ;

        myRecyclerView = findViewById(R.id.recyclerView) ;
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myLayoutManager = new LinearLayoutManager(this) ;
        myRecyclerView.setLayoutManager(myLayoutManager);

        fab = findViewById(R.id.fabAddCity) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this, AddEditCityActivity.class) ;
                startActivity(intent);
            }
        });

        myCityAdapter = new CityAdapter(cities, R.layout.city_card_item, new CityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(City city, int position) {
                Intent intent = new Intent(CityActivity.this, AddEditCityActivity.class);
                intent.putExtra("id", city.getId());
                startActivity(intent);
            }
        }, new CityAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(City city, int position) {
                showAlertForDeletingCity("Delete city", "Are you sure you want to delete " + city.getName() + "?", position);
            }
        }) ;

        myRecyclerView.setAdapter(myCityAdapter);
        cities.addChangeListener(this);
    }

    //** CRUD Actions **//

    private void deleteCity(final int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                cities.deleteFromRealm(position);
            }
        });
    }

    //** Dialogs **//

    private void showAlertForDeletingCity(String title, String message,final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title) ;
        builder.setMessage(message) ;
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCity(position) ;
                Toast.makeText(CityActivity.this, "It has been deleted successfullly",Toast.LENGTH_LONG).show();
            }
        })
                .setNegativeButton("Cancel", null).show() ;

    }

    @Override
    public void onChange(RealmResults<City> cities) {
        myCityAdapter.notifyDataSetChanged();
    }
}
