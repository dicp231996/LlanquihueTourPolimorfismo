package model.entities.people;

import model.core.Persona;
import model.interfaces.Registrable;

public class GuiaTuristico extends Persona implements Registrable {
    private String nivelIngles;
    private String especialidad;

    // Constructor por defecto
    public GuiaTuristico() {
        super();
    }

    // Constructor con parámetros (incluye los de la superclase)
    public GuiaTuristico(String nombre, String rut, String direccion, String nivelIngles, String especialidad) {
        super(nombre, rut, direccion); // Invoca al constructor de Persona
        this.nivelIngles = nivelIngles;
        this.especialidad = especialidad;
    }

    // Métodos Getter y Setter para 'nivelIngles'
    public String getNivelIngles() {
        return nivelIngles;
    }

    public void setNivelIngles(String nivelIngles) {
        this.nivelIngles = nivelIngles;
    }

    // Métodos Getter y Setter para 'especialidad'
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // Sobrescritura del método informacionPersonal
    @Override
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();
        // Se recupera la cadena base de la clase padre Persona
        sb.append(super.informacionPersonal());
        // Se anexan los atributos específicos de GuiaTuristico
        sb.append("\n Nivel de Inglés: ").append(nivelIngles);
        sb.append("\n Especialidad: ").append(especialidad);
        return sb.toString();
    }

    @Override
    public void mostarResumen() {
        System.out.println("Persona");
    }
}
