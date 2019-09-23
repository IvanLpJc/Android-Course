package com.vanya.myform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class ThirdActivity extends AppCompatActivity {

    //Elementos UI
    private ImageButton btnImg ;
    private Button shareButton ;


    //Variables
    private String name ;
    private int age ;
    private int option ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activamos la flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras() ;
        if(bundle != null){
            name = bundle.getString("name") ;
            age = bundle.getInt("age") ;
            option = bundle.getInt("option") ;
        }

        btnImg = findViewById(R.id.confirmButton) ;
        shareButton = findViewById(R.id.buttonShare) ;

        shareButton.setVisibility(View.INVISIBLE);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperActivityToast.create(ThirdActivity.this, new Style())
                        .setText(createMessage(option, age, name))
                        .setDuration(Style.DURATION_LONG)
                        .setFrame(Style.FRAME_KITKAT)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY))
                        .setTextColor(getResources().getColor(R.color.colorPrimaryText))
                        .setAnimations(Style.ANIMATIONS_FLY).show();
                btnImg.setVisibility(View.INVISIBLE);
                shareButton.setVisibility(View.VISIBLE);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShare = new Intent(Intent.ACTION_SEND) ;
                intentShare.setType("plain/text") ;
                intentShare.putExtra(Intent.EXTRA_TEXT,createMessage(option,age,name)) ;
                startActivity(intentShare);
            }
        });
    }


    public String createMessage(int option, int age, String name){
        if(option == SecondActivity.GREETER_OPTION) return ("Hola "+name+ ", ¿Como llevas esos " + age+ " años? #MyForm") ;
        else return ("Espero verte pronto " + name + ", antes de que cumplas " +(age+1) + "... #MyForm") ;

    }
}
