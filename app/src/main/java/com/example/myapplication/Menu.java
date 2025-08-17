package com.example.myapplication;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private PantallaRegistrarVuelo pantallaRegistrar;
    private PantallaListarVuelos pantallaListar;
    private PantallaBuscarVuelos pantallaBuscar;

    public Menu() {
        scanner = new Scanner(System.in);
        pantallaRegistrar = new PantallaRegistrarVuelo();
        pantallaListar = new PantallaListarVuelos();
        pantallaBuscar = new PantallaBuscarVuelos();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE GESTIÓN DE VUELOS =====");
            System.out.println("1. Registrar vuelo");
            System.out.println("2. Listar vuelos");
            System.out.println("3. Buscar vuelo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            ejecutarOpcion(opcion);
        } while (opcion != 4);
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                pantallaRegistrar.mostrarFormulario();
                break;
            case 2:
                pantallaListar.mostrarVuelos();
                break;
            case 3:
                pantallaBuscar.buscarVuelo();
                break;
            case 4:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}

