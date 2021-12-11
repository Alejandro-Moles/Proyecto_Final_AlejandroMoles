package com.example.proyectofintrimestre_alejandromoles.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofintrimestre_alejandromoles.Controlador.CreaUsuario;
import com.example.proyectofintrimestre_alejandromoles.Modelo.Usuario;
import com.example.proyectofintrimestre_alejandromoles.R;

public class Pantalla_Inicio extends AppCompatActivity {
    //creo los componentes que tendrá mi vista
    EditText correo;
    EditText contrasenia;
    Button iniciarSesion, registrarse;
    CreaUsuario cU;
    Usuario u1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //relaciono las variables creadas anteriormente con los componentes que ya tengo en la actividad
        correo =findViewById(R.id.text_correo);
        contrasenia = findViewById(R.id.text_contrasenia);
        iniciarSesion = findViewById(R.id.iniciarSesion_btn);
        registrarse = findViewById(R.id.registrarse_btn);

        //instancio un objeto tipo CreaUsuario
        cU = new CreaUsuario(this);

    //metodo que se ejecuta al pulsar el boton iniciarSesion
    iniciarSesion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //compruebo si el usuario que se ha insertado existe en la base de datos o no
            u1 = cU.logearse(correo.getText().toString(),contrasenia.getText().toString());
            if( u1 != null){
                irVentana_Principal(view, u1);
            }else{
                //si no existe mando un mensaje de que no existe
                Toast("Error, el correo no esta registrado");
                correo.setText("");
                contrasenia.setText("");
                correo.requestFocus();
            }
        }
    });

    //metodo que se ejecuta al pulsar el boton registrarse
    registrarse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            irRegistrarse(view);
        }
    });

    }

    //metodo que pasandole un String, te crea un Toast
    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //metodo que hace que te muevas a la pantalla de registrarte
    public void irRegistrarse(View view){
        Intent i = new Intent(this, PantallaRegistrarse.class);
        startActivity(i);
        finish();
    }

    public void irVentana_Principal(View view, Usuario u1){
        Intent i = new Intent(this, Ventana_Principal.class);

        i.putExtra("nombre",u1.getNombre());
        i.putExtra("correo", u1.getCorreo());
        startActivity(i);
        finish();
    }
}