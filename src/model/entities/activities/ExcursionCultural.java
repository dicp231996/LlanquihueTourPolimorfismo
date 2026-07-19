package model.entities.activities;

import model.core.ServicioTuristico;

/**
 * Clase que representa un servicio de excursión cultural.
 * Hereda de la clase abstracta {@link ServicioTuristico} e incluye características
 * específicas relacionadas con visitas a lugares de interés histórico o patrimonial.
 */
public class ExcursionCultural extends ServicioTuristico {

    /**
     * Nombre del lugar histórico, monumento o zona patrimonial principal a visitar durante la excursión.
     */
    private String lugarHistorico;

    /**
     * Constructor por defecto de la clase ExcursionCultural.
     * Invoca al constructor de la superclase e inicializa el lugar histórico con una cadena vacía.
     */
    public ExcursionCultural() {
        super();
        this.lugarHistorico = "";
    }

    /**
     * Constructor parametrizado de la clase ExcursionCultural.
     *
     * @param nombreServicio Nombre descriptivo de la excursión cultural.
     * @param codigoServicio Código identificador único del servicio.
     * @param duracionHoras  Cantidad de horas estimadas que dura la excursión.
     * @param lugarHistorico El lugar de interés histórico principal a visitar.
     */
    public ExcursionCultural(String nombreServicio, String codigoServicio, int duracionHoras, String lugarHistorico) {
        super(nombreServicio, codigoServicio, duracionHoras); // Invoca al constructor de la superclase
        this.lugarHistorico = lugarHistorico;
    }

    /**
     * Obtiene el lugar histórico asociado a la excursión.
     *
     * @return Un {@code String} con el nombre del lugar histórico.
     */
    public String getLugarHistorico() {
        return lugarHistorico;
    }

    /**
     * Establece o modifica el lugar histórico de la excursión.
     *
     * @param lugarHistorico El nuevo lugar histórico a asignar.
     */
    public void setLugarHistorico(String lugarHistorico) {
        this.lugarHistorico = lugarHistorico;
    }

    /**
     * Genera una cadena de texto estructurada con la información completa de la excursión cultural.
     * Sobrescribe el método de la superclase para concatenar los datos base del tour
     * con el destino histórico específico de esta clase.
     *
     * @return Un {@code String} multilínea con los detalles de la excursión.
     */
    @Override
    public String informacionTour() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.informacionTour()).append("\n");
        sb.append("Destino del Tour: ").append(lugarHistorico).append("\n");
        return sb.toString();
    }

    /**
     * Genera un resumen formateado de la excursión cultural para su visualización en reportes o listados.
     * Cumple con el contrato de la interfaz {@link model.interfaces.Registrable} heredado a través de la superclase.
     *
     * @return Un {@code String} que contiene el código, nombre del servicio y un enfoque descriptivo de la actividad.
     */
    @Override
    public String mostrarResumen() {
        // Se actualiza el llamado a getNombreServicio() y se añade el código de servicio
        return String.format("[ACTIVIDAD - EXCURSIÓN CULTURAL] Código: %s | Recorrido: %s | Enfoque: Patrimonio e historia local | Incluye paradas fotográficas.",
                getCodigoServicio(), getNombreServicio());
    }
}