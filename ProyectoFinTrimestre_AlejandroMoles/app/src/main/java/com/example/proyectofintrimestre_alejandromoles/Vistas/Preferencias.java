package com.example.proyectofintrimestre_alejandromoles.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.proyectofintrimestre_alejandromoles.Fragments.Ajustes;
import com.example.proyectofintrimestre_alejandromoles.R;

public class Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);



        getSupportFragmentManager().beginTransaction().replace(R.id.AjustesContenedor,new Ajustes()).commit();
    }
}