package com.vanya.cityworld.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vanya.cityworld.R;
import com.vanya.cityworld.models.City;

import io.realm.Realm;

public class AddEditCityActivity extends AppCompatActivity {

    private int cityId ;
    private boolean isCreation ;

    private City city ;
    private Realm realm ;

    private EditText editTextCityName ;
    private EditText editTextCityDescription ;
    private EditText editTextCityLink ;
    private ImageView cityImage ;
    private Button btnPreview ;
    private FloatingActionButton fab ;
    private RatingBar ratingBarCity ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_city);

        realm = Realm.getDefaultInstance() ;
        bindUIReferences() ;

        //Check if it's a creation or edition ;
        if(getIntent().getExtras() != null){
            cityId = getIntent().getExtras().getInt("id") ;
            isCreation = false;
        }else isCreation = true ;

        setActivityTitle() ;

        if(!isCreation){
            city = getCityById(cityId) ;
            bindDataToFields() ;
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEditNewCity() ;
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = editTextCityLink.getText().toString() ;
                if(link.length() > 0){
                    loadImageLinkForPreview(link);
                }
            }
        });


    }

    private void setActivityTitle(){
        String title = "Edit City" ;
        if(isCreation) title = "Create new city" ;
        setTitle(title);
    }

    private City getCityById(int id){
        return realm.where(City.class).equalTo("id", id).findFirst() ;
    }

    private void bindUIReferences(){

        editTextCityName = findViewById(R.id.editTextCityName) ;
        editTextCityDescription = findViewById(R.id.editTextCityDescription) ;
        editTextCityLink = findViewById(R.id.editTextCityImage) ;
        cityImage = findViewById(R.id.imageViewPreview) ;
        btnPreview = findViewById(R.id.buttonPreview) ;
        fab = findViewById(R.id.FABEditingCity) ;
        ratingBarCity = findViewById(R.id.ratingBarCity) ;

    }

    private void bindDataToFields(){
        editTextCityName.setText(city.getName());
        editTextCityDescription.setText(city.getDescription());
        editTextCityLink.setText(city.getBackgroundImage());
        loadImageLinkForPreview(city.getBackgroundImage());
        ratingBarCity.setRating(city.getRate());
    }

    private void loadImageLinkForPreview(String link){
        Picasso.get().load(link).fit().into(cityImage) ;
    }

    private boolean isValidDataForNewCity(){
        if(editTextCityName.getText().toString().length() > 0 &&
            editTextCityDescription.getText().toString().length() > 0 &&
            editTextCityLink.getText().toString().length() > 0){
            return true ;
        }else return false ;
    }
    private void goToMainActivity(){
        Intent intent = new Intent(AddEditCityActivity.this, CityActivity.class) ;
        startActivity(intent) ;
    }

    private void addEditNewCity(){
        if(isValidDataForNewCity()){
            String name = editTextCityName.getText().toString() ;
            String description = editTextCityDescription.getText().toString() ;
            String link = editTextCityLink.getText().toString() ;
            float rate = ratingBarCity.getRating() ;

            final City city = new City(name, description, link, rate) ;

            if(!isCreation) city.setId(cityId);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(city) ;
                }
            });

            goToMainActivity() ;
        }else {
            Toast.makeText(this, "The data is not valid, please check the fields again", Toast.LENGTH_SHORT).show();
        }
    }
}
