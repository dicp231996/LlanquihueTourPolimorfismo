package model.valueobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rut {

    private final String valor;

    /**
     * Expresión regular que valida dos formatos comunes:
     * 1. Con puntos y guion: 12.345.678-9 o 1.234.567-K
     * 2. Sin puntos y con guion: 12345678-9 o 1234567-k
     */
    private static final String REGEX_FORMATO_RUT = "^(\\d{1,2}(?:\\.\\d{3}){2}-[\\dkK]|\\d{7,8}-[\\dkK])$";

    public Rut() {
        this.valor = "10.177.579-8";
    }

    public Rut(String valor) {
        if (!validarFormato(valor)) {
            throw new IllegalArgumentException("Error de formato: El RUT '" + valor + "' no cumple con la estructura esperada (XX.XXX.XXX-Y o XXXXXXXX-Y).");
        }
        this.valor = valor.trim().toUpperCase();
    }

    /**
     * Evalúa si la cadena de texto cumple con el patrón Regex establecido.
     * @param rutTexto El RUT en formato String.
     * @return true si el formato es válido, false en caso contrario.
     */
    public static boolean validarFormato(String rutTexto) {
        if (rutTexto == null || rutTexto.trim().isEmpty()) {
            return false;
        }

        Pattern patron = Pattern.compile(REGEX_FORMATO_RUT);
        Matcher verificador = patron.matcher(rutTexto.trim());

        return verificador.matches();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}

