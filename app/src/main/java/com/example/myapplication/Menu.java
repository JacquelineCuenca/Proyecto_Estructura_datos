package com.example.myapplication;
import android.content.Context;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private PantallaRegistrarVuelo pantallaRegistrar;
    private PantallaListarVuelos pantallaListar;
    private PantallaBuscarVuelos pantallaBuscar;

    public Menu(ControlVuelos cv) {
        scanner = new Scanner(System.in);
        pantallaRegistrar = new PantallaRegistrarVuelo(cv);
        pantallaListar = new PantallaListarVuelos(cv);
        pantallaBuscar = new PantallaBuscarVuelos(cv);
    }

    public void mostrarMenu(Context context) {
        int opcion = 0;
        do {
            System.out.println("\n===== SISTEMA DE GESTIÓN DE VUELOS =====");
            System.out.println("1. Registrar vuelo");
            System.out.println("2. Listar vuelos");
            System.out.println("3. Buscar vuelo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar buffer
                ejecutarOpcion(opcion, context);
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // limpiar buffer
            }
        } while (opcion != 4);

        scanner.close(); // Cerrar el Scanner al salir
    }

    private void ejecutarOpcion(int opcion, Context context) {
        switch (opcion) {
            case 1:
                pantallaRegistrar.mostrarFormulario(context);
                break;
            case 2:
                pantallaListar.mostrarVuelos(context);
                break;
            case 3:
                pantallaBuscar.buscarVuelo(context);
                break;
            case 4:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
}

