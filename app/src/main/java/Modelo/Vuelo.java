package Modelo;

public class Vuelo {
        private Aeropuerto origen;
        private Aeropuerto destino;
        private int distancia;

        // Constructor
        public Vuelo(Aeropuerto origen, Aeropuerto destino, int distancia) {
            this.origen = origen;
            this.destino = destino;
            this.distancia = distancia;
        }

        // Getters
        public Aeropuerto getOrigen() {
            return origen;
        }

        public Aeropuerto getDestino() {
            return destino;
        }

        public int getDistancia() {
            return distancia;
        }

        // Método toString para representación en texto
        @Override
        public String toString() {
            return "Vuelo de " + origen + " a " + destino + " - Distancia: " + distancia + " km";
        }

}
