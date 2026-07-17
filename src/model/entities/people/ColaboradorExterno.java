package model.entities.people;

import model.core.Persona;
import model.interfaces.Registrable;
import model.valueobjects.Rut;

public class ColaboradorExterno extends Persona implements Registrable {
    private String tipoServicio;
    private String nombreEmpresa;

    // Constructor por defecto
    public ColaboradorExterno() {
        super();
    }

    // Constructor con parámetros
    public ColaboradorExterno(String nombre, Rut rut, String direccion, String tipoServicio, String nombreEmpresa) {
        super(nombre, rut, direccion);
        this.tipoServicio = tipoServicio;
        this.nombreEmpresa = nombreEmpresa;
    }

    // Métodos Getter y Setter
    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    // Sobrescritura del método informacionPersonal
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

    @Override
    public String mostrarResumen() {
        return String.format("[PROVEEDOR EXTERNO] Empresa: %s | Contacto: %s | Servicio provisto: %s%n",
                getNombreEmpresa(), getNombre(), getTipoServicio());
    }
}
