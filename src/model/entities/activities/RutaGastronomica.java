package model.entities.activities;

import model.core.ServicioTuristico;

public class RutaGastronomica extends ServicioTuristico {
    // Atributo propio de la subclase
    private int numeroParadas;

    // Constructor por defecto
    public RutaGastronomica() {
        super();
        this.numeroParadas = 1;
    }

    // Constructor con parámetros (incluye los de la superclase)
    public RutaGastronomica(String nombre, int duracionHoras, int numeroParadas) {
        super(nombre, duracionHoras); // Invoca al constructor de ServicioTuristico
        this.numeroParadas = numeroParadas;
    }

    // Métodos Getter y Setter para 'numeroParadas'
    public int getNumeroParadas() {
        return numeroParadas;
    }

    public void setNumeroParadas(int numeroParadas) {
        this.numeroParadas = numeroParadas;
    }

    // Sobrescritura del método toString con el formato solicitado
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.toString()).append("\n");
        sb.append("Número de paradas: ").append(numeroParadas).append("\n");
        return sb.toString();
    }
}
