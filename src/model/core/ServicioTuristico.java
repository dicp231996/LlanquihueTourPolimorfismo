package model.core;

import model.interfaces.Registrable;

/**
 * Clase abstracta que representa un servicio turístico base ofrecido por la empresa.
 * Define los atributos y comportamientos comunes para cualquier actividad o excursión,
 * como el nombre, el código identificador y la duración.
 * Al implementar la interfaz {@link Registrable}, asegura que todos los servicios derivados
 * puedan ser listados y formateados polimórficamente en los reportes del sistema.
 */
public abstract class ServicioTuristico implements Registrable {

    /**
     * Nombre comercial o representativo del servicio turístico.
     * Su visibilidad es protected para permitir el acceso directo desde las clases hijas.
     */
    protected String nombreServicio;

    /**
     * Código alfanumérico único que identifica al servicio en el sistema (ej. "TRK001").
     * Su visibilidad es protected para permitir el acceso directo desde las clases hijas.
     */
    protected String codigoServicio;

    /**
     * Tiempo estimado de duración de la actividad, expresado en horas.
     */
    private int duracionHoras;

    /**
     * Constructor por defecto de la clase ServicioTuristico.
     * Crea una instancia vacía sin inicializar sus atributos.
     */
    public ServicioTuristico() {
    }

    /**
     * Constructor parametrizado de la clase ServicioTuristico.
     *
     * @param nombreServicio Nombre descriptivo del tour o actividad.
     * @param codigoServicio Código identificador único asociado al servicio.
     * @param duracionHoras  Cantidad de horas estimadas que dura el servicio.
     */
    public ServicioTuristico(String nombreServicio, String codigoServicio, int duracionHoras) {
        this.nombreServicio = nombreServicio;
        this.codigoServicio = codigoServicio;
        this.duracionHoras = duracionHoras;
    }

    /**
     * Obtiene el nombre del servicio turístico.
     *
     * @return Un {@code String} con el nombre del servicio.
     */
    public String getNombreServicio() {
        return nombreServicio;
    }

    /**
     * Establece o modifica el nombre del servicio turístico.
     *
     * @param nombreServicio El nuevo nombre a asignar al servicio.
     */
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    /**
     * Obtiene el código identificador del servicio turístico.
     *
     * @return Un {@code String} con el código del servicio.
     */
    public String getCodigoServicio() {
        return codigoServicio;
    }

    /**
     * Establece o modifica el código identificador del servicio turístico.
     *
     * @param codigoServicio El nuevo código a asignar.
     */
    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    /**
     * Obtiene la duración estimada del servicio en horas.
     *
     * @return Un entero que representa las horas de duración.
     */
    public int getDuracionHoras() {
        return duracionHoras;
    }

    /**
     * Establece o modifica la duración del servicio en horas.
     *
     * @param duracionHoras La nueva cantidad de horas a asignar.
     */
    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    /**
     * Genera una cadena de texto estructurada con la información fundamental del servicio.
     * Formatea los datos incluyendo el nombre, el código y la duración estimada.
     *
     * @return Un {@code String} multilínea con los datos básicos del tour o actividad.
     */
    public String informacionTour() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombreServicio).append("\n");
        sb.append("Código: ").append(codigoServicio).append("\n");
        sb.append("Duración estimada: ").append(duracionHoras).append(" Horas");
        return sb.toString();
    }
}