package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;

import Modelo.Aeropuerto;
import Modelo.Vuelo;


public class PantallaRegistrarVuelo {

    private ControlVuelos control;

    public PantallaRegistrarVuelo(ControlVuelos control) {
        this.control = control;
    }

    public void mostrarFormulario(Context context) {
        // Ejemplo fijo; más adelante se puede agregar un formulario real
        Aeropuerto origen = new Aeropuerto("DXG", "Daxing");
        Aeropuerto destino = new Aeropuerto("JFK", "John F. Kennedy");
        int distancia = 13000;

        Vuelo vuelo = new Vuelo(origen, destino, distancia);
        control.agregarVuelo(vuelo);

        new AlertDialog.Builder(context)
                .setTitle("Vuelo registrado")
                .setMessage(vuelo.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}

