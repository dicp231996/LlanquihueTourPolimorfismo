package model.entities.activities;

import model.core.ServicioTuristico;

/**
 * Clase que representa un servicio de ruta gastronómica.
 * Hereda de la clase abstracta {@link ServicioTuristico} y añade propiedades
 * específicas relacionadas con el turismo culinario, como la cantidad de paradas
 * de degustación durante el recorrido.
 */
public class RutaGastronomica extends ServicioTuristico {

    /**
     * Cantidad de paradas, locales o puntos de degustación contemplados a lo largo de la ruta.
     */
    private int numeroParadas;

    /**
     * Constructor por defecto de la clase RutaGastronomica.
     * Invoca al constructor de la superclase e inicializa el número de paradas con un valor por defecto de 1.
     */
    public RutaGastronomica() {
        super();
        this.numeroParadas = 1;
    }

    /**
     * Constructor parametrizado de la clase RutaGastronomica.
     *
     * @param nombreServicio Nombre descriptivo de la ruta gastronómica.
     * @param codigoServicio Código identificador único del servicio.
     * @param duracionHoras  Cantidad de horas estimadas que dura el recorrido.
     * @param numeroParadas  El número de paradas o degustaciones incluidas en el tour.
     */
    public RutaGastronomica(String nombreServicio, String codigoServicio, int duracionHoras, int numeroParadas) {
        super(nombreServicio, codigoServicio, duracionHoras); // Invoca al constructor de ServicioTuristico
        this.numeroParadas = numeroParadas;
    }

    /**
     * Obtiene el número de paradas incluidas en la ruta gastronómica.
     *
     * @return Un entero con la cantidad de paradas programadas.
     */
    public int getNumeroParadas() {
        return numeroParadas;
    }

    /**
     * Establece o modifica el número de paradas de la ruta gastronómica.
     *
     * @param numeroParadas La nueva cantidad de paradas a asignar.
     */
    public void setNumeroParadas(int numeroParadas) {
        this.numeroParadas = numeroParadas;
    }

    /**
     * Genera una cadena de texto estructurada con la información completa de la ruta.
     * Sobrescribe el método de la superclase para concatenar los datos base del servicio
     * con el detalle específico del número de paradas.
     *
     * @return Un {@code String} multilínea con los detalles de la actividad culinaria.
     */
    @Override
    public String informacionTour() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.informacionTour()).append("\n");
        sb.append("Número de paradas: ").append(numeroParadas).append("\n");
        return sb.toString();
    }

    /**
     * Genera un resumen formateado de la ruta gastronómica para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link model.interfaces.Registrable} a través de la herencia.
     *
     * @return Un {@code String} que contiene el código, nombre del servicio y detalles sobre la experiencia de degustación.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[ACTIVIDAD - RUTA GASTRONÓMICA] Código: %s | Experiencia: %s | Detalle: Incluye degustación de productos típicos y visita a productores locales.",
                getCodigoServicio(), getNombreServicio());
    }
}