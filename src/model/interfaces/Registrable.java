package model.interfaces;

/**
 * Interfaz que define el contrato para todas las entidades del sistema que necesitan
 * ser listadas o reportadas. Cualquier clase que implemente esta interfaz garantiza
 * que puede proporcionar un resumen textual de su estado actual, permitiendo el uso
 * de polimorfismo en las clases de gestión y generación de reportes.
 */
public interface Registrable {

    /**
     * Genera un resumen textual de la entidad que implementa esta interfaz.
     * Este método es utilizado para mostrar información consistente en reportes,
     * inventarios o interfaces de usuario.
     *
     * @return Un {@code String} que contiene un resumen formateado de la entidad.
     */
    String mostrarResumen();
}