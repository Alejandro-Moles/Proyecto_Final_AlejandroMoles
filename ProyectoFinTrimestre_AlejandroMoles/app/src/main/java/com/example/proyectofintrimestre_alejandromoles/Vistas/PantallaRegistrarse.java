package com.example.proyectofintrimestre_alejandromoles.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofintrimestre_alejandromoles.Controlador.CreaUsuario;
import com.example.proyectofintrimestre_alejandromoles.Modelo.Usuario;
import com.example.proyectofintrimestre_alejandromoles.R;
import com.example.proyectofintrimestre_alejandromoles.Vistas.Pantalla_Inicio;

public class PantallaRegistrarse extends AppCompatActivity {
    //creo los componentes que tendrá mi vista
    EditText correo, nombre,contrasenia, contrasenia2;
    Button registrarse, cancelar;
    CreaUsuario cU;
    private Boolean comprobar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrarse);
        //relaciono las variables creadas anteriormente con los componentes que ya tengo en la actividad
        correo = findViewById(R.id.text_correoreg);
        contrasenia = findViewById(R.id.text_contraseniareg);
        contrasenia2 = findViewById(R.id.text_contraseniareg2);
        registrarse = findViewById(R.id.Registrarse);
        nombre = findViewById(R.id.text_Nombre);
        cancelar = findViewById(R.id.cancelar_btn);
        //instancio un objeto tipo CreaUsuario
        cU = new CreaUsuario(this);

        //metodo que se ejecuta al cambiar el foco del textfield de correo
        correo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //si la caja de texto no esta vacia entonces ejecuto el metodo validaremail
                if(!correo.getText().toString().isEmpty()){
                    if(!validarEmail(correo.getText().toString())){
                        correo.setText("");
                        correo.setHint("Error, correo no valido");
                        correo.setHintTextColor(Color.RED);
                    }
                }
            }
        });

        //metodo que se ejecuta al presionar el boton registrarse
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comprueba si los campos estan vacios
                if(nombre.getText().toString().isEmpty() || correo.getText().toString().isEmpty() || contrasenia.getText().toString().isEmpty() || contrasenia2.getText().toString().isEmpty()){
                    Toast("Error, introduzca los campos para el registro");
                }else{
                    //comprueba si las dos contraseñas que se tienen que introducir son iguales
                   if(!String.valueOf(contrasenia.getText()).equalsIgnoreCase(String.valueOf(contrasenia2.getText()))){
                        Toast("Error, introduzca las dos contraseñas iguales");
                   }else{
                       //ejecuta el metodo registrar
                       registrar(view);
                       if(comprobar){
                           irPrincipal(view);
                           finish();
                       }else{
                           nombre.setText("");
                           correo.setText("");
                           contrasenia.setText("");
                           contrasenia2.setText("");
                           nombre.requestFocus();
                       }
                   }
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irPrincipal(view);
                finish();
            }
        });


    }

    public void irPrincipal(View view){
        Intent i = new Intent(this, Pantalla_Inicio.class);
        startActivity(i);
    }

    //metodo que pasandole un String, te crea un Toast
    public void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    public void registrar(View view){
        //hago un String por cada uno de los datos que obtengo mediante los textfield
        String correos = correo.getText().toString();
        String contrasenias = contrasenia2.getText().toString();
        String nombres = nombre.getText().toString();

        //creo un objeto usuario
        Usuario u1 = new Usuario();
        //establezco los campos del objeto usuario con lpos Strings que contienen los datos
        u1.setCorreo(correos);
        u1.setContrasenia(contrasenias);
        u1.setNombre(nombres);

        //si el metodo de CreUsuario me retorna true, quiere decir que se ha realizado de una manera correcta el registro
        if(cU.insertarUsu(u1)){
            Toast("Registro realizado correctamente");
            comprobar = true;
        }else{
            Toast("Error, correo ya registrado");
            comprobar = false;
        }
    }


    private boolean validarEmail(String correos){
        //si el String que le paso no tiene el formato de un email convencional entonces me realiza un Toasty me devuelve un falso
        if(!Patterns.EMAIL_ADDRESS.matcher(correos).matches()){
            return false;
        }else{
            return true;
        }
    }
}