package Modelo;

import java.util.Date;

public class Vuelo {
    private Aeropuerto partida;
    private Aeropuerto destino;
    private Date horaInicio;
    private Date horaFin;


    // Constructor
    public Vuelo(Aeropuerto partida, Aeropuerto destino, Date horaInicio, Date horaFin) {
        this.partida = partida;
        this.destino = destino;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters
    public Aeropuerto getOrigen() {
        return origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }
    public Date getHoraFin() {
        return horaFin;
    }

    // Método toString para representación en texto
    @Override
    public String toString() {
        return partida + " -> " + destino + " | " + horaInicio + " - " + horaFin;
    }

}
