package util;

import java.util.ArrayList;

import data.CargarDatos;
import model.core.ServicioTuristico;

public class GestorServicios {
    private CargarDatos cargador;
    private ArrayList<ServicioTuristico> catalogoServicios;

    public GestorServicios() {
        this.cargador = new CargarDatos();
        this.catalogoServicios = new ArrayList<>();
    }

    // Recibe la ruta del directorio y extrae la lista unificada
    public void inicializarServicios(String rutaDirectorio) {
        cargador.cargarDesdeDirectorio(rutaDirectorio);
        this.catalogoServicios = cargador.getListaServicios();
        System.out.println("Carga de datos completada con éxito.");
    }

    public ArrayList<ServicioTuristico> getCatalogoServicios() {
        return catalogoServicios;
    }

    public void mostrarCatalogoCompleto() {
        System.out.println("\n=== CATÁLOGO DE SERVICIOS TURÍSTICOS (LLANQUIHUE Y ALREDEDORES) ===");

        if (catalogoServicios.isEmpty()) {
            System.out.println("No hay servicios registrados en el sistema.");
        } else {
            for (ServicioTuristico servicio : catalogoServicios) {
                System.out.println(servicio.toString());
                System.out.println("----------------------------------------");
            }
        }
    }
}
