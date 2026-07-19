package model.entities.activities;

import model.core.ServicioTuristico;

/**
 * Clase que representa un servicio de paseo lacustre o navegación.
 * Hereda de la clase abstracta {@link ServicioTuristico} y añade propiedades
 * específicas para actividades acuáticas, como el tipo de embarcación utilizada.
 */
public class PaseoLacustre extends ServicioTuristico {

    /**
     * Define el tipo de vehículo acuático utilizado para el paseo (ej. "Lancha", "Catamarán", "Velero").
     */
    private String tipoEmbarcacion;

    /**
     * Constructor por defecto de la clase PaseoLacustre.
     * Invoca al constructor de la superclase e inicializa el tipo de embarcación con el valor por defecto "Lancha".
     */
    public PaseoLacustre() {
        super();
        this.tipoEmbarcacion = "Lancha";
    }

    /**
     * Constructor parametrizado de la clase PaseoLacustre.
     *
     * @param nombreServicio  Nombre descriptivo del paseo lacustre.
     * @param codigoServicio  Código identificador único del servicio.
     * @param duracionHoras   Cantidad de horas estimadas que dura la navegación.
     * @param tipoEmbarcacion El tipo de embarcación en la que se realizará el paseo.
     */
    public PaseoLacustre(String nombreServicio, String codigoServicio, int duracionHoras, String tipoEmbarcacion) {
        super(nombreServicio, codigoServicio, duracionHoras); // Invoca al constructor de ServicioTuristico
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    /**
     * Obtiene el tipo de embarcación asignada para el paseo.
     *
     * @return Un {@code String} con el tipo de embarcación.
     */
    public String getTipoEmbarcacion() {
        return tipoEmbarcacion;
    }

    /**
     * Establece o modifica el tipo de embarcación para el paseo.
     *
     * @param tipoEmbarcacion El nuevo tipo de embarcación a asignar.
     */
    public void setTipoEmbarcacion(String tipoEmbarcacion) {
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    /**
     * Genera una cadena de texto estructurada con la información completa del paseo lacustre.
     * Sobrescribe el método de la superclase para concatenar los datos base del servicio
     * con el detalle específico del tipo de embarcación.
     *
     * @return Un {@code String} multilínea con los detalles de la actividad acuática.
     */
    @Override
    public String informacionTour() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.informacionTour()).append("\n");
        sb.append("Tipo de Embarcación: ").append(tipoEmbarcacion).append("\n");
        return sb.toString();
    }

    /**
     * Genera un resumen formateado del paseo lacustre para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link model.interfaces.Registrable} a través de la herencia.
     *
     * @return Un {@code String} que contiene el código, nombre del servicio y advertencias operativas (como la sujeción al clima).
     */
    @Override
    public String mostrarResumen() {
        return String.format("[ACTIVIDAD - PASEO LACUSTRE] Código: %s | Navegación: %s | Experiencia: Vista panorámica desde embarcación | Zarpe sujeto a clima.",
                getCodigoServicio(), getNombreServicio());
    }
}