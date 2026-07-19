package view;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

/**
 * Panel de interfaz gráfica diseñado para la entrada de datos del Rol Único Tributario (RUT) chileno.
 * Este componente desglosa la entrada en cuatro campos separados para facilitar el formato estándar
 * (XX.XXX.XXX-X). Incluye filtros de validación internos para restringir la entrada a dígitos
 * y el dígito verificador "K", asegurando la integridad del dato antes de su procesamiento.
 */
public class PanelBuscadorRut extends JPanel {

    private JTextField campo1, campo2, campo3, campo4;

    /**
     * Constructor que inicializa el panel. Configura el diseño (layout) y los campos
     * de texto con sus respectivos filtros de restricción.
     */
    public PanelBuscadorRut() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));

        // Instanciamos los campos definiendo su límite y si aceptan solo números
        campo1 = crearCampoFiltrado(3, true);
        campo2 = crearCampoFiltrado(3, true);
        campo3 = crearCampoFiltrado(3, true);
        campo4 = crearCampoFiltrado(1, false);

        add(campo1);
        add(new JLabel("."));
        add(campo2);
        add(new JLabel("."));
        add(campo3);
        add(new JLabel("-"));
        add(campo4);
    }

    /**
     * Factory method que crea un {@link JTextField} configurado con un {@link DocumentFilter}
     * para restringir la entrada de datos en tiempo real.
     *
     * @param limite      Número máximo de caracteres permitidos en el campo.
     * @param soloNumeros Si es {@code true}, solo permite dígitos; si es {@code false},
     *                    permite dígitos y la letra 'K' (o 'k').
     * @return Un {@code JTextField} configurado y con el filtro aplicado.
     */
    private JTextField crearCampoFiltrado(int limite, boolean soloNumeros) {
        JTextField campo = new JTextField(limite);
        campo.setHorizontalAlignment(JTextField.CENTER);

        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Proyectamos cómo quedaría el texto si permitimos la inserción
                String stringActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String stringResultante = stringActual.substring(0, offset) + text + stringActual.substring(offset + length);

                // Primera barrera: Límite de longitud
                if (stringResultante.length() <= limite) {

                    // Segunda barrera: Restricción de dominio de caracteres
                    if (soloNumeros && stringResultante.matches("[0-9]*")) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                    else if (!soloNumeros && stringResultante.matches("[0-9Kk]*")) {
                        // Forzamos que la K sea mayúscula por consistencia
                        super.replace(fb, offset, length, text.toUpperCase(), attrs);
                    }
                }
            }
        });
        return campo;
    }

    /**
     * Construye y retorna la cadena final del RUT unificando los valores de los cuatro campos.
     * <p>
     * El formato resultante es "XX.XXX.XXX-X".
     *
     * @return El RUT formateado como {@code String}, o una cadena vacía si alguno de los campos
     *         está incompleto o vacío.
     */
    public String getRutFormateado() {
        String c1 = campo1.getText().trim();
        String c2 = campo2.getText().trim();
        String c3 = campo3.getText().trim();
        String c4 = campo4.getText().trim();

        if (c1.isEmpty() || c2.isEmpty() || c3.isEmpty() || c4.isEmpty()) {
            return ""; // Retorna vacío si la construcción está incompleta
        }

        return c1 + "." + c2 + "." + c3 + "-" + c4;
    }
}