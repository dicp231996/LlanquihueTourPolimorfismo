package model.valueobjects;

/**
 * Clase que representa un objeto de valor (Value Object) para gestionar números de tarjeta de crédito.
 * Garantiza la integridad de los datos mediante la limpieza de formato y la aplicación
 * del algoritmo de Luhn (Módulo 10) para la validación de sumas de comprobación.
 * <p>
 * Los objetos de esta clase son inmutables. Proporciona métodos para obtener
 * el número normalizado y una versión enmascarada por seguridad para visualización.
 */
public class TarjetaCredito {

    /**
     * Número de tarjeta almacenado de forma segura, normalizada y sin caracteres especiales.
     */
    private final String numero;

    /**
     * Construye un nuevo objeto TarjetaCredito tras validar el número proporcionado.
     * <p>
     * El proceso de validación incluye:
     * 1. Verificación de que el número no sea nulo o vacío.
     * 2. Limpieza de espacios y guiones.
     * 3. Validación de que contenga solo dígitos.
     * 4. Verificación de integridad matemática mediante el algoritmo de Luhn.
     *
     * @param numeroTexto El número de tarjeta en formato de cadena (puede incluir espacios o guiones).
     * @throws IllegalArgumentException Si el número es nulo, vacío, contiene caracteres no numéricos o no pasa el test de Luhn.
     */
    public TarjetaCredito(String numeroTexto) {
        if (numeroTexto == null || numeroTexto.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El número de tarjeta no puede ser nulo o vacío.");
        }

        // 1. Limpieza de formato: Se eliminan espacios en blanco y guiones
        String numeroLimpio = numeroTexto.replaceAll("[\\s-]", "");

        // 2. Validación estructural básica: Solo deben quedar números
        if (!numeroLimpio.matches("^\\d+$")) {
            throw new IllegalArgumentException("Error: El número de tarjeta contiene caracteres inválidos.");
        }

        // 3. Validación matemática mediante el Algoritmo de Luhn
        if (!esValidoPorLuhn(numeroLimpio)) {
            throw new IllegalArgumentException("Error de validación: El número ingresado no pasa el control de integridad de Luhn.");
        }

        this.numero = numeroLimpio;
    }

    /**
     * Aplica el algoritmo de Luhn (Módulo 10) a una cadena de dígitos.
     * Es un método estático utilizado para validar que el número de tarjeta sea consistente
     * antes de crear el objeto.
     *
     * @param numeroTarjeta Cadena que contiene exclusivamente números.
     * @return {@code true} si la suma de comprobación es múltiplo de 10, {@code false} en caso contrario.
     */
    public static boolean esValidoPorLuhn(String numeroTarjeta) {
        int sumaTotal = 0;
        boolean alternarPosicion = false;

        // Se itera el arreglo de caracteres desde el final (derecha a izquierda)
        for (int i = numeroTarjeta.length() - 1; i >= 0; i--) {

            // Convertimos el carácter actual a su valor numérico real
            int digito = Character.getNumericValue(numeroTarjeta.charAt(i));

            // Paso clave del algoritmo: duplicar el valor de las posiciones alternas
            if (alternarPosicion) {
                digito = digito * 2;

                // Si al duplicar el número se vuelve de dos cifras (ej. 7 * 2 = 14),
                // se deben sumar sus dígitos (1 + 4 = 5).
                // Restar 9 es matemáticamente equivalente y más eficiente en cómputo.
                if (digito > 9) {
                    digito = digito - 9;
                }
            }

            sumaTotal += digito;

            // Invertimos la bandera para la siguiente iteración
            alternarPosicion = !alternarPosicion;
        }

        // Si el total exacto es divisible por 10 (módulo 10 == 0), el número es válido
        return (sumaTotal % 10 == 0);
    }

    /**
     * Obtiene el número de tarjeta de crédito completo y normalizado.
     *
     * @return El número de tarjeta como {@code String}.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Devuelve los últimos 4 dígitos de la tarjeta para su visualización segura,
     * reemplazando los dígitos anteriores con asteriscos.
     *
     * @return El número enmascarado (ej: ************1234).
     */
    public String obtenerNumeroEnmascarado() {
        if (numero.length() <= 4) {
            return numero;
        }
        String ultimosCuatro = numero.substring(numero.length() - 4);
        String mascara = "*".repeat(numero.length() - 4);
        return mascara + ultimosCuatro;
    }

    /**
     * Representación textual del objeto, retornando el número enmascarado.
     *
     * @return El número de tarjeta enmascarado.
     */
    @Override
    public String toString() {
        return obtenerNumeroEnmascarado();
    }
}