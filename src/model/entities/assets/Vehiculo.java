package model.entities.assets;

import model.core.ActivoEmpresa;
import model.interfaces.Registrable;

/**
 * Clase que representa un vehículo dentro de los activos de la empresa.
 * Hereda de {@link ActivoEmpresa} y añade propiedades específicas para el control
 * de transporte, como la patente vehicular. Implementa la interfaz {@link Registrable}
 * para permitir su inclusión en los reportes polimórficos del sistema.
 */
public class Vehiculo extends ActivoEmpresa implements Registrable {

    /**
     * Identificador alfanumérico (patente) del vehículo.
     */
    private String patente;

    /**
     * Constructor por defecto de la clase Vehiculo.
     * Invoca al constructor de la superclase sin parámetros.
     */
    public Vehiculo() {
        super();
    }

    /**
     * Constructor parametrizado de la clase Vehiculo.
     *
     * @param anioCompra Año de adquisición del vehículo.
     * @param vidaUtil   Años de vida útil estimada del vehículo.
     * @param modelo     Modelo comercial o descripción del vehículo.
     * @param patente    Identificador alfanumérico (patente) del vehículo.
     */
    public Vehiculo(int anioCompra, int vidaUtil, String modelo, String patente) {
        super(anioCompra, vidaUtil, modelo); // Invoca al constructor de ActivoEmpresa
        this.patente = patente;
    }

    /**
     * Obtiene la patente del vehículo.
     *
     * @return Un {@code String} con la patente.
     */
    public String getPatente() {
        return patente;
    }

    /**
     * Establece o modifica la patente del vehículo.
     *
     * @param patente La nueva patente a asignar.
     */
    public void setPatente(String patente) {
        this.patente = patente;
    }

    /**
     * Genera una cadena de texto estructurada con los detalles completos del vehículo.
     * Sobrescribe el método de la superclase para integrar la información base del activo
     * con el identificador específico (patente).
     *
     * @return Un {@code String} multilínea con la información del vehículo.
     */
    @Override
    public String informacionActivo() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la información base de la superclase
        sb.append(super.informacionActivo());
        // Se anexa el atributo específico de esta clase hija
        sb.append("\nPatente: ").append(patente);
        return sb.toString();
    }

    /**
     * Genera un resumen formateado del vehículo para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link model.interfaces.Registrable}.
     *
     * @return Un {@code String} con los datos clave del vehículo, incluyendo modelo, patente y años de vida útil.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[Flota terrestre] Modelo: %s | Patente: %s | Vida útil: %d años%n",
                getModelo(), getPatente(), getVidaUtil());
    }
}