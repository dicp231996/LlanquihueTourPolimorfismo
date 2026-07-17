package model.entities.people;

import model.interfaces.Registrable;
import model.valueobjects.CorreoContacto;
import model.core.Persona;
import model.valueobjects.Rut;
import model.valueobjects.TarjetaCredito;

public class Cliente extends Persona implements Registrable {

    private CorreoContacto correoContacto;
    private TarjetaCredito tarjetaAsociada;
    private boolean esClienteFrecuente;

    /**
     * Constructor completo del Cliente.
     * @param rut El identificador validado (pasa a la superclase).
     * @param nombre El nombre completo (pasa a la superclase).
     * @param correoContacto Instancia ya validada de Correo.
     * @param esViajeroFrecuente Bandera para lógica de fidelización.
     * @param tarjetaAsociada Instancia ya validada de TarjetaCredito.
     */
    public Cliente(String nombre, Rut rut, String direccion, CorreoContacto correoContacto, TarjetaCredito tarjetaAsociada, boolean esViajeroFrecuente) {
        super(nombre,rut,direccion);
        this.correoContacto = correoContacto;
        this.tarjetaAsociada = tarjetaAsociada;
        this.esClienteFrecuente = esViajeroFrecuente;
    }

    public CorreoContacto getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(CorreoContacto correoContacto) {
        this.correoContacto = correoContacto;
    }

    public TarjetaCredito getTarjetaAsociada() {
        return tarjetaAsociada;
    }

    public void setTarjetaAsociada(TarjetaCredito tarjetaAsociada) {
        this.tarjetaAsociada = tarjetaAsociada;
    }

    public boolean isEsClienteFrecuente() {
        return esClienteFrecuente;
    }

    public void setEsClienteFrecuente(boolean esClienteFrecuente) {
        this.esClienteFrecuente = esClienteFrecuente;
    }

    @Override
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.informacionPersonal());

        sb.append("\nCorreo de contacto: ").append(correoContacto);
        sb.append("\nTarjeta: ").append(tarjetaAsociada);
        sb.append("\nAplica descuento de cliente frecuente: ").append(esClienteFrecuente);

        return sb.toString();
    }

    @Override
    public String mostrarResumen() {
            return String.format("[CLIENTE] Nombre: %s | Correo: %s | Frecuente: %b | Tarjeta: %s",
                    getNombre(), correoContacto, esClienteFrecuente, tarjetaAsociada.obtenerNumeroEnmascarado());
    }
}
