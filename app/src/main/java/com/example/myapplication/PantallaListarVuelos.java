package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

public class PantallaListarVuelos {

    public void mostrarVuelos(Context context) {
        // Simulación de listado de vuelos
        String vuelos = "Listado de vuelos:\n" +
                        "DXG → JFK (13000 km)\n" +
                        "DXG → LHR (9200 km)\n" +
                        "JFK → LHR (5500 km)";
        Toast.makeText(context, vuelos, Toast.LENGTH_LONG).show();
    }
}

