package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

public class PantallaRegistrarVuelo {

    public void mostrarFormulario(Context context, String origen, String destino, int distancia) {
        // Simulación de registro de vuelo
        String mensaje = "Vuelo registrado: " + origen + " → " + destino + " (" + distancia + " km)";
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    // Método de prueba para botones sin datos reales
    public void mostrarFormulario(Context context) {
        // Datos de ejemplo
        mostrarFormulario(context, "DXG", "JFK", 13000);
    }
}

