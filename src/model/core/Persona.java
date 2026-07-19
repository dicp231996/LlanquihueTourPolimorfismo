package model.core;

import model.valueobjects.Rut;

/**
 * Clase abstracta que representa a una persona genérica dentro del sistema.
 * Agrupa los atributos fundamentales y comunes que comparten todas las personas
 * (como clientes, guías turísticos o colaboradores), tales como el nombre,
 * el RUT y la dirección. Al ser abstracta, requiere ser heredada por clases concretas.
 */
public abstract class Persona {

    /**
     * Nombre completo de la persona.
     */
    private String nombre;

    /**
     * Objeto de valor (Value Object) que representa el Rol Único Tributario (RUT) de la persona.
     */
    private Rut rut;

    /**
     * Dirección de residencia o contacto físico de la persona.
     */
    private String direccion;

    /**
     * Constructor por defecto de la clase Persona.
     * Inicializa una instancia vacía sin asignar valores a sus atributos.
     */
    public Persona() {
    }

    /**
     * Constructor parametrizado de la clase Persona.
     *
     * @param nombre    Nombre completo a asignar a la persona.
     * @param rut       Objeto {@link Rut} que representa la identificación de la persona.
     *                  (Nota: En la implementación actual, se inicializa un nuevo objeto Rut por defecto).
     * @param direccion Dirección física o de contacto de la persona.
     */
    public Persona(String nombre, Rut rut, String direccion) {
        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return Un {@code String} con el nombre completo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece o modifica el nombre de la persona.
     *
     * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el RUT de la persona.
     *
     * @return Un objeto {@link Rut} que representa la identificación de la persona.
     */
    public Rut getRut() {
        return rut;
    }

    /**
     * Establece o modifica el RUT de la persona.
     *
     * @param rut El nuevo objeto {@link Rut} a asignar.
     */
    public void setRut(Rut rut) {
        this.rut = rut;
    }

    /**
     * Obtiene la dirección de la persona.
     *
     * @return Un {@code String} con la dirección registrada.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece o modifica la dirección de la persona.
     *
     * @param direccion La nueva dirección a asignar.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Genera una cadena de texto estructurada con los datos personales básicos.
     * Formatea la información incluyendo el nombre, RUT y dirección.
     *
     * @return Un {@code String} multilínea con la información personal.
     */
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Rut: ").append(rut).append("\n");
        sb.append("Dirección: ").append(direccion);
        return sb.toString();
    }

    /**
     * Método abstracto que obliga a las subclases a implementar una forma de mostrar
     * un resumen específico de su información.
     *
     * @return Un {@code String} con el resumen de los datos de la entidad concreta.
     */
    public abstract String mostrarResumen();
}