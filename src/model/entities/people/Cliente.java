package model.entities.people;

import model.interfaces.Registrable;
import model.valueobjects.CorreoContacto;
import model.core.Persona;
import model.valueobjects.Rut;
import model.valueobjects.TarjetaCredito;

/**
 * Clase que representa a un cliente dentro del sistema.
 * Extiende de {@link model.core.Persona} para manejar los datos básicos de identidad
 * e implementa la interfaz {@link Registrable} para permitir su inclusión en reportes
 * y listados polimórficos del sistema.
 * Gestiona información de contacto, medios de pago y estado de fidelización del cliente.
 */
public class Cliente extends Persona implements Registrable {

    /**
     * Objeto de valor (Value Object) que almacena y valida el correo electrónico del cliente.
     */
    private CorreoContacto correoContacto;

    /**
     * Objeto de valor (Value Object) que contiene los datos de la tarjeta de crédito asociada al cliente.
     */
    private TarjetaCredito tarjetaAsociada;

    /**
     * Bandera lógica que indica si el cliente posee estatus de "frecuente" para beneficios o descuentos.
     */
    private boolean esClienteFrecuente;

    /**
     * Constructor completo del Cliente.
     *
     * @param nombre             El nombre completo del cliente (pasa a la superclase).
     * @param rut                El identificador único validado (pasa a la superclase).
     * @param direccion          La dirección física del cliente (pasa a la superclase).
     * @param correoContacto     Instancia validada de {@link CorreoContacto}.
     * @param tarjetaAsociada    Instancia validada de {@link TarjetaCredito}.
     * @param esViajeroFrecuente Bandera booleana para lógica de fidelización.
     */
    public Cliente(String nombre, Rut rut, String direccion, CorreoContacto correoContacto, TarjetaCredito tarjetaAsociada, boolean esViajeroFrecuente) {
        super(nombre, rut, direccion);
        this.correoContacto = correoContacto;
        this.tarjetaAsociada = tarjetaAsociada;
        this.esClienteFrecuente = esViajeroFrecuente;
    }

    /**
     * Obtiene el correo de contacto del cliente.
     *
     * @return Una instancia de {@link CorreoContacto}.
     */
    public CorreoContacto getCorreoContacto() {
        return correoContacto;
    }

    /**
     * Establece o modifica el correo de contacto del cliente.
     *
     * @param correoContacto La nueva instancia de {@link CorreoContacto} a asignar.
     */
    public void setCorreoContacto(CorreoContacto correoContacto) {
        this.correoContacto = correoContacto;
    }

    /**
     * Obtiene la tarjeta de crédito asociada al cliente.
     *
     * @return Una instancia de {@link TarjetaCredito}.
     */
    public TarjetaCredito getTarjetaAsociada() {
        return tarjetaAsociada;
    }

    /**
     * Establece o modifica la tarjeta de crédito asociada al cliente.
     *
     * @param tarjetaAsociada La nueva instancia de {@link TarjetaCredito} a asignar.
     */
    public void setTarjetaAsociada(TarjetaCredito tarjetaAsociada) {
        this.tarjetaAsociada = tarjetaAsociada;
    }

    /**
     * Verifica si el cliente tiene estatus de frecuente.
     *
     * @return {@code true} si el cliente es frecuente, {@code false} en caso contrario.
     */
    public boolean isEsClienteFrecuente() {
        return esClienteFrecuente;
    }

    /**
     * Establece o modifica el estatus de cliente frecuente.
     *
     * @param esClienteFrecuente El nuevo estado de fidelización a asignar.
     */
    public void setEsClienteFrecuente(boolean esClienteFrecuente) {
        this.esClienteFrecuente = esClienteFrecuente;
    }

    /**
     * Genera una cadena de texto estructurada con la información personal detallada del cliente.
     * Sobrescribe el método de la superclase añadiendo los datos específicos de cliente
     * (correo, tarjeta y estatus de fidelización).
     *
     * @return Un {@code String} multilínea con toda la información personal y de cliente.
     */
    @Override
    public String informacionPersonal() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.informacionPersonal());

        sb.append("\nCorreo de contacto: ").append(correoContacto);
        sb.append("\nTarjeta: ").append(tarjetaAsociada);
        sb.append("\nAplica descuento de cliente frecuente: ").append(esClienteFrecuente);

        return sb.toString();
    }

    /**
     * Genera un resumen formateado del cliente para su visualización en listados o reportes.
     * Cumple con el contrato de la interfaz {@link Registrable}.
     *
     * @return Un {@code String} que contiene nombre, correo, estatus de frecuencia y número de tarjeta enmascarado.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[CLIENTE] Nombre: %s | %s| Correo: %s | Frecuente: %b | Tarjeta: %s",
                getNombre(), getRut(), correoContacto, esClienteFrecuente, tarjetaAsociada.obtenerNumeroEnmascarado());
    }
}