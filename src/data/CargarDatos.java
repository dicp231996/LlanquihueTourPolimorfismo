package data;
import model.core.ServicioTuristico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class CargarDatos {

    private ArrayList<ServicioTuristico> listaServicios;

    public CargarDatos() {
        this.listaServicios = new ArrayList<>();
    }

    public void cargarDesdeDirectorio(String rutaDirectorio) {
        File directorio = new File(rutaDirectorio);

        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("Directorio inválido o no encontrado: " + rutaDirectorio);
            return;
        }

        File[] archivosTxt = directorio.listFiles((dir, nombre) -> nombre.toLowerCase().endsWith(".txt"));

        if (archivosTxt == null || archivosTxt.length == 0) {
            System.out.println("No se encontraron archivos .txt en: " + rutaDirectorio);
            return;
        }

        for (File archivo : archivosTxt) {
            procesarArchivoDinamicamente(archivo);
        }
    }

    // Nuevo método para procesar y limpiar el nombre del archivo
    private String extraerNombreClase(String nombreArchivo) {
        // 1. Se quita la extensión .txt
        String nombreSinExtension = nombreArchivo.replace(".txt", "");

        // 2. Se verifica si el nombre comienza con "BaseDatos" (respetando mayúsculas/minúsculas)
        String prefijo = "BaseDatos";
        if (nombreSinExtension.startsWith(prefijo)) {
            // 3. Se obtiene el substring cortando desde donde termina "BaseDatos"
            return nombreSinExtension.substring(prefijo.length());
        }

        // Si el archivo no tiene el prefijo, se devuelve tal cual
        return nombreSinExtension;
    }

    private void procesarArchivoDinamicamente(File archivo) {
        // Llamamos al nuevo método para obtener el nombre real de la clase
        String nombreClase = extraerNombreClase(archivo.getName());
        String paquete = "model.entities.activities.";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            // Busca la clase en tiempo de ejecución utilizando el nombre limpio
            Class<?> claseDinamica = Class.forName(paquete + nombreClase);

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length == 3) {
                    Constructor<?>[] constructores = claseDinamica.getConstructors();
                    Constructor<?> constructorAdecuado = null;

                    for (Constructor<?> c : constructores) {
                        if (c.getParameterCount() == 3) {
                            constructorAdecuado = c;
                            break;
                        }
                    }

                    if (constructorAdecuado != null) {
                        Class<?>[] tiposParametros = constructorAdecuado.getParameterTypes();
                        Object[] argumentos = new Object[3];

                        argumentos[0] = partes[0];
                        argumentos[1] = Integer.parseInt(partes[1]);

                        if (tiposParametros[2] == int.class || tiposParametros[2] == Integer.class) {
                            argumentos[2] = Integer.parseInt(partes[2]);
                        } else {
                            argumentos[2] = partes[2];
                        }

                        ServicioTuristico instancia = (ServicioTuristico) constructorAdecuado.newInstance(argumentos);
                        listaServicios.add(instancia);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Clase no encontrada: " + paquete + nombreClase + ". Verifica que el nombre después de 'BaseDatos' sea idéntico a la clase.");
        } catch (Exception e) {
            System.err.println("Error al instanciar dinámicamente desde " + archivo.getName() + ": " + e.getMessage());
        }
    }

    public ArrayList<ServicioTuristico> getListaServicios() {
        return listaServicios;
    }
}

