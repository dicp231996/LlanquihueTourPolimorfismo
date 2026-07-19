package ui;

import data.CargarDatos;
import data.GestorEscritura;
import view.VentanaPrincipal;

import javax.swing.*;

/**
 * Clase principal que actúa como punto de entrada (Entry Point) para la aplicación LlanquihueTour.
 * Esta clase se encarga de inicializar la infraestructura de persistencia de datos (carga y guardado),
 * y de desplegar la ventana principal del sistema dentro del hilo de despacho de eventos de Swing (EDT).
 */
public class LlanquihueTour {

    /**
     * Método principal que ejecuta la aplicación.
     * Configura el directorio de recursos, instancia el cargador y el gestor de archivos,
     * e inicializa la {@link view.VentanaPrincipal} para permitir la interacción del usuario.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        String rutaBaseDatos = "resources";

        // Inicialización de la lógica de datos
        CargarDatos cargador = new CargarDatos();
        cargador.cargarDesdeDirectorio(rutaBaseDatos);

        // Inicialización del gestor de persistencia
        GestorEscritura escritor = new GestorEscritura(rutaBaseDatos);

        // Ejecución de la interfaz gráfica en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal(cargador, escritor);
                ventana.setVisible(true);
            }
        });
    }
}