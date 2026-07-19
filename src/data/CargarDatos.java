package data;

import model.core.ServicioTuristico;
import model.entities.people.Cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de cargar y gestionar dinámicamente los datos del sistema LlanquihueTour.
 * Utiliza reflexión (Reflection) para instanciar objetos a partir de archivos de texto (.txt),
 * mapeando cada línea de texto a los constructores de las clases correspondientes.
 * Gestiona automáticamente las dependencias (como cargar primero Clientes y Servicios antes que Reservas).
 */
public class CargarDatos {

    /**
     * Estructura de datos principal que actúa como base de datos en memoria.
     * Asocia una Clase con una lista de sus instancias cargadas.
     */
    private Map<Class<?>, ArrayList<Object>> baseDatosLlanquihueTour;

    /**
     * Almacena la última ruta de directorio utilizada para permitir recargas.
     */
    private String rutaDirectorioActual;

    /**
     * Arreglo de paquetes donde el sistema buscará las clases para instanciarlas dinámicamente.
     */
    private final String[] PAQUETES_POSIBLES = {
            "model.entities.activities.",
            "model.entities.assets.",
            "model.entities.people.",
            "model.entities.business."
    };

    /**
     * Constructor por defecto de CargarDatos.
     * Inicializa el mapa vacío que actuará como base de datos en memoria.
     */
    public CargarDatos() {
        this.baseDatosLlanquihueTour = new HashMap<>();
    }

    /**
     * Carga todos los archivos de texto (.txt) desde el directorio especificado.
     * Procesa primero las entidades independientes y deja para el final los archivos
     * que contengan la palabra "Reserva" para garantizar que las dependencias existan previamente.
     *
     * @param ruta La ruta absoluta o relativa del directorio que contiene los archivos .txt
     */
    public void cargarDesdeDirectorio(String ruta) {
        this.rutaDirectorioActual = ruta;

        File directorio = new File(ruta);
        baseDatosLlanquihueTour.clear();

        if (!directorio.exists() || !directorio.isDirectory()) {
            System.err.println("El directorio no existe en la ruta: " + ruta);
            return;
        }

        File[] archivosTxt = directorio.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (archivosTxt == null || archivosTxt.length == 0) {
            System.out.println("No se encontro el archivo txt en: " + ruta);
            return;
        }

        ArrayList<File> archivosDependientes = new ArrayList<>();

        for (File archivo : archivosTxt) {
            if (!archivo.getName().contains("Reserva")) {
                procesarArchivoDinamicamente(archivo);
            } else {
                archivosDependientes.add(archivo);
            }
        }

        for (File archivoReserva : archivosDependientes) {
            procesarArchivoDinamicamente(archivoReserva);
        }
    }

    /**
     * Extrae el nombre real de la clase a partir del nombre de su archivo.
     * Elimina la extensión ".txt" y el prefijo "BaseDatos" si lo tuviera.
     *
     * @param nombreArchivo El nombre del archivo (ej. BaseDatosCliente.txt)
     * @return El nombre limpio de la clase (ej. Cliente)
     */
    private String extraerNombreClase(String nombreArchivo) {
        String nombreSinExtension = nombreArchivo.replace(".txt","");
        String prefijo = "BaseDatos";
        if (nombreArchivo.startsWith(prefijo)) {
            return nombreSinExtension.substring(prefijo.length());
        }
        return nombreSinExtension;
    }

    /**
     * Busca una clase en tiempo de ejecución iterando sobre los paquetes definidos en {@link #PAQUETES_POSIBLES}.
     *
     * @param nombreClase El nombre de la clase a buscar (ej. Cliente).
     * @return El objeto Class correspondiente si se encuentra, o null si no existe en ningún paquete.
     */
    private Class<?> buscarClase(String nombreClase) {
        for (String paquete : PAQUETES_POSIBLES) {
            try {
                return Class.forName(paquete + nombreClase);
            } catch (ClassNotFoundException e) {

            }
        }
        return null;
    }

    /**
     * Lee un archivo de texto, mapea cada línea separada por punto y coma (;)
     * y utiliza reflexión para encontrar un constructor compatible y crear las instancias.
     * También se encarga de convertir los tipos de datos simples y objetos de valor (Value Objects).
     *
     * @param archivo El archivo .txt a procesar.
     */
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
                        else if (tipoParametros[i] == boolean.class || tipoParametros[i] == Boolean.class) {
                            argumentos[i] = Boolean.parseBoolean(partes[i]);
                        }
                        else if (tipoParametros[i] == model.valueobjects.Rut.class) {
                            argumentos[i] = new model.valueobjects.Rut(partes[i]);
                        }
                        else if (tipoParametros[i] == model.valueobjects.CorreoContacto.class) {
                            argumentos[i] = new model.valueobjects.CorreoContacto(partes[i]);
                        }
                        else if (tipoParametros[i] == model.valueobjects.TarjetaCredito.class) {
                            argumentos[i] = new model.valueobjects.TarjetaCredito(partes[i]);
                        }
                        else if (tipoParametros[i] == model.entities.people.Cliente.class) {
                            argumentos[i] = buscarClientePorRut(partes[i]);
                        }
                        else if (model.core.ServicioTuristico.class.isAssignableFrom(tipoParametros[i])) {
                            argumentos[i] = buscarServicioPorCodigo(partes[i]);
                        }
                        else if (tipoParametros[i] == java.time.LocalDate.class) {
                            java.time.format.DateTimeFormatter formateador = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            argumentos[i] = java.time.LocalDate.parse(partes[i], formateador);
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

    /**
     * Obtiene un conjunto (Set) con todas las clases que han sido cargadas exitosamente
     * en la base de datos en memoria.
     *
     * @return Conjunto de objetos Class presentes en el mapa de datos.
     */
    public java.util.Set<Class<?>> obtenerClasesCargadas() {
        return baseDatosLlanquihueTour.keySet();
    }

    /**
     * Retorna una lista fuertemente tipada de todas las instancias cargadas de una clase específica.
     *
     * @param clase El objeto Class del tipo requerido (ej. Cliente.class).
     * @param <T>   El tipo genérico esperado.
     * @return Una lista de tipo {@code ArrayList<T>} con los objetos correspondientes, o una lista vacía si no hay registros.
     */
    @SuppressWarnings("unchecked")
    public <T> ArrayList<T> obtenerLista(Class<T> clase) {
        ArrayList<Object> lista = baseDatosLlanquihueTour.get(clase);
        if (lista == null) {
            return new ArrayList<>();
        }
        return (ArrayList<T>) lista;
    }

    /**
     * Recarga toda la información desde la última ruta de directorio utilizada.
     * Si no se ha cargado previamente ningún directorio, no realiza ninguna acción.
     */
    public void recargar() {
        if (rutaDirectorioActual != null) {
            cargarDesdeDirectorio(rutaDirectorioActual);
        }
    }

    /**
     * Genera un arreglo de cadenas de texto con las opciones visuales de los servicios turísticos.
     * Formato utilizado: "Código - NombreServicio".
     *
     * @return Arreglo de String con las opciones de servicios disponibles.
     */
    public String[] obtenerOpcionesServicios() {
        ArrayList<String> opciones = new ArrayList<>();

        for (ArrayList<Object> listaDeEntidades : baseDatosLlanquihueTour.values()) {
            for (Object objeto : listaDeEntidades) {
                if (objeto instanceof ServicioTuristico) {
                    ServicioTuristico servicio = (ServicioTuristico) objeto;
                    String opcionVisual = servicio.getCodigoServicio() + " - " + servicio.getNombreServicio();
                    opciones.add(opcionVisual);
                }
            }
        }

        return opciones.toArray(new String[0]);
    }

    /**
     * Genera un arreglo de cadenas de texto con las opciones visuales de los clientes.
     * Formato utilizado: "RUT - Nombre".
     *
     * @return Arreglo de String con las opciones de clientes disponibles.
     */
    public String[] obtenerOpcionesClientes() {
        ArrayList<String> opciones = new ArrayList<>();

        // Iteramos sobre el mapa bidimensional
        for (ArrayList<Object> listaDeEntidades : baseDatosLlanquihueTour.values()) {
            for (Object objeto : listaDeEntidades) {
                // Asegúrate de importar la clase Cliente (ej. import model.entities.people.Cliente;)
                if (objeto instanceof Cliente) {
                    Cliente cliente = (Cliente) objeto;
                    // Construimos el formato: "RUT - Nombre"
                    String opcionVisual = cliente.getRut() + " - " + cliente.getNombre();
                    opciones.add(opcionVisual);
                }
            }
        }
        return opciones.toArray(new String[0]);
    }

    /**
     * Busca una instancia de Cliente en memoria comparando el RUT proporcionado.
     *
     * @param rutBuscado String con el RUT del cliente a buscar.
     * @return La instancia de {@link Cliente} si se encuentra, o null en caso contrario.
     */
    private Object buscarClientePorRut(String rutBuscado) {
        for (ArrayList<Object> listaDeEntidades : baseDatosLlanquihueTour.values()) {
            for (Object objeto : listaDeEntidades) {
                // Asegúrate de que la clase Cliente esté importada
                if (objeto instanceof Cliente) {
                    Cliente cliente = (Cliente) objeto;

                    // Se utiliza String.valueOf() por si getRut() devuelve un objeto model.valueobjects.Rut
                    String rutCliente = String.valueOf(cliente.getRut());

                    if (rutCliente.equals(rutBuscado)) {
                        return cliente; // Coincidencia encontrada, se retorna la instancia en memoria
                    }
                }
            }
        }
        return null; // Sin coincidencias
    }

    /**
     * Busca una instancia de un Servicio Turístico en memoria mediante su código.
     *
     * @param codigoBuscado String con el código del servicio a buscar.
     * @return La instancia de {@link ServicioTuristico} si se encuentra, o null en caso contrario.
     */
    private Object buscarServicioPorCodigo(String codigoBuscado) {
        for (ArrayList<Object> listaDeEntidades : baseDatosLlanquihueTour.values()) {
            for (Object objeto : listaDeEntidades) {
                if (objeto instanceof ServicioTuristico) {
                    ServicioTuristico servicio = (ServicioTuristico) objeto;

                    if (servicio.getCodigoServicio().equals(codigoBuscado)) {
                        return servicio; // Coincidencia encontrada, se retorna la instancia en memoria
                    }
                }
            }
        }
        return null; // Sin coincidencias
    }

    /**
     * Obtiene el mapa completo que contiene la base de datos en memoria.
     * La clave es el objeto Class de la entidad y el valor es la lista de instancias asociadas.
     *
     * @return Mapa con la estructura principal de datos cargados.
     */
    public Map<Class<?>, ArrayList<Object>> getBaseDatosLlanquihueTour() {
        return this.baseDatosLlanquihueTour;
    }
}