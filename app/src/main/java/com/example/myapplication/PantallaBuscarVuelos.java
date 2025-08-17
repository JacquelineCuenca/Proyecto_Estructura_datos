package com.example.myapplication;

import java.util.Scanner;

public class PantallaBuscarVuelos {
    private Scanner scanner;

    public PantallaBuscarVuelos() {
        scanner = new Scanner(System.in);
    }

    public void buscarVuelo() {
        System.out.println("\n--- Buscar Vuelo ---");
        System.out.print("Ingrese aeropuerto origen: ");
        String origen = scanner.nextLine();
        System.out.print("Ingrese aeropuerto destino: ");
        String destino = scanner.nextLine();

        // Aquí se llamaría a la lógica de negocio (Wentland)
        System.out.println("Buscando vuelo de " + origen + " a " + destino + "...");
        System.out.println("Ruta encontrada: " + origen + " → " + destino + " (simulado)");
    }
}

