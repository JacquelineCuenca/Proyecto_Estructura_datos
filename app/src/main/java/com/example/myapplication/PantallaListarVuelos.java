package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;

import Modelo.Vuelo;


public class PantallaListarVuelos {

    private ControlVuelos control;

    public PantallaListarVuelos(ControlVuelos control) {
        this.control = control;
    }

    public void mostrarVuelos(Context context) {
        StringBuilder sb = new StringBuilder();
        for (Vuelo v : control.listarVuelos()) {
            sb.append(v.toString()).append("\n");
        }

        new AlertDialog.Builder(context)
                .setTitle("Listado de vuelos")
                .setMessage(sb.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
