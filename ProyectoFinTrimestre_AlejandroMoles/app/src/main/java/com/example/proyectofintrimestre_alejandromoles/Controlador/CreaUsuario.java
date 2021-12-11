package com.example.proyectofintrimestre_alejandromoles.Controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectofintrimestre_alejandromoles.Modelo.Usuario;

import java.util.ArrayList;

public class CreaUsuario {
    //creo los atributos que tendra esta clase
    Context context;
    

    SQLiteDatabase sql;
    String BD ="USUARIOS";
    //creo la sentencia para crear la tabla que voy a utilizar en este proyecto
    String sentencia = "create table if not exists usuarios(correo text primary key, contrasenia text, nombre text)";

    //creo el contructor, pasandole un context
    public CreaUsuario(Context context){
        this.context = context;
        //creo la base de datos
        sql = context.openOrCreateDatabase(BD,context.MODE_PRIVATE,null);
        //ejecuto la sentencia para crear la tabla
        sql.execSQL(sentencia);

    }

    //metodo que inserta el usuario el cual ha sido pasado por parametros como objeto y te devuelve un booleano indicando si se ha podido realizar el registro
    public boolean insertarUsu(Usuario usu){
            ContentValues cv = new ContentValues();
            cv.put("correo", usu.getCorreo());
            cv.put("contrasenia", usu.getContrasenia());
            cv.put("nombre", usu.getNombre());
            return (sql.insert("usuarios", null, cv)>0);

    }

    // metodo que comprueba si el correo y la contraseña que se ha pasado es la que se encuentra en la base de datos
    //realizando un cursor, si la comtraseña es la que esta asignada al correo, entonces devuelve la variable comprobacion
    public Usuario logearse(String correo, String contrasenia){
        Usuario u1 = null;
        Cursor cr = sql.rawQuery("select * from usuarios", null);
        if(cr !=null&&cr.moveToFirst()){
            do{
                if(cr.getString(0).equals(correo)&&cr.getString(1).equals(contrasenia)){
                    u1 = new Usuario();
                    u1.setNombre(cr.getString(2));
                    u1.setCorreo(correo);
                    u1.setContrasenia(contrasenia);
                }
            }while(cr.moveToNext());
        }
        return u1;
    }
}
