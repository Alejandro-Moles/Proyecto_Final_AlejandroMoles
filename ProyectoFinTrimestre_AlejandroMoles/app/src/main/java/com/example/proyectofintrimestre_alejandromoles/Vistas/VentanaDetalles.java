package com.example.proyectofintrimestre_alejandromoles.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectofintrimestre_alejandromoles.Modelo.Cartas;
import com.example.proyectofintrimestre_alejandromoles.R;

public class VentanaDetalles extends AppCompatActivity {

    //Creo las variaboes de los elementos que tiene mi vista
    TextView Titulo, descripcion, tipo;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_detalles);
        //instancio las variables de los elementos que tiene mi vista
        imagen = findViewById(R.id.imagen_carta);
        Titulo = findViewById(R.id.titulo);
        descripcion = findViewById(R.id.descripcion);
        tipo = findViewById(R.id.tipo);

        descripcion.setMovementMethod(new ScrollingMovementMethod());

        //rocojo la informacion que pase hacia esta actividad
        Cartas carta = (Cartas) getIntent().getSerializableExtra("obj");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //coloco los datos es sus respectivas posiciones
        Glide.with(getApplicationContext())
                .load(carta.getURL())
                .error(R.drawable.reverso)
               .into(imagen);

        Titulo.setText(carta.getNombre());
        descripcion.setText(carta.getDescripcion());
        tipo.setText(carta.getTipo());
    }
}