package com.example.proyectofintrimestre_alejandromoles.Modelo;

import java.io.Serializable;

public class Cartas implements Serializable {

    String nombre, URL, tipo, descripcion;

    public Cartas(String nombre, String URL, String tipo, String descripcion){
        this.nombre = nombre;
        this.URL = URL;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
