package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import java.util.List;

import Modelo.Vuelo;

public class PantallaBuscarVuelos {

    private ControlVuelos control;

    public PantallaBuscarVuelos(ControlVuelos control) {
        this.control = control;
    }

    public void buscarVuelo(Context context) {
        // Ejemplo fijo; más adelante se puede usar EditText para ingresar origen/destino
        String origen = "DXG";
        String destino = "LHR";

        List<Vuelo> resultados = control.buscarVuelo(origen, destino);
        StringBuilder sb = new StringBuilder();
        if (resultados.isEmpty()) {
            sb.append("No se encontraron vuelos");
        } else {
            for (Vuelo v : resultados) {
                sb.append(v.toString()).append("\n");
            }
        }

        new AlertDialog.Builder(context)
                .setTitle("Resultados de búsqueda")
                .setMessage(sb.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}

