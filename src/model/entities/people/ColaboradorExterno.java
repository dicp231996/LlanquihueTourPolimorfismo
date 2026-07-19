package model.entities.people;

import model.core.Persona;
import model.interfaces.Registrable;
import model.valueobjects.Rut;

/**
 * Clase que representa a un colaborador externo o proveedor de servicios dentro del sistema.
 * Extiende de {@link Persona} para manejar los datos de identidad básicos e implementa
 * la interfaz {@link Registrable} para permitir su inclusión en los reportes polimórficos del sistema.
 * Gestiona información específica sobre la empresa representada y la naturaleza del servicio que proveen.
 */
public class ColaboradorExterno extends Persona implements Registrable {

    /**
     * Descripción o categoría del servicio que el colaborador externo provee a la empresa.
     */
    private String tipoServicio;

    /**
     * Nombre comercial o razón social de la empresa representada por el colaborador.
     */
    private String nombreEmpresa;

    /**
     * Constructor por defecto de la clase ColaboradorExterno.
     * Invoca al constructor de la superclase sin parámetros.
     */
    public ColaboradorExterno() {
        super();
    }

    /**
     * Constructor parametrizado de la clase ColaboradorExterno.
     *
     * @param nombre        El nombre completo del colaborador (pasa a la superclase).
     * @param rut           El identificador único validado (pasa a la superclase).
     * @param direccion     La dirección física o de contacto (pasa a la superclase).
     * @param tipoServicio  Descripción del servicio que provee el colaborador.
     * @param nombreEmpresa Nombre de la empresa representada.
     */
    public ColaboradorExterno(String nombre, Rut rut, String direccion, String tipoServicio, String nombreEmpresa) {
        super(nombre, rut, direccion);
        this.tipoServicio = tipoServicio;
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Obtiene el tipo de servicio que provee el colaborador.
     *
     * @return Un {@code String} con el tipo de servicio.
     */
    public String getTipoServicio() {
        return tipoServicio;
    }

    /**
     * Establece o modifica el tipo de servicio que provee el colaborador.
     *
     * @param tipoServicio El nuevo tipo de servicio a asignar.
     */
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    /**
     * Obtiene el nombre de la empresa representada por el colaborador.
     *
     * @return Un {@code String} con el nombre de la empresa.
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * Establece o modifica el nombre de la empresa representada.
     *
     * @param nombreEmpresa El nuevo nombre de la empresa a asignar.
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Genera una cadena de texto estructurada con la información personal y profesional del colaborador.
     * Sobrescribe el método de la superclase incluyendo los datos específicos de la empresa y el servicio.
     *
     * @return Un {@code String} multilínea con toda la información relevante del colaborador externo.
     */
    @Override
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();
        // Se recupera la base de la clase padre Persona
        sb.append(super.informacionPersonal());
        // Se anexan los atributos propios del Colaborador Externo
        sb.append("\n Empresa: ").append(nombreEmpresa);
        sb.append("\n Servicio que provee: ").append(tipoServicio);
        return sb.toString();
    }

    /**
     * Genera un resumen formateado del colaborador externo para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link Registrable}.
     *
     * @return Un {@code String} que contiene el nombre de la empresa, el contacto y el servicio provisto.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[PROVEEDOR EXTERNO] Empresa: %s | Contacto: %s | Servicio provisto: %s%n",
                getNombreEmpresa(), getNombre(), getTipoServicio());
    }
}