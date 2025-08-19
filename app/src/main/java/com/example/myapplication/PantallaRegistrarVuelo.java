package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.text.ParseException;

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
        Vuelo vuelo = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            vuelo = new Vuelo(origen, destino, dateFormat.parse("2023-10-01T10:00:00"), dateFormat.parse("2023-10-01T14:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        control.agregarVuelo(vuelo);

        if (vuelo != null) {
            control.agregarVuelo(vuelo);
            new AlertDialog.Builder(context)
                    .setTitle("Vuelo registrado")
                    .setMessage(vuelo.toString())
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("No se pudo registrar el vuelo.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}

