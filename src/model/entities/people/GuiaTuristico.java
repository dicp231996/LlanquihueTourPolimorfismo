package model.entities.people;

import model.core.Persona;
import model.interfaces.Registrable;
import model.valueobjects.Rut;

/**
 * Clase que representa a un guía turístico dentro del sistema.
 * Extiende de {@link Persona} para gestionar los datos de identidad básicos e implementa
 * la interfaz {@link Registrable} para permitir su inclusión en los reportes polimórficos del sistema.
 * Gestiona información específica sobre el nivel de dominio del idioma inglés y la especialidad profesional del guía.
 */
public class GuiaTuristico extends Persona implements Registrable {

    /**
     * Nivel de dominio del idioma inglés del guía (ej. "Básico", "Intermedio", "Avanzado").
     */
    private String nivelIngles;

    /**
     * Área de especialización o competencia técnica del guía (ej. "Alta Montaña", "Historia Local").
     */
    private String especialidad;

    /**
     * Constructor por defecto de la clase GuiaTuristico.
     * Invoca al constructor de la superclase sin parámetros.
     */
    public GuiaTuristico() {
        super();
    }

    /**
     * Constructor parametrizado de la clase GuiaTuristico.
     *
     * @param nombre       El nombre completo del guía (pasa a la superclase).
     * @param rut          El identificador único validado (pasa a la superclase).
     * @param direccion    La dirección física o de contacto (pasa a la superclase).
     * @param nivelIngles  Nivel de dominio del idioma inglés.
     * @param especialidad Especialidad profesional del guía.
     */
    public GuiaTuristico(String nombre, Rut rut, String direccion, String nivelIngles, String especialidad) {
        super(nombre, rut, direccion); // Invoca al constructor de Persona
        this.nivelIngles = nivelIngles;
        this.especialidad = especialidad;
    }

    /**
     * Obtiene el nivel de inglés del guía.
     *
     * @return Un {@code String} con el nivel de inglés.
     */
    public String getNivelIngles() {
        return nivelIngles;
    }

    /**
     * Establece o modifica el nivel de inglés del guía.
     *
     * @param nivelIngles El nuevo nivel de inglés a asignar.
     */
    public void setNivelIngles(String nivelIngles) {
        this.nivelIngles = nivelIngles;
    }

    /**
     * Obtiene la especialidad del guía.
     *
     * @return Un {@code String} con la especialidad.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Establece o modifica la especialidad del guía.
     *
     * @param especialidad La nueva especialidad a asignar.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Genera una cadena de texto estructurada con la información personal y profesional del guía.
     * Sobrescribe el método de la superclase incluyendo los datos específicos de competencia lingüística y especialidad.
     *
     * @return Un {@code String} multilínea con toda la información relevante del guía turístico.
     */
    @Override
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.informacionPersonal());
        sb.append("\n Nivel de Inglés: ").append(nivelIngles);
        sb.append("\n Especialidad: ").append(especialidad);
        return sb.toString();
    }

    /**
     * Genera un resumen formateado del guía para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link Registrable}.
     *
     * @return Un {@code String} que contiene el nombre, la especialidad y el nivel de inglés del guía.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[STAFF INTERNO] Nombre: %s | Especialidad: %s | Nivel de Inglés: %s%n",
                getNombre(), getEspecialidad(), getNivelIngles());
    }
}