package model.core;

/**
 * Clase abstracta que representa un activo físico o recurso material perteneciente a la empresa.
 * Proporciona los atributos y comportamientos base que comparten todos los activos,
 * como el año de adquisición, su vida útil estimada y el modelo.
 * Al ser abstracta, no puede ser instanciada directamente y debe ser heredada por clases concretas.
 */
public abstract class ActivoEmpresa {

    /**
     * Año en el que la empresa adquirió el activo.
     */
    private int anioCompra;

    /**
     * Cantidad de años estimados de vida útil para el activo antes de su depreciación o reemplazo.
     */
    private int vidaUtil;

    /**
     * Nombre, modelo o descripción técnica del activo.
     */
    private String modelo;

    /**
     * Constructor por defecto de la clase ActivoEmpresa.
     * Crea una instancia vacía sin inicializar sus atributos.
     */
    public ActivoEmpresa() {
    }

    /**
     * Constructor parametrizado de la clase ActivoEmpresa.
     *
     * @param anioCompra Año de adquisición del activo.
     * @param vidaUtil   Años de vida útil esperada.
     * @param modelo     Modelo o descripción del activo.
     */
    public ActivoEmpresa(int anioCompra, int vidaUtil, String modelo) {
        this.anioCompra = anioCompra;
        this.vidaUtil = vidaUtil;
        this.modelo = modelo;
    }

    /**
     * Obtiene el año de compra del activo.
     *
     * @return Un entero con el año de adquisición.
     */
    public int getAnioCompra() {
        return anioCompra;
    }

    /**
     * Establece o modifica el año de compra del activo.
     *
     * @param anioCompra El nuevo año de adquisición a asignar.
     */
    public void setAnioCompra(int anioCompra) {
        this.anioCompra = anioCompra;
    }

    /**
     * Obtiene la vida útil estimada del activo.
     *
     * @return Un entero con los años de vida útil.
     */
    public int getVidaUtil() {
        return vidaUtil;
    }

    /**
     * Establece o modifica la vida útil estimada del activo.
     *
     * @param vidaUtil La nueva cantidad de años de vida útil a asignar.
     */
    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    /**
     * Obtiene el modelo o descripción del activo.
     *
     * @return Un {@code String} con el modelo del activo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece o modifica el modelo del activo.
     *
     * @param modelo El nuevo nombre o modelo a asignar.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Genera una cadena de texto estructurada con los detalles fundamentales del activo.
     * Formatea la información incluyendo el modelo, año de compra y su vida útil.
     *
     * @return Un {@code String} multilínea con la información del activo.
     */
    public String informacionActivo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Modelo: ").append(modelo).append("\n");
        sb.append("Año de Compra: ").append(anioCompra).append("\n");
        sb.append("Vida Útil: ").append(vidaUtil).append(" años");
        return sb.toString();
    }
}