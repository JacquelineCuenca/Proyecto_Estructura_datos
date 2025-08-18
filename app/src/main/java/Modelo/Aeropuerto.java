package Modelo;

public class Aeropuerto {
    private String codigo;
    private String nombre;

    // Constructor
    public Aeropuerto(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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
        return codigo + " - " + nombre;
    }

}
