package com.vanya.myform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

public class SecondActivity extends AppCompatActivity {

    //Elementos UI
    private SeekBar seekBarAge ;
    private TextView textViewAge ;
    private Button btnNext ;
    private RadioButton radioButtonGreeter ;
    private RadioButton radioButtonFarewell ;

    //Otros valores
    private String name = "";
    private int age = 18 ;
    private final int MAX_AGE = 60 ;
    private final int MIN_AGE = 16 ;

    //Para compartir
    public static final int GREETER_OPTION = 1 ;
    public static final int FAREWELL_OPTION = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Activamos la flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recogemos el nombre del activity anterior
        Bundle bundle = getIntent().getExtras() ;
        if(bundle != null){
            name = bundle.getString("name") ;
        }

        //Instanciamos los botones
        seekBarAge = findViewById(R.id.seekBarAge) ;
        textViewAge = findViewById(R.id.textViewAge) ;
        btnNext = findViewById(R.id.buttonNextSecond) ;
        radioButtonFarewell = findViewById(R.id.radioButtonFarewell) ;
        radioButtonGreeter = findViewById(R.id.radioButtonGreeter) ;

        //Evento change para el SeekBar
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentAge, boolean fromUser) {
                age = currentAge ;
                textViewAge.setText(age + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Declaramos las restricciones de edad en el evento en que el usuario suelta el seekbar
                age = seekBarAge.getProgress() ;
                textViewAge.setText(age +"");

                if(age > MAX_AGE){
                    SuperActivityToast.create(SecondActivity.this, new Style())
                            .setText("The max age allowed is: "+MAX_AGE+" years old")
                            .setDuration(Style.DURATION_LONG)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY))
                            .setTextColor(getResources().getColor(R.color.colorPrimaryText))
                            .setAnimations(Style.ANIMATIONS_FADE).show();

                    btnNext.setVisibility(View.INVISIBLE);
                }else if(age < MIN_AGE){
                    SuperActivityToast.create(SecondActivity.this, new Style())
                            .setText("The min age allowed is: "+MIN_AGE+" years old")
                            .setDuration(Style.DURATION_LONG)
                            .setFrame(Style.FRAME_LOLLIPOP)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY))
                            .setTextColor(getResources().getColor(R.color.colorPrimaryText))
                            .setAnimations(Style.ANIMATIONS_SCALE).show();
                    btnNext.setVisibility(View.INVISIBLE);
                }else{
                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class) ;
                intent.putExtra("name", name) ;
                intent.putExtra("age", age) ;
                //En funcion del boton: Greeter -> 1 | Farewell -> 2
                int option = (radioButtonGreeter.isChecked()) ? GREETER_OPTION : FAREWELL_OPTION ;
                intent.putExtra("option", option) ;
                startActivity(intent);
                Toast.makeText(SecondActivity.this, "" +seekBarAge.getProgress(), Toast.LENGTH_LONG).show() ;
            }
        });


    }
}
