package ui;

import data.CargarDatos;
import data.GestorEscritura;
import view.VentanaPrincipal;
import view.VentanaRegistro;

import javax.swing.*;

public class LlanquihueTour {
    public static void main(String[] args) {
        String rutaBaseDatos = "resources";

        CargarDatos cargador = new CargarDatos();
        cargador.cargarDesdeDirectorio(rutaBaseDatos);

        GestorEscritura escritor = new GestorEscritura(rutaBaseDatos);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal ventana = new VentanaPrincipal(cargador, escritor);
                ventana.setVisible(true);
            }
        });
    }
}


