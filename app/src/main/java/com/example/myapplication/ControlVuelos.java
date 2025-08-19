package com.example.myapplication;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import Modelo.Aeropuerto;
import Modelo.Vuelo;
import java.text.ParseException;

public class ControlVuelos {

    private List<Aeropuerto> aeropuertos;
    private List<Vuelo> vuelos;

    public ControlVuelos() {
        aeropuertos = new ArrayList<>();
        vuelos = new ArrayList<>();
        inicializarEjemplo();
    }

    private void inicializarEjemplo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Aeropuerto dxg = new Aeropuerto("DXG", "Daxing");
        Aeropuerto jfk = new Aeropuerto("JFK", "John F. Kennedy");
        Aeropuerto lhr = new Aeropuerto("LHR", "Heathrow");

        aeropuertos.add(dxg);
        aeropuertos.add(jfk);
        aeropuertos.add(lhr);
        try {
            vuelos.add(new Vuelo(dxg, jfk, dateFormat.parse("2023-10-01T10:00:00"), dateFormat.parse("2023-10-01T14:00:00")));
            vuelos.add(new Vuelo(dxg, lhr, dateFormat.parse("2023-10-01T15:00:00"), dateFormat.parse("2023-10-01T19:00:00")));
            vuelos.add(new Vuelo(jfk, lhr, dateFormat.parse("2023-10-02T08:00:00"), dateFormat.parse("2023-10-02T12:00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
        if (!aeropuertos.contains(vuelo.getPartida())) {
            aeropuertos.add(vuelo.getPartida());
        }
        if (!aeropuertos.contains(vuelo.getDestino())) {
            aeropuertos.add(vuelo.getDestino());
        }
    }

    public List<Vuelo> listarVuelos() {
        return vuelos;
    }

    public List<Vuelo> buscarVuelo(String origenCodigo, String destinoCodigo) {
        List<Vuelo> resultado = new ArrayList<>();
        for (Vuelo v : vuelos) {
            if (v.getPartida().getCodigo().equalsIgnoreCase(origenCodigo) &&
                    v.getDestino().getCodigo().equalsIgnoreCase(destinoCodigo)) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
