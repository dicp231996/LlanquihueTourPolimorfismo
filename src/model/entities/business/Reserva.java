package model.entities.business;

import model.core.ServicioTuristico;
import model.entities.people.Cliente;
import model.interfaces.Registrable;

import java.time.LocalDate;

/**
 * Clase que representa una transacción de reserva de un servicio turístico.
 * Actúa como un vínculo entre un {@link Cliente} y un {@link ServicioTuristico},
 * registrando datos clave como el número de orden de compra y la fecha de la reserva.
 * Implementa la interfaz {@link Registrable} para permitir su inclusión en reportes polimórficos.
 */
public class Reserva implements Registrable {

    /**
     * Identificador único de la orden de compra para la reserva.
     */
    private String numeroOrdenCompra;

    /**
     * Fecha en la que se ha efectuado la reserva.
     */
    private LocalDate fechaReserva;

    /**
     * Instancia del cliente que ha realizado la reserva.
     */
    private Cliente cliente;

    /**
     * Instancia del servicio turístico que ha sido contratado en esta reserva.
     */
    private ServicioTuristico servicioContratado;

    /**
     * Constructor parametrizado de la clase Reserva.
     *
     * @param numeroOrdenCompra  El identificador único de la orden de compra.
     * @param fechaReserva       La fecha en la que se realiza la reserva.
     * @param cliente            El {@link Cliente} que solicita el servicio.
     * @param servicioContratado El {@link ServicioTuristico} que se está reservando.
     */
    public Reserva(String numeroOrdenCompra, LocalDate fechaReserva, Cliente cliente, ServicioTuristico servicioContratado) {
        this.numeroOrdenCompra = numeroOrdenCompra;
        this.fechaReserva = fechaReserva;
        this.cliente = cliente;
        this.servicioContratado = servicioContratado;
    }

    /**
     * Obtiene el número de orden de compra de la reserva.
     *
     * @return Un {@code String} con el identificador de la orden.
     */
    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    /**
     * Obtiene la fecha en la que se realizó la reserva.
     *
     * @return Un objeto {@link LocalDate} con la fecha de la reserva.
     */
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Obtiene el cliente asociado a la reserva.
     *
     * @return Una instancia de {@link Cliente}.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Obtiene el servicio turístico contratado en esta reserva.
     *
     * @return Una instancia de {@link ServicioTuristico}.
     */
    public ServicioTuristico getServicioContratado() {
        return servicioContratado;
    }

    /**
     * Genera un resumen formateado de la reserva para su visualización.
     * Cumple con el contrato de la interfaz {@link Registrable} para unificar la salida de datos.
     *
     * @return Un {@code String} con el resumen de la reserva, incluyendo número de orden,
     *         fecha, nombre del cliente y nombre del tour contratado.
     */
    @Override
    public String mostrarResumen() {
        return String.format("[RESERVA] Orden: %s | Fecha: %s | Cliente: %s | Tour: %s",
                numeroOrdenCompra,
                fechaReserva.toString(),
                cliente.getNombre(),
                servicioContratado.getNombreServicio());
    }
}