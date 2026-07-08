package model.core;

public abstract class ActivoEmpresa {
    private int anioCompra; // Reemplazada la 'ñ' por 'ni'
    private int vidaUtil;
    private String modelo;

    // Constructor por defecto
    public ActivoEmpresa() {
    }

    // Constructor con parámetros
    public ActivoEmpresa(int anioCompra, int vidaUtil, String modelo) {
        this.anioCompra = anioCompra;
        this.vidaUtil = vidaUtil;
        this.modelo = modelo;
    }

    // Métodos Getter y Setter para 'anioCompra'
    public int getAnioCompra() {
        return anioCompra;
    }

    public void setAnioCompra(int anioCompra) {
        this.anioCompra = anioCompra;
    }

    // Métodos Getter y Setter para 'vidaUtil'
    public int getVidaUtil() {
        return vidaUtil;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    // Métodos Getter y Setter para 'modelo'
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    // Método propio para estructurar y devolver la información del transporte
    public String informacionVehiculo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Modelo: ").append(modelo).append("\n");
        sb.append("Año de Compra: ").append(anioCompra).append("\n");
        sb.append("Vida Útil: ").append(vidaUtil).append(" años");
        return sb.toString();
    }
}
