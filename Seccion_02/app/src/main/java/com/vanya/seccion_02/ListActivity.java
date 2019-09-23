package com.vanya.seccion_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView ;
    private List<String> names ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView) ;

        //Datos a mostrar
        names = new ArrayList<>() ;
        names.add("Alejandro");
        names.add("Fernando");
        names.add("Ruben");
        names.add("Santiago");
        names.add("Alejandro");
        names.add("Fernando");
        names.add("Ruben");
        names.add("Santiago");
        names.add("Alejandro");
        names.add("Fernando");
        names.add("Ruben");
        names.add("Santiago");
        names.add("Alejandro");
        names.add("Fernando");
        names.add("Ruben");
        names.add("Santiago");

        /*
            Otra forma de crear la lista

        List<String> names2 = new ArrayList<String>() {{
            add("") ;
            add("") ;
            add("") ;
            add("") ;
        }} ;*/

        //Necesitamos un adaptador para mostrar los layout de cada elemento
        //Estamos usando un layout predefinido de android. Mas adelante crearemos uno nosotros mismos
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, simple_list_item_1, names) ;

        //Establecemos el adaptador a la lista
        //listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_SHORT).show() ;
            }
        });

        // Enlazamos con nuestro adaptador personalizado
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, names) ;
        listView.setAdapter(myAdapter);
    }
}

