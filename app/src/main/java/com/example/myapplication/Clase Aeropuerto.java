package com.example.myapplication;

public class Aeropuerto {
    private String codigo;
    private String nombre;

    public Aeropuerto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}

