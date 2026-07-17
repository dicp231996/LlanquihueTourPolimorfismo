package data;
import java.util.ArrayList;
import model.interfaces.Registrable;

public class GestorEntidades {

    private CargarDatos cargador;

    public GestorEntidades(CargarDatos cargador) {
        this.cargador = cargador;
    }

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
    public void procesarInventarioPolimorfico() {
        ArrayList<Registrable> inventarioGlobal = obtenerInventarioCompleto();

        for (Registrable instancia : inventarioGlobal) {

            System.out.println(instancia.mostrarResumen());

        }
    }
    // Añade este método en GestorEntidades.java
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
