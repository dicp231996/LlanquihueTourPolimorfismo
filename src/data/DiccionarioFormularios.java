package data;

import java.util.HashMap;
import java.util.Map;

import model.entities.assets.Vehiculo;
import model.entities.business.Reserva;
import model.entities.people.Cliente;
import model.entities.people.GuiaTuristico;
import model.entities.people.ColaboradorExterno;
import model.entities.activities.TrekkingAltaMontania;
import model.entities.activities.RutaGastronomica;
import model.entities.activities.PaseoLacustre;
import model.entities.activities.ExcursionCultural;

/**
 * Clase que actúa como un diccionario para asociar las entidades del modelo del sistema
 * con los nombres legibles de sus atributos.
 * Esta estructura es fundamental para la generación dinámica de la interfaz gráfica de usuario,
 * permitiendo crear formularios automáticamente basados en la clase seleccionada.
 */
public class DiccionarioFormularios {

    /**
     * Mapa que almacena la relación entre el tipo de clase (Class) y un arreglo de
     * cadenas de texto (String[]) que representan las etiquetas de los campos del formulario.
     */
    private Map<Class<?>, String[]> diccionarioEtiquetas;

    /**
     * Constructor por defecto de DiccionarioFormularios.
     * Inicializa la estructura del mapa y ejecuta la carga de datos base
     * llamando al método {@link #inicializarDiccionario()}.
     */
    public DiccionarioFormularios() {
        diccionarioEtiquetas = new HashMap<>();
        inicializarDiccionario();
    }

    /**
     * Puebla el mapa de etiquetas con las clases soportadas por el sistema y sus respectivos
     * campos legibles. Los datos están organizados por los paquetes del modelo de dominio:
     * Assets, People, Activities y Business.
     */
    private void inicializarDiccionario() {
        // Paquete Assets
        diccionarioEtiquetas.put(Vehiculo.class, new String[]{
                "Año de Compra", "Vida Útil Estimada", "Modelo del Vehículo", "Patente"
        });

        // Paquete People
        diccionarioEtiquetas.put(GuiaTuristico.class, new String[]{
                "Nombre Completo", "Rut", "Dirección", "Nivel de Inglés", "Especialidad"
        });

        diccionarioEtiquetas.put(ColaboradorExterno.class, new String[]{
                "Nombre Completo", "Rut", "Dirección", "Tipo de Servicio","Empresa representada"
        });

        diccionarioEtiquetas.put(Cliente.class, new String[]{
                "Nombre Completo", "Rut", "Dirección", "Correo de contacto", "Tarjeta  asociada", "Cliente frecuente"
        });

        // Paquete Activities
        diccionarioEtiquetas.put(TrekkingAltaMontania.class, new String[]{
                "Nombre del tour","Codigo del tour", "Duracion (en horas)", "Altura ascenso"
        });

        diccionarioEtiquetas.put(RutaGastronomica.class, new String[]{
                "Nombre del tour","Codigo del tour" , "Duracion (en horas)", "Cantidad de Degustaciones"
        });

        diccionarioEtiquetas.put(PaseoLacustre.class, new String[]{
                "Nombre del tour","Codigo del tour" , "Duracion (en horas)", "Tipo de embarcación"
        });

        diccionarioEtiquetas.put(ExcursionCultural.class, new String[]{
                "Nombre del tour","Codigo del tour" , "Duracion (en horas)", "Lugar historico a visitar"
        });

        // Paquete Business
        diccionarioEtiquetas.put(Reserva.class, new String[]{
                "Número de Orden de Compra","Fecha reserva","Cliente","Servicio Contratado"
        });
    }

    /**
     * Devuelve las etiquetas legibles que corresponden a los campos de formulario de una clase específica.
     *
     * @param clase El objeto Class del cual se desean obtener las etiquetas de formulario.
     * @return Un arreglo de {@code String} con los nombres de los campos si la clase está en el diccionario;
     *         devuelve {@code null} si la clase no está registrada.
     */
    public String[] obtenerEtiquetas(Class<?> clase) {
        return diccionarioEtiquetas.get(clase);
    }
}