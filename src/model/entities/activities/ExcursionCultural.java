package model.entities.activities;

import model.core.ServicioTuristico;

public class ExcursionCultural extends ServicioTuristico {
    private String lugarHistorico;

    // Constructor por defecto
    public ExcursionCultural() {
        super();
        this.lugarHistorico = "";
    }

    // Constructor con parámetros (incluye los de la superclase)
    public ExcursionCultural(String nombre, int duracionHoras, String lugarHistorico) {
        super(nombre, duracionHoras); // Invoca al constructor de la superclase
        this.lugarHistorico = lugarHistorico;
    }

    // Métodos Getter y Setter para 'lugarHistorico'
    public String getLugarHistorico() {
        return lugarHistorico;
    }

    public void setLugarHistorico(String lugarHistorico) {
        this.lugarHistorico = lugarHistorico;
    }

    // Sobrescritura del método toString integrando el super.toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.toString()).append("\n");
        sb.append("Destino del Tour: ").append(lugarHistorico).append("\n");
        return sb.toString();
    }
}
