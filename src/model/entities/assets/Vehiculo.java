package model.entities.assets;

import model.core.ActivoEmpresa;
import model.interfaces.Registrable;

public class Vehiculo extends ActivoEmpresa implements Registrable {

    private String patente;

    // Constructor por defecto
    public Vehiculo() {
        super();
    }

    // Constructor con parámetros (incluye los de la superclase)
    public Vehiculo(int anioCompra, int vidaUtil, String modelo, String patente) {
        super(anioCompra, vidaUtil, modelo); // Invoca al constructor de MedioTransporte
        this.patente = patente;
    }

    // Métodos Getter y Setter para 'patente'
    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    // Sobrescritura del método informacionActivo
    @Override
    public String informacionActivo() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la información base de la superclase
        sb.append(super.informacionActivo());
        // Se anexa el atributo específico de esta clase hija
        sb.append("\nPatente: ").append(patente);
        return sb.toString();
        }

    @Override
    public String mostrarResumen() {
        return String.format("[Flota terrestre] Modelo: %s | Patente: %s | Vida útil: %d años%n",
                getModelo(), getPatente(), getAnioCompra());
    }
}
