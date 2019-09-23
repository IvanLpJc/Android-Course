package com.vanya.myform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class MainActivity extends AppCompatActivity {

    private Button btnNext ;
    private EditText editTextName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Icono
        getSupportActionBar().setIcon(R.mipmap.ic_myform);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Instanciamos los botones
        editTextName = findViewById(R.id.editTextName) ;
        btnNext = findViewById(R.id.buttonNext) ;

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString() ;
                if(name != null && !name.isEmpty()){
                    Intent goSecond = new Intent(MainActivity.this, SecondActivity.class);
                    goSecond.putExtra("name", name) ;
                    Toast.makeText(MainActivity.this, "Hola " + name, Toast.LENGTH_SHORT).show();
                    startActivity(goSecond) ;
                }else{
                    SuperActivityToast.create(MainActivity.this, new Style())
                            .setText("Set name")
                            .setDuration(Style.DURATION_LONG)
                            .setFrame(Style.FRAME_KITKAT)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_YELLOW))
                            .setTextColor(getResources().getColor(R.color.colorPrimaryText))
                            .setAnimations(Style.ANIMATIONS_FLY).show();
                }
            }
        });
    }
}
