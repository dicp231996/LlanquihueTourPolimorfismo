package model.core;

import model.valueobjects.Rut;

public abstract class Persona {
    private String nombre;
    private Rut rut;
    private String direccion;

    // Constructor por defecto
    public Persona() {
    }

    // Constructor con parámetros
    public Persona(String nombre, Rut rut, String direccion) {
        this.nombre = nombre;
        this.rut = new Rut();
        this.direccion = direccion;
    }

    // Métodos Getter y Setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos Getter y Setter para 'rut'
    public Rut getRut() {
        return rut;
    }

    public void setRut(Rut rut) {
        this.rut = rut;
    }

    // Métodos Getter y Setter para 'direccion'
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Método propio para estructurar y devolver la información
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();

        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Rut: ").append(rut).append("\n");
        sb.append("Dirección: ").append(direccion);
        return sb.toString();
    }

    public abstract String mostrarResumen();
}
