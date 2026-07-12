package data;

import java.util.HashMap;
import java.util.Map;

import model.entities.assets.Vehiculo;
import model.entities.people.GuiaTuristico;
import model.entities.people.ColaboradorExterno;
import model.entities.activities.TrekkingAltaMontania;
import model.entities.activities.RutaGastronomica;
import model.entities.activities.PaseoLacustre;
import model.entities.activities.ExcursionCultural;

public class DiccionarioFormularios {

    private Map<Class<?>, String[]> diccionarioEtiquetas;

    public DiccionarioFormularios() {
        diccionarioEtiquetas = new HashMap<>();
        inicializarDiccionario();
    }

    private void inicializarDiccionario() {
        // Paquete Assets
        // Asumiendo constructor: Vehiculo(int anioCompra, int vidaUtil, String modelo, String patente)
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

        // Paquete Activities
        diccionarioEtiquetas.put(TrekkingAltaMontania.class, new String[]{
                "Nombre de la Ruta", "Duracion (en horas)", "Altura ascenso"
        });

        diccionarioEtiquetas.put(RutaGastronomica.class, new String[]{
                "Nombre de la Ruta", "Duracion (en horas)", "Cantidad de Degustaciones"
        });

        diccionarioEtiquetas.put(PaseoLacustre.class, new String[]{
                "Nombre del Paseo", "Duracion (en horas)", "Tipo de embarcación"
        });

        diccionarioEtiquetas.put(ExcursionCultural.class, new String[]{
                "Nombre de la Excursión", "Duracion (en horas)", "Lugar historico a visitar"
        });
    }

    /**
     * Devuelve las etiquetas legibles para una clase.
     * Si la clase es nueva y no está en el diccionario, devuelve null.
     */
    public String[] obtenerEtiquetas(Class<?> clase) {
        return diccionarioEtiquetas.get(clase);
    }
}
