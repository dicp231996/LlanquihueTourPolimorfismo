package model.entities.business;

import model.core.ServicioTuristico;
import model.entities.people.Cliente;
import model.interfaces.Registrable;

import java.time.LocalDate;

public class Reserva implements Registrable {
    private String numeroOrdenCompra;
    private LocalDate fechaReserva;
    private Cliente cliente;
    private ServicioTuristico servicioContratado;

    // Constructor
    public Reserva(String numeroOrdenCompra, LocalDate fechaReserva, Cliente cliente, ServicioTuristico servicioContratado) {
        this.numeroOrdenCompra = numeroOrdenCompra;
        this.fechaReserva = fechaReserva;
        this.cliente = cliente;
        this.servicioContratado = servicioContratado;
    }

    // Getters
    public String getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ServicioTuristico getServicioContratado() {
        return servicioContratado;
    }

    // Cumplimiento del contrato polimórfico
    @Override
    public String mostrarResumen() {
        // Asumiendo que Cliente tiene getNombre() (heredado de Persona) y Tour tiene getNombre()
        return String.format("[RESERVA] Orden: %s | Fecha: %s | Cliente: %s | Tour: %s",
                numeroOrdenCompra,
                fechaReserva.toString(),
                cliente.getNombre(),
                servicioContratado.getNombre());
    }
}
