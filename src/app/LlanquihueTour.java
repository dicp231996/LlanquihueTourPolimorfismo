package app;

import java.nio.file.Paths;

import util.GestorServicios;

public class LlanquihueTour {
    public static void main(String[] args) {
        GestorServicios gestor = new GestorServicios();

        // Se define el directorio raíz donde están los archivos txt
        String carpetaDatos = "resources";

        // Se obtiene la ruta absoluta para evitar problemas de ubicación del Working Directory
        String rutaDirectorio = Paths.get(carpetaDatos).toAbsolutePath().toString();

        // Se inicia la carga enviando el directorio
        gestor.inicializarServicios(rutaDirectorio);

        // Se imprime el resultado en consola
        gestor.mostrarCatalogoCompleto();
    }
}
