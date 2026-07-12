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
}
