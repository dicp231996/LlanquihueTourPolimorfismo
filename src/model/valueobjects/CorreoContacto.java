package model.valueobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorreoContacto {

    private final String email;

    /**
     * Expresión regular para validar correos electrónicos.
     * Estructura: usuario + @ + dominio + . + extensión (mínimo 2 letras)
     */
    private static final String REGEX_CORREO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";

    public CorreoContacto(String emailTexto) {
        if (!validarFormato(emailTexto)) {
            throw new IllegalArgumentException("Error de formato: El correo '" + emailTexto + "' no cumple con la estructura estándar (usuario@dominio.ext).");
        }
        // Se almacena en minúsculas para mantener consistencia en la base de datos
        this.email = emailTexto.trim().toLowerCase();
    }

    /**
     * Evalúa si la cadena de texto cumple con el patrón Regex establecido para correos.
     * @param emailTexto El correo electrónico en formato String.
     * @return true si el formato es válido, false en caso contrario.
     */
    public static boolean validarFormato(String emailTexto) {
        if (emailTexto == null || emailTexto.trim().isEmpty()) {
            return false;
        }

        Pattern patron = Pattern.compile(REGEX_CORREO);
        Matcher verificador = patron.matcher(emailTexto.trim());

        return verificador.matches();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return email;
    }
}

