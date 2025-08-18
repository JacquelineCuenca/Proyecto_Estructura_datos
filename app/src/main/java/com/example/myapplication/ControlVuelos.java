package com.example.myapplication;


import java.util.ArrayList;
import java.util.List;

import Modelo.Aeropuerto;
import Modelo.Vuelo;

public class ControlVuelos {

    private List<Aeropuerto> aeropuertos;
    private List<Vuelo> vuelos;

    public ControlVuelos() {
        aeropuertos = new ArrayList<>();
        vuelos = new ArrayList<>();
        inicializarEjemplo();
    }

    private void inicializarEjemplo() {
        Aeropuerto dxg = new Aeropuerto("DXG", "Daxing");
        Aeropuerto jfk = new Aeropuerto("JFK", "John F. Kennedy");
        Aeropuerto lhr = new Aeropuerto("LHR", "Heathrow");

        aeropuertos.add(dxg);
        aeropuertos.add(jfk);
        aeropuertos.add(lhr);

        vuelos.add(new Vuelo(dxg, jfk, 13000));
        vuelos.add(new Vuelo(dxg, lhr, 9200));
        vuelos.add(new Vuelo(jfk, lhr, 5500));
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
        if (!aeropuertos.contains(vuelo.getOrigen())) {
            aeropuertos.add(vuelo.getOrigen());
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
            if (v.getOrigen().getCodigo().equalsIgnoreCase(origenCodigo) &&
                v.getDestino().getCodigo().equalsIgnoreCase(destinoCodigo)) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
