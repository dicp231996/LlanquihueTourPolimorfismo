package data;

import model.core.ServicioTuristico;
import java.util.ArrayList;
import java.util.Map;

/**
 * Clase utilitaria encargada de la generación automática de códigos secuenciales
 * para los distintos servicios turísticos del sistema.
 */
public class GeneradorCodigosServicios {

    /**
     * Calcula el siguiente código secuencial disponible para un servicio turístico basándose
     * en los registros existentes en la base de datos en memoria y un prefijo específico.
     * <p>
     * El método itera sobre todos los objetos cargados en el mapa, filtra las instancias
     * de {@link ServicioTuristico} cuyos códigos comiencen con el prefijo indicado, extrae
     * la parte numérica para determinar el valor máximo actual, y genera el siguiente
     * código formateado con tres dígitos numéricos (por ejemplo, si el máximo es 2 para el
     * prefijo "TRK", devolverá "TRK003").
     *
     * @param baseDatos Mapa que representa la base de datos en memoria, asociando las clases con sus listas de objetos instanciados.
     * @param prefijo   Cadena de texto que representa el prefijo del tipo de servicio (ej. "TRK" para Trekking, "GAS" para Ruta Gastronómica).
     * @return Un {@code String} con el nuevo código generado secuencialmente.
     */
    public static String calcularProximoCodigo(Map<Class<?>, ArrayList<Object>> baseDatos, String prefijo) {
        int maxId = 0;

        for (ArrayList<Object> listaEntidades : baseDatos.values()) {
            for (Object objeto : listaEntidades) {

                if (objeto instanceof ServicioTuristico) {
                    ServicioTuristico servicio = (ServicioTuristico) objeto;
                    String codigoActual = servicio.getCodigoServicio();

                    if (codigoActual != null && codigoActual.startsWith(prefijo)) {
                        try {
                            String parteNumerica = codigoActual.substring(prefijo.length());
                            int numero = Integer.parseInt(parteNumerica);

                            if (numero > maxId) {
                                maxId = numero;
                            }
                        } catch (NumberFormatException e) {
                            // Se ignoran los formatos corruptos
                        }
                    }
                }
            }
        }

        return String.format("%s%03d", prefijo, (maxId + 1));
    }
}