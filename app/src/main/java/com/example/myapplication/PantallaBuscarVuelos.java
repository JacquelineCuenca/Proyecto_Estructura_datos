package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

public class PantallaBuscarVuelos {

    public void buscarVuelo(Context context) {
        // Simulación de búsqueda
        String origen = "DXG";
        String destino = "LHR";
        String mensaje = "Buscando vuelo de " + origen + " a " + destino + "...\n" +
                         "Ruta encontrada: " + origen + " → " + destino + " (simulado)";
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }
}

