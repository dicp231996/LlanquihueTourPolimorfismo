package data;

import java.util.ArrayList;
import model.interfaces.Registrable;

public class GestorInstancias {
    private CargarDatos cargador;

    public GestorInstancias() {
        this.cargador = new CargarDatos();
    }

    public void inicializarDatos(String ruta){
        cargador.cargarDesdeDirectorio(ruta);
        System.out.println("Carga de datos realizada con éxito");
    }

    public <T> ArrayList<T> listarRegistros(Class<T> tipoClase){
        return cargador.obtenerLista(tipoClase);
    }

    public <T extends Registrable> void mostrarCatalogo(Class<T> tipoClase, String tituloCatalogo){
        System.out.println("\n====== " + tituloCatalogo.toUpperCase() + " ======");

        ArrayList<T> lista = listarRegistros(tipoClase);

        if(lista.isEmpty()){
            System.out.println("No hay registros para esta clase en el sistema");
        }
        else{
            for(T instancia : lista){
                System.out.println(instancia.mostrarResumen());
                System.out.println("-------------------------------------");
            }
        }
    }

    public void mostrarTodosLosCatalogosDinamicos() {
        java.util.Set<Class<?>> clasesCargadas = cargador.obtenerClasesCargadas();

        if (clasesCargadas.isEmpty()) {
            System.out.println("No hay datos cargados en el sistema.");
            return;
        }

        for (Class<?> clase : clasesCargadas) {

            String nombreClase = clase.getSimpleName();

            String nombreSeparado = nombreClase.replaceAll("([a-z])([A-Z]+)", "$1 $2");

            String tituloCatalogo = "Catálogo de " + nombreSeparado;

            System.out.println("\n=== " + tituloCatalogo.toUpperCase() + " ===");

            ArrayList<?> lista = cargador.obtenerLista(clase);

            if (lista.isEmpty()) {
                System.out.println("No hay registros disponibles.");
            } else {
                for (Object instancia : lista) {
                    if (instancia instanceof model.interfaces.Registrable) {
                        ((model.interfaces.Registrable) instancia).mostrarResumen();
                    }
                }
            }
            System.out.println("----------------------------------------");
        }
    }
}
