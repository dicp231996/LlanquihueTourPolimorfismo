package view;

import data.CargarDatos;
import data.GestorEntidades;
import data.GestorEscritura;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.lang.reflect.Method;

/**
 * Ventana principal de la aplicación Llanquihue Tour.
 * Actúa como la interfaz gráfica central donde el usuario puede visualizar el inventario,
 * aplicar filtros polimórficos, buscar clientes por RUT y gestionar registros.
 * Coordina la comunicación entre las capas de datos (CargarDatos, GestorEscritura)
 * y la interfaz de usuario.
 */
public class VentanaPrincipal extends JFrame {

    private CargarDatos cargador;
    private GestorEscritura escritor;
    private GestorEntidades gestorEntidades;

    private JComboBox<String> comboFiltro;
    private JTextArea areaVisualizacion;
    private JButton btnAgregarRegistro;
    private JButton btnFinalizar;
    private JButton btnActualizar;

    private JPanel panelFiltroRut;

    /**
     * Constructor de la ventana principal.
     *
     * @param cargador  Instancia del cargador de datos para acceder a las entidades.
     * @param escritor  Instancia del gestor de escritura para persistencia de datos.
     */
    public VentanaPrincipal(CargarDatos cargador, GestorEscritura escritor) {
        this.cargador = cargador;
        this.escritor = escritor;
        this.gestorEntidades = new GestorEntidades(cargador);

        configurarVentana();
        inicializarComponentes();
        cargarDatosEnPantalla("Todos");
    }

    /**
     * Define las propiedades básicas del frame (título, tamaño, cierre, layout).
     */
    private void configurarVentana() {
        setTitle("Sistema de Gestión - Llanquihue Tour");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
    }

    /**
     * Crea, organiza y configura todos los componentes de la interfaz (paneles, botones, campos).
     * También define los escuchadores de eventos para la interactividad del sistema.
     */
    private void inicializarComponentes() {
        // Usamos BoxLayout en el eje Y para que los paneles colapsen dinámicamente al ocultarse
        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // --- Fila 1: Filtro General Polimórfico ---
        JPanel panelFiltroGeneral = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltroGeneral.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelFiltroGeneral.add(new JLabel("Filtrar por Entidad:"));

        comboFiltro = new JComboBox<>();
        comboFiltro.addItem("Todos");

        Set<Class<?>> clasesCargadas = cargador.obtenerClasesCargadas();
        for (Class<?> clase : clasesCargadas) {
            comboFiltro.addItem(clase.getSimpleName());
        }

        panelFiltroGeneral.add(comboFiltro);
        panelNorte.add(panelFiltroGeneral);

        // --- Fila 2: Buscador Específico de RUT ---
        panelFiltroRut = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltroRut.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelFiltroRut.add(new JLabel("Buscar Cliente por RUT:"));

        PanelBuscadorRut buscadorRut = new PanelBuscadorRut();
        panelFiltroRut.add(buscadorRut);

        JButton btnBuscarRut = new JButton("Buscar");
        panelFiltroRut.add(btnBuscarRut);
        panelNorte.add(panelFiltroRut);

        add(panelNorte, BorderLayout.NORTH);

        // --- ZONA CENTRAL ---
        areaVisualizacion = new JTextArea();
        areaVisualizacion.setEditable(false);
        areaVisualizacion.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollArea = new JScrollPane(areaVisualizacion);
        scrollArea.setBorder(BorderFactory.createTitledBorder("Inventario Activo"));
        add(scrollArea, BorderLayout.CENTER);

        // --- ZONA SUR ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregarRegistro = new JButton("Agregar Nuevo Registro");
        btnActualizar = new JButton("Actualizar Pantalla");
        btnFinalizar = new JButton("Finalizar");

        panelBotones.add(btnAgregarRegistro);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnFinalizar);

        add(panelBotones, BorderLayout.SOUTH);

        // --- EVENTOS ---
        comboFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboFiltro.getSelectedItem();

                cargarDatosEnPantalla(seleccion);

                // Lógica de visibilidad: buscador solo visible para clientes o todo
                if ("Todos".equals(seleccion) || "Cliente".equals(seleccion)) {
                    panelFiltroRut.setVisible(true);
                } else {
                    panelFiltroRut.setVisible(false);
                }

                panelNorte.revalidate();
                panelNorte.repaint();
            }
        });

        btnBuscarRut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rutObjetivo = buscadorRut.getRutFormateado();

                if (rutObjetivo.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this,
                            "La estructura del RUT está incompleta. Llene todos los campos.",
                            "Error de Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                buscarYMostrarCliente(rutObjetivo);
            }
        });

        btnAgregarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRegistro ventanaRegistro = new VentanaRegistro(cargador, escritor);
                ventanaRegistro.setVisible(true);
            }
        });

        btnFinalizar.addActionListener(e -> System.exit(0));

        btnActualizar.addActionListener(e -> {
            cargador.recargar();
            String seleccionActual = (String) comboFiltro.getSelectedItem();
            cargarDatosEnPantalla(seleccionActual);
        });
    }

    /**
     * Actualiza el área de visualización llamando al generador de reportes polimórficos.
     *
     * @param filtro Nombre de la clase o criterio para filtrar los datos.
     */
    private void cargarDatosEnPantalla(String filtro) {
        String textoProcesado = gestorEntidades.generarReportePolimorfico(filtro);
        areaVisualizacion.setText(textoProcesado);
        areaVisualizacion.setCaretPosition(0);
    }

    /**
     * Realiza una búsqueda específica de un cliente utilizando reflexión para invocar
     * el método {@code getRut()} en tiempo de ejecución.
     *
     * @param rutBuscado RUT formateado a buscar.
     */
    private void buscarYMostrarCliente(String rutBuscado) {
        boolean encontrado = false;

        for (ArrayList<Object> listaEntidades : cargador.getBaseDatosLlanquihueTour().values()) {
            for (Object entidad : listaEntidades) {
                if (entidad.getClass().getSimpleName().equals("Cliente")) {
                    try {
                        Method metodoGetRut = entidad.getClass().getMethod("getRut");
                        String rutRegistrado = String.valueOf(metodoGetRut.invoke(entidad));

                        if (rutRegistrado.contains(rutBuscado)) {
                            Method metodoInformacion = entidad.getClass().getMethod("informacionPersonal");
                            String datosCliente = (String) metodoInformacion.invoke(entidad);

                            areaVisualizacion.setText("=== RESULTADO DE BÚSQUEDA ===\n\n" + datosCliente);
                            encontrado = true;
                            break;
                        }
                    } catch (Exception ex) {
                        // Se ignoran entidades que no cumplan el contrato de búsqueda
                    }
                }
            }
            if (encontrado) break;
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(this,
                    "El RUT " + rutBuscado + " no registra coincidencias en el sistema.",
                    "Búsqueda Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Clase auxiliar interna para gestionar la entrada del RUT en la interfaz.
     */
    private static class PanelBuscadorRut extends JPanel {
        private JTextField campo1, campo2, campo3, campo4;

        public PanelBuscadorRut() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));

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

        private JTextField crearCampoFiltrado(int limite, boolean soloNumeros) {
            JTextField campo = new JTextField(limite);
            campo.setHorizontalAlignment(JTextField.CENTER);

            ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String stringActual = fb.getDocument().getText(0, fb.getDocument().getLength());
                    String stringResultante = stringActual.substring(0, offset) + text + stringActual.substring(offset + length);

                    if (stringResultante.length() <= limite) {
                        if (soloNumeros && stringResultante.matches("[0-9]*")) {
                            super.replace(fb, offset, length, text, attrs);
                        } else if (!soloNumeros && stringResultante.matches("[0-9Kk]*")) {
                            super.replace(fb, offset, length, text.toUpperCase(), attrs);
                        }
                    }
                }
            });
            return campo;
        }

        public String getRutFormateado() {
            String c1 = campo1.getText().trim();
            String c2 = campo2.getText().trim();
            String c3 = campo3.getText().trim();
            String c4 = campo4.getText().trim();

            if (c1.isEmpty() || c2.isEmpty() || c3.isEmpty() || c4.isEmpty()) {
                return "";
            }
            return c1 + "." + c2 + "." + c3 + "-" + c4;
        }
    }
}