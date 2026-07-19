package model.valueobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que representa un objeto de valor (Value Object) para gestionar correos electrónicos.
 * Se encarga de garantizar la integridad del dato mediante una validación de formato
 * mediante expresiones regulares durante la instanciación.
 * <p>
 * Los objetos de esta clase son inmutables, y los correos se almacenan normalizados
 * (en minúsculas y sin espacios en blanco) para asegurar la consistencia en el sistema.
 */
public class CorreoContacto {

    /**
     * Correo electrónico almacenado de forma segura e inmutable.
     */
    private final String email;

    /**
     * Expresión regular para validar correos electrónicos.
     * Estructura: usuario + @ + dominio + . + extensión (mínimo 2 letras).
     */
    private static final String REGEX_CORREO = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * Construye un nuevo objeto CorreoContacto validando el formato del texto recibido.
     *
     * @param emailTexto La cadena de texto que contiene el correo electrónico.
     * @throws IllegalArgumentException Si el formato del correo no es válido.
     */
    public CorreoContacto(String emailTexto) {
        if (!validarFormato(emailTexto)) {
            throw new IllegalArgumentException("Error de formato: El correo '" + emailTexto + "' no cumple con la estructura estándar (usuario@dominio.ext).");
        }
        // Se almacena en minúsculas para mantener consistencia en la base de datos
        this.email = emailTexto.trim().toLowerCase();
    }

    /**
     * Evalúa si una cadena de texto cumple con el patrón Regex establecido para correos electrónicos.
     *
     * @param emailTexto El correo electrónico en formato String a evaluar.
     * @return {@code true} si el formato es válido, {@code false} si es nulo, vacío o no cumple el patrón.
     */
    public static boolean validarFormato(String emailTexto) {
        if (emailTexto == null || emailTexto.trim().isEmpty()) {
            return false;
        }

        Pattern patron = Pattern.compile(REGEX_CORREO);
        Matcher verificador = patron.matcher(emailTexto.trim());

        return verificador.matches();
    }

    /**
     * Obtiene el correo electrónico normalizado.
     *
     * @return El correo en formato {@code String}.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Representación textual del objeto correo.
     *
     * @return El correo electrónico almacenado.
     */
    @Override
    public String toString() {
        return email;
    }
}