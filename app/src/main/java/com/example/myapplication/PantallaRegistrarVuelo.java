package com.example.myapplication;

import java.util.Scanner;

public class PantallaRegistrarVuelo {
    private Scanner scanner;

    public PantallaRegistrarVuelo() {
        scanner = new Scanner(System.in);
    }

    public void mostrarFormulario() {
        System.out.println("\n--- Registrar Vuelo ---");
        System.out.print("Código aeropuerto origen: ");
        String origen = scanner.nextLine();
        System.out.print("Código aeropuerto destino: ");
        String destino = scanner.nextLine();
        System.out.print("Distancia (km): ");
        int distancia = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        // Aquí normalmente se enviaría a la lógica de negocio (Persona 3)
        System.out.println("Vuelo registrado: " + origen + " → " + destino + " (" + distancia + " km)");
    }
}

