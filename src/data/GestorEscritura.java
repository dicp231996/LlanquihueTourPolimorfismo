package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase encargada de gestionar la persistencia de datos mediante la escritura en archivos de texto.
 * Permite registrar nuevas instancias añadiendo líneas al final de los archivos de texto correspondientes
 * a cada clase, funcionando como el componente de guardado físico para la base de datos.
 */
public class GestorEscritura {

    /**
     * Ruta absoluta o relativa del directorio donde se encuentran alojados los archivos de texto
     * que conforman la base de datos.
     */
    private String rutaDirectorio;

    /**
     * Constructor de la clase GestorEscritura.
     *
     * @param rutaDirectorio La ruta del directorio en el sistema de archivos donde se escribirán y
     *                       actualizarán los archivos de texto (ej. "src/data/files/").
     */
    public GestorEscritura(String rutaDirectorio) {
        this.rutaDirectorio = rutaDirectorio;
    }

    /**
     * Registra una nueva instancia escribiendo sus datos serializados en una nueva línea
     * dentro del archivo de texto asociado a su clase.
     * <p>
     * El nombre del archivo de destino se construye dinámicamente concatenando el prefijo
     * "BaseDatos", el nombre de la clase y la extensión ".txt". El proceso utiliza el modo "append"
     * (añadir) para no sobrescribir la información existente.
     *
     * @param nombreClase El nombre de la clase de la entidad a registrar (ej. "Cliente" guardará en "BaseDatosCliente.txt").
     * @param lineaDatos  La cadena de texto que contiene los atributos de la instancia, generalmente separados por un delimitador como punto y coma (;).
     * @return {@code true} si la operación de escritura en el archivo fue exitosa; {@code false} si ocurrió una excepción de entrada/salida (I/O).
     */
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