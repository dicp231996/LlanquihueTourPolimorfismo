package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CargarDatos {
    private Map<Class<?>, ArrayList<Object>> baseDatosLlanquihueTour;

    private final String[] PAQUETES_POSIBLES = {
            "model.entities.activities.",
            "model.entities.assets.",
            "model.entities.people."
    };

    public CargarDatos() {
        this.baseDatosLlanquihueTour = new HashMap<>();
    }

    public void cargarDesdeDirectorio(String ruta) {
        File directorio = new File(ruta);

        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("El directorio no existe en la ruta: " + ruta);
            return;
        }

        File[] archivosTxt = directorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (archivosTxt == null || archivosTxt.length == 0) {
            System.out.println("No se encontro el archivo txt en: " + ruta);
            return;
        }

        for (File archivo : archivosTxt) {
            procesarArchivoDinamicamente(archivo);
        }
    }

    private String extraerNombreClase(String nombreArchivo) {
        String nombreSinExtension = nombreArchivo.replace(".txt","");
        String prefijo = "BaseDatos";
        if (nombreArchivo.startsWith(prefijo)) {
            return nombreSinExtension.substring(prefijo.length());
        }
        return nombreSinExtension;
    }

    private Class<?> buscarClase(String nombreClase) {
        for (String paquete : PAQUETES_POSIBLES) {
            try {
                return Class.forName(paquete + nombreClase);
            } catch (ClassNotFoundException e) {

            }
        }
        return null;
    }

    private void procesarArchivoDinamicamente(File archivo) {
        String nombreClase = extraerNombreClase(archivo.getName());
        Class<?> claseDinamica = buscarClase(nombreClase);

        if (claseDinamica == null) {
            System.err.println("Clase no encontrada en ninguno de los paquetes del sistema: " + nombreClase);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                Constructor<?>[] constructores = claseDinamica.getConstructors();
                Constructor<?> constructorAdecuado = null;

                for (Constructor<?> c : constructores) {
                    if (c.getParameterCount() == partes.length) {
                        constructorAdecuado = c;
                        break;
                    }
                }

                if (constructorAdecuado != null) {
                    Class<?>[] tipoParametros = constructorAdecuado.getParameterTypes();
                    Object[] argumentos = new Object[partes.length];

                    for (int i = 0; i < partes.length; i++) {
                        if (tipoParametros[i] == int.class || tipoParametros[i] == Integer.class) {
                            argumentos[i] = Integer.parseInt(partes[i]);
                        }
                        else if (tipoParametros[i] == double.class || tipoParametros[i] == Double.class) {
                            argumentos[i] = Double.parseDouble(partes[i]);
                        }
                        else {
                            argumentos[i] = partes[i];
                        }
                    }
                    Object instancia = constructorAdecuado.newInstance(argumentos);

                    baseDatosLlanquihueTour.putIfAbsent(claseDinamica,new ArrayList<>());
                    baseDatosLlanquihueTour.get(claseDinamica).add(instancia);
                }
                else {
                    System.err.println("No hay un contructor compatible para " + partes.length + "argumentos en " + nombreClase);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + archivo.getName() + ":" + e.getMessage());
        }
    }

    public java.util.Set<Class<?>> obtenerClasesCargadas() {
        return baseDatosLlanquihueTour.keySet();
    }

    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> obtenerLista(Class<T> clase) {
        ArrayList<Object> lista = baseDatosLlanquihueTour.get(clase);
        if (lista == null) {
            return new ArrayList<>();
        }
        return (ArrayList<T>) lista;
    }
}

