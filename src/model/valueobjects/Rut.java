package model.valueobjects;

/**
 * Clase que representa el Rol Único Tributario (RUT) chileno como un (Value Object).
 * Se encarga de almacenar, formatear y validar la correcta estructura del RUT.
 * @author Daniel Campos
 * @version 1.0.0
 */

public class Rut {
    private String rut;

    /**
     * Constructor por defecto.
     * Inicializa el RUT con el estado "Sin registro".
     */

    public Rut() {
        this.rut = "Sin registro";
    }

    /**
     * Constructor que inicializa el objeto validando y asignando un RUT específico.
     *
     * @param rut El RUT a registrar, el cual debe venir en el formato correcto (con puntos y guion).
     */

    public Rut(String rut) {
        setRut(rut);
    }

    //Metodos [Rut]

    /**
     * Obtiene el RUT registrado en el objeto.
     *
     * @return El RUT almacenado como cadena de caracteres.
     */

    public String getRut() {
        return rut;
    }

    /**
     * Establece y valida el RUT ingresado.
     * El formato requerido debe incluir puntos y guion, aceptando de 2 a 3 dígitos
     * antes del primer punto, y terminando con un dígito verificador del 0 al 9, o la letra K/k
     * (ej. 12.345.678-9 o 123.456.789-K).
     * el dígito verificador se almacena en mayúscula
     * y, si el dígito ingresado es "0", se convierte automáticamente a "K".
     *
     * @param rutRegistrado El RUT que se desea validar y registrar.
     * @throws IllegalArgumentException si el RUT proporcionado es nulo o no coincide con la expresión regular del formato requerido.
     */

    public void setRut(String rutRegistrado) {
        // El cuantificador \\d{1,3} ahora acepta 1, 2 o 3 dígitos iniciales
        if (rutRegistrado == null || !rutRegistrado.matches("^\\d{1,3}\\.\\d{3}\\.\\d{3}-[0-9Kk]$")){
            throw new IllegalArgumentException("El RUT ingresado no cumple con el formato requerido.");
        }

        int indiceGuion = rutRegistrado.indexOf("-");
        String rutConGuion = rutRegistrado.substring(0, indiceGuion + 1);
        String digitoVerificador = rutRegistrado.substring(indiceGuion + 1);

        digitoVerificador = digitoVerificador.toUpperCase();

        if (digitoVerificador.equals("0")) {
            digitoVerificador = "K";
        }
        this.rut = rutConGuion + digitoVerificador;
    }

    //Instancia de objeto

    /**
     * Retorna una representación en formato de texto del objeto Rut.
     *
     * @return Una cadena de caracteres con el prefijo "Rut: " seguido del valor almacenado.
     */

    @Override
    public String toString() {
        return "Rut: " + rut;
    }

}
