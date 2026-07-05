package model.entities;

import model.core.ServicioTuristico;

public class TrekkingAltaMontania extends ServicioTuristico {
    // Atributo propio de la subclase (ej. metros de altitud a alcanzar)
    private int alturaAscenso;

    // Constructor por defecto
    public TrekkingAltaMontania() {
        super();
        this.alturaAscenso = 0;
    }

    // Constructor con parámetros (incluye los de la superclase)
    public TrekkingAltaMontania(String nombre, int duracionHoras, int alturaAscenso) {
        super(nombre, duracionHoras); // Invoca al constructor de ServicioTuristico
        this.alturaAscenso = alturaAscenso;
    }

    // Métodos Getter y Setter para 'alturaAscenso'
    public int getAlturaAscenso() {
        return alturaAscenso;
    }

    public void setAlturaAscenso(int alturaAscenso) {
        this.alturaAscenso = alturaAscenso;
    }

    // Sobrescritura del método toString con el formato unificado
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.toString()).append("\n");
        sb.append("Altura de ascenso: ").append(alturaAscenso).append(" m.s.n.m.\n");
        return sb.toString();
    }
}
