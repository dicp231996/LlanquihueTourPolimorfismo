package model.entities.activities;

import model.core.ServicioTuristico;

/**
 * Clase que representa un servicio de trekking de alta montaña.
 * Hereda de la clase abstracta {@link ServicioTuristico} y añade propiedades
 * específicas para esta actividad de alta exigencia, como la altura máxima de ascenso.
 */
public class TrekkingAltaMontania extends ServicioTuristico {

    /**
     * Altitud máxima alcanzada durante el trekking, medida en metros sobre el nivel del mar (m.s.n.m.).
     */
    private int alturaAscenso;

    /**
     * Constructor por defecto de la clase TrekkingAltaMontania.
     * Invoca al constructor de la superclase e inicializa la altura de ascenso con 0.
     */
    public TrekkingAltaMontania() {
        super();
        this.alturaAscenso = 0;
    }

    /**
     * Constructor parametrizado de la clase TrekkingAltaMontania.
     *
     * @param nombreServicio Nombre descriptivo de la actividad de trekking.
     * @param codigoServicio Código identificador único del servicio.
     * @param duracionHoras  Cantidad de horas estimadas que dura el ascenso.
     * @param alturaAscenso  La altitud máxima que se alcanza en la ruta.
     */
    public TrekkingAltaMontania(String nombreServicio, String codigoServicio, int duracionHoras, int alturaAscenso) {
        super(nombreServicio, codigoServicio, duracionHoras); // Invoca al constructor de ServicioTuristico
        this.alturaAscenso = alturaAscenso;
    }

    /**
     * Obtiene la altura de ascenso programada para el trekking.
     *
     * @return Un entero con la altitud en m.s.n.m.
     */
    public int getAlturaAscenso() {
        return alturaAscenso;
    }

    /**
     * Establece o modifica la altura de ascenso del trekking.
     *
     * @param alturaAscenso La nueva altitud a asignar.
     */
    public void setAlturaAscenso(int alturaAscenso) {
        this.alturaAscenso = alturaAscenso;
    }

    /**
     * Genera una cadena de texto estructurada con la información completa de la actividad.
     * Sobrescribe el método de la superclase para concatenar los datos base del servicio
     * con el detalle específico de la altura alcanzada.
     *
     * @return Un {@code String} multilínea con los detalles de la actividad de montaña.
     */
    @Override
    public String informacionTour() {
        StringBuilder sb = new StringBuilder();
        // Se obtiene la cadena de la superclase y se concatena el atributo propio
        sb.append(super.informacionTour()).append("\n");
        sb.append("Altura de ascenso: ").append(alturaAscenso).append(" m.s.n.m.\n");
        return sb.toString();
    }

    /**
     * Genera un resumen formateado del trekking para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link model.interfaces.Registrable} a través de la herencia.
     *
     * @return Un {@code String} que contiene el código, nombre de la ruta y advertencias de dificultad técnica.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[ACTIVIDAD - TREKKING] Código: %s | Ruta: %s | Dificultad: Alta | Requiere equipo técnico especial.",
                getCodigoServicio(), getNombreServicio());
    }
}