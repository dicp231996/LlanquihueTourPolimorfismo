package data;
import java.util.ArrayList;
import model.interfaces.Registrable;

/**
 * Clase encargada de gestionar y agrupar las entidades cargadas en el sistema.
 * Trabaja en conjunto con {@link CargarDatos} para extraer y procesar todos los objetos
 * que implementan la interfaz {@link Registrable}, permitiendo operar sobre ellos
 * de manera polimórfica sin importar su clase concreta.
 */
public class GestorEntidades {

    /**
     * Instancia del cargador de datos que contiene la base de datos en memoria.
     */
    private CargarDatos cargador;

    /**
     * Constructor de la clase GestorEntidades.
     *
     * @param cargador Objeto {@link CargarDatos} que provee el acceso a las colecciones de entidades instanciadas.
     */
    public GestorEntidades(CargarDatos cargador) {
        this.cargador = cargador;
    }

    /**
     * Recorre todas las clases y sus respectivas listas de instancias en el cargador de datos,
     * filtrando y agrupando aquellas que cumplen con el contrato de la interfaz {@link Registrable}.
     *
     * @return Una lista global ({@code ArrayList<Registrable>}) que contiene todas las entidades registrables del sistema.
     */
    public ArrayList<Registrable> obtenerInventarioCompleto() {
        ArrayList<Registrable> inventarioGlobal = new ArrayList<>();

        for (Class<?> claseDiferenciada : cargador.obtenerClasesCargadas()) {

            ArrayList<?> listaEspecifica = cargador.obtenerLista(claseDiferenciada);

            for (Object instancia : listaEspecifica) {
                if (instancia instanceof Registrable) {
                    inventarioGlobal.add((Registrable) instancia);
                }
            }
        }
        return inventarioGlobal;
    }

    /**
     * Obtiene el inventario completo y procesa cada entidad de forma polimórfica,
     * imprimiendo por consola el resultado del método {@code mostrarResumen()} de cada instancia.
     */
    public void procesarInventarioPolimorfico() {
        ArrayList<Registrable> inventarioGlobal = obtenerInventarioCompleto();

        for (Registrable instancia : inventarioGlobal) {

            System.out.println(instancia.mostrarResumen());

        }
    }

    /**
     * Genera un reporte en formato de cadena de texto (String) con los resúmenes de las entidades,
     * aplicando polimorfismo puro al invocar {@code mostrarResumen()} sobre la interfaz {@link Registrable}.
     * Permite filtrar los resultados según el nombre de la clase concreta o mostrar todos.
     *
     * @param filtro Cadena de texto que indica el nombre simple de la clase a filtrar (ej. "Cliente", "Reserva")
     *               o la palabra "Todos" para incluir el inventario completo.
     * @return Un {@code String} con el reporte formateado, separado por líneas, y un conteo total al final.
     *         Si no hay datos, retorna un mensaje indicando la ausencia de registros.
     */
    public String generarReportePolimorfico(String filtro) {
        StringBuilder reporte = new StringBuilder();
        ArrayList<Registrable> inventarioGlobal = obtenerInventarioCompleto();
        int contador = 0;

        if (inventarioGlobal.isEmpty()) {
            return "No hay datos registrados en el sistema.\n";
        }

        for (Registrable instancia : inventarioGlobal) {
            // Evaluamos si el filtro es "Todos" o si coincide con el nombre de la clase
            String nombreClase = instancia.getClass().getSimpleName();

            if (filtro.equals("Todos") || nombreClase.equals(filtro)) {

                // Aplicación del Polimorfismo Puro:
                // Se invoca el método del contrato y se acumula en el reporte
                reporte.append(instancia.mostrarResumen()).append("\n");
                reporte.append("----------------------------------------------------------------------\n");
                contador++;
            }
        }

        reporte.append("\nTotal de registros mostrados: ").append(contador);
        return reporte.toString();
    }
}