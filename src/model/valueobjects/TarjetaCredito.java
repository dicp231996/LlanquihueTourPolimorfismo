package model.valueobjects;

public class TarjetaCredito {

    private final String numero;

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
     * @param numeroTarjeta Cadena que contiene exclusivamente números.
     * @return true si la suma de comprobación es múltiplo de 10.
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

    public String getNumero() {
        return numero;
    }

    /**
     * Devuelve los últimos 4 dígitos de la tarjeta para su visualización segura.
     */
    public String obtenerNumeroEnmascarado() {
        if (numero.length() <= 4) {
            return numero;
        }
        String ultimosCuatro = numero.substring(numero.length() - 4);
        String mascara = "*".repeat(numero.length() - 4);
        return mascara + ultimosCuatro;
    }

    @Override
    public String toString() {
        return obtenerNumeroEnmascarado();
    }
}

