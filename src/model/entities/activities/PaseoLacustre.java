package model.entities.activities;

import model.core.ServicioTuristico;

public class PaseoLacustre extends ServicioTuristico {
    private String tipoEmbarcacion;

    // Constructor por defecto
    public PaseoLacustre() {
        super();
        this.tipoEmbarcacion = "Lancha";
    }

    // Constructor con parámetros (incluye los de la superclase)
    public PaseoLacustre(String nombre, int duracionHoras, String tipoEmbarcacion) {
        super(nombre, duracionHoras); // Invoca al constructor de ServicioTuristico
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    // Métodos Getter y Setter para 'tipoEmbarcacion'
    public String getTipoEmbarcacion() {
        return tipoEmbarcacion;
    }

    public void setTipoEmbarcacion(String tipoEmbarcacion) {
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    // Sobrescritura del método toString con el formato solicitado
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.toString()).append("\n");
        sb.append("Tipo de Embarcación: ").append(tipoEmbarcacion).append("\n");
        return sb.toString();
    }
}
