package Modelo;

public class Aeropuerto {
    private String codigo;
    private String nombre;
    private double latitud;
    private double longitud;

    // Constructor
    public Aeropuerto(String codigo, String nombre, double latitud, double longitud) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // Método toString para representación en texto
    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + latitud + ", " + longitud + ")";
    }

}
