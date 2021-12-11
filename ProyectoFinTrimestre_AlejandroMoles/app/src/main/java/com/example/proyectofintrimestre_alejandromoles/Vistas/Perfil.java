package com.example.proyectofintrimestre_alejandromoles.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.proyectofintrimestre_alejandromoles.R;

public class Perfil extends AppCompatActivity {
    TextView nombre_view, correo_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombre_view = findViewById(R.id.nombre);
        correo_view = findViewById(R.id.correo_text);

        String nombre = getIntent().getStringExtra("nombre");
        String correo = getIntent().getStringExtra("correo");

        nombre_view.setText(nombre);
        correo_view.setText(correo);

    }
}