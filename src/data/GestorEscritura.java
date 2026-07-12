package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GestorEscritura {

    private String rutaDirectorio;

    public GestorEscritura(String rutaDirectorio) {
        this.rutaDirectorio = rutaDirectorio;
    }

    public boolean registrarNuevaInstancia(String nombreClase, String lineaDatos) {
        String nombreArchivo = "BaseDatos" + nombreClase + ".txt";
        File archivo = new File(rutaDirectorio, nombreArchivo);

        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.newLine();

            bw.write(lineaDatos);

            return true;

        } catch (IOException e) {
            System.err.println("Error crítico de I/O al intentar escribir en " + nombreArchivo + ": " + e.getMessage());
            return false;
        }
    }
}