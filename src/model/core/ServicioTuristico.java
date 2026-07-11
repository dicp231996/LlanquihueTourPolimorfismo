package model.core;

public class ServicioTuristico {
    // Atributos privados para garantizar el encapsulamiento
    private String nombre;
    private int duracionHoras;

    // Constructor por defecto
    public ServicioTuristico() {
    }

    // Constructor con parámetros
    public ServicioTuristico(String nombre, int duracionHoras) {
        this.nombre = nombre;
        this.duracionHoras = duracionHoras;
    }

    // Métodos Getter y Setter para 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos Getter y Setter para 'duracionHoras'
    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String informacionTour() {
        StringBuilder sb = new StringBuilder();
        sb.append("nombre: ").append(nombre).append("\n");
        sb.append("Duracion estimada: ").append(duracionHoras).append(" Horas");
        return sb.toString();
    }
}
