package view;

import data.CargarDatos;
import data.GestorEscritura;
import data.DiccionarioFormularios;
import data.GeneradorCodigosServicios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Ventana de interfaz gráfica diseñada para el registro dinámico de nuevas entidades en el sistema.
 * <p>
 * Esta clase utiliza introspección (Reflection) para detectar clases instanciables dentro de los
 * paquetes del modelo y generar automáticamente formularios de entrada basados en los parámetros
 * de los constructores de dichas clases. Se apoya en {@link DiccionarioFormularios} para asignar
 * etiquetas legibles a los campos generados.
 */
public class VentanaRegistro extends JFrame {

    private CargarDatos cargador;
    private GestorEscritura escritor;
    private DiccionarioFormularios diccionario;
    private Class<?> claseActiva;

    private JComboBox<String> comboClases;
    private JPanel panelFormulario;
    private JButton btnGuardar;
    private JButton btnFinalizar;

    private Class<?>[] arregloClases;

    private ArrayList<JComponent> camposGenerados = new ArrayList<>();
    private Map<String, String[]> configuracionDesplegables;

    /**
     * Escanea el paquete de actividades en busca de clases para poblar dinámicamente las opciones.
     *
     * @return Arreglo de String con los nombres de las especialidades formateados.
     */
    private String[] obtenerEspecialidadesDinamicas() {
        ArrayList<String> opcionesFormateadas = new ArrayList<>();
        String paquete = "model.entities.activities";
        String rutaDirectorio = paquete.replace('.', '/');

        try {
            java.net.URL recurso = Thread.currentThread().getContextClassLoader().getResource(rutaDirectorio);

            if (recurso != null) {
                java.io.File directorio = new java.io.File(recurso.getFile());

                if (directorio.exists()) {
                    for (String archivo : directorio.list()) {
                        if (archivo.endsWith(".class")) {
                            String nombreClase = archivo.substring(0, archivo.length() - 6);
                            String nombreFormateado = nombreClase.replaceAll("(?<!^)(?=[A-Z])", " ");
                            opcionesFormateadas.add(nombreFormateado);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al escanear el paquete de actividades: " + e.getMessage());
        }

        return opcionesFormateadas.toArray(new String[0]);
    }

    /**
     * Escanea los paquetes del modelo mediante reflexión para encontrar todas las clases
     * que son instanciables (no abstractas y no interfaces).
     *
     * @return Arreglo de objetos {@code Class} encontrados.
     */
    private Class<?>[] obtenerTodasLasClasesRegistrables() {
        ArrayList<Class<?>> clasesEncontradas = new ArrayList<>();
        String[] paquetes = {
                "model.entities.activities",
                "model.entities.assets",
                "model.entities.people",
                "model.entities.business"
        };

        for (String paquete : paquetes) {
            String rutaDirectorio = paquete.replace('.', '/');
            try {
                java.net.URL recurso = Thread.currentThread().getContextClassLoader().getResource(rutaDirectorio);
                if (recurso != null) {
                    java.io.File directorio = new java.io.File(recurso.getFile());
                    if (directorio.exists()) {
                        for (String archivo : directorio.list()) {
                            if (archivo.endsWith(".class")) {
                                String nombreClase = archivo.substring(0, archivo.length() - 6);
                                try {
                                    Class<?> clase = Class.forName(paquete + "." + nombreClase);
                                    if (!Modifier.isAbstract(clase.getModifiers()) && !clase.isInterface()) {
                                        clasesEncontradas.add(clase);
                                    }
                                } catch (Exception e) {
                                    // Se ignoran clases inaccesibles
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al escanear el paquete " + paquete + ": " + e.getMessage());
            }
        }
        return clasesEncontradas.toArray(new Class<?>[0]);
    }

    /**
     * Constructor de la ventana de registro.
     *
     * @param cargador Referencia al cargador para recuperar datos y opciones de listas.
     * @param escritor Referencia al gestor de persistencia para guardar nuevas entidades.
     */
    public VentanaRegistro(CargarDatos cargador, GestorEscritura escritor) {
        this.cargador = cargador;
        this.escritor = escritor;
        this.diccionario = new DiccionarioFormularios();

        this.configuracionDesplegables = new HashMap<>();
        this.configuracionDesplegables.put("Nivel de Inglés", new String[]{"A1","A2","B1","B2","C1","C2"});
        this.configuracionDesplegables.put("Especialidad", obtenerEspecialidadesDinamicas());
        this.configuracionDesplegables.put("Cliente", cargador.obtenerOpcionesClientes());
        this.configuracionDesplegables.put("Servicio Contratado", cargador.obtenerOpcionesServicios());

        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Configura el título y dimensiones de la ventana.
     */
    private void configurarVentana() {
        setTitle("Registro Dinámico de Entidades");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa los componentes de la interfaz, incluyendo la creación del formulario dinámico.
     */
    private void inicializarComponentes() {
        JPanel panelSeleccion = new JPanel(new FlowLayout());
        panelSeleccion.add(new JLabel("Seleccione entidad a registrar:"));

        Class<?>[] clasesDisponibles = obtenerTodasLasClasesRegistrables();
        comboClases = new JComboBox<>();
        arregloClases = clasesDisponibles;

        for (Class<?> clase : clasesDisponibles) {
            String nombreSeparado = clase.getSimpleName().replaceAll("([a-z])([A-Z]+)", "$1 $2");
            comboClases.addItem(nombreSeparado);
        }

        panelSeleccion.add(comboClases);
        add(panelSeleccion, BorderLayout.NORTH);

        panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnGuardar = new JButton("Guardar Registro");
        btnGuardar.setEnabled(false);
        btnFinalizar = new JButton("Finalizar");
        panelInferior.add(btnGuardar);
        panelInferior.add(btnFinalizar);
        add(panelInferior, BorderLayout.SOUTH);

        btnFinalizar.addActionListener(e -> dispose());

        // Lógica de guardado: extrae datos de los componentes y escribe mediante el gestor
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder constructorTexto = new StringBuilder();
                for (int i = 0; i < camposGenerados.size(); i++) {
                    JComponent componenteActivo = camposGenerados.get(i);
                    String textoIngresado = "";

                    if (componenteActivo instanceof JTextField) {
                        textoIngresado = ((JTextField) componenteActivo).getText().trim();
                    }
                    else if (componenteActivo instanceof JComboBox) {
                        String seleccionBruta = (String) ((JComboBox<?>) componenteActivo).getSelectedItem();
                        if (seleccionBruta != null) {
                            if (seleccionBruta.contains(" - ")) {
                                textoIngresado = seleccionBruta.split(" - ")[0].trim();
                            } else {
                                textoIngresado = seleccionBruta.trim();
                            }
                        }
                    }
                    else if (componenteActivo instanceof PanelFechaSelector) {
                        textoIngresado = ((PanelFechaSelector) componenteActivo).getFechaFormateada();
                    }

                    constructorTexto.append(textoIngresado);
                    if (i < camposGenerados.size() - 1) constructorTexto.append(";");
                }

                String lineaDatos = constructorTexto.toString();
                boolean guardadoExitoso = escritor.registrarNuevaInstancia(claseActiva.getSimpleName(), lineaDatos);

                if (guardadoExitoso) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "Registro añadido exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario();
                    generarFormulario(claseActiva);
                } else {
                    JOptionPane.showMessageDialog(VentanaRegistro.this, "Error al escribir en archivo.", "Fallo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        comboClases.addActionListener(e -> {
            int index = comboClases.getSelectedIndex();
            if (index >= 0) generarFormulario(arregloClases[index]);
        });

        if (comboClases.getItemCount() > 0) comboClases.setSelectedIndex(0);
    }

    /**
     * Limpia los inputs del formulario después de una operación exitosa.
     */
    private void limpiarFormulario() {
        for (JComponent componente : camposGenerados) {
            if (componente instanceof JTextField && ((JTextField) componente).isEditable()) ((JTextField) componente).setText("");
            else if (componente instanceof JComboBox) ((JComboBox<?>) componente).setSelectedIndex(0);
            else if (componente instanceof PanelFechaSelector) ((PanelFechaSelector) componente).reiniciar();
        }
    }

    /**
     * Genera dinámicamente los campos de entrada basándose en los constructores de la clase.
     *
     * @param claseSeleccionada Clase sobre la cual generar el formulario.
     */
    private void generarFormulario(Class<?> claseSeleccionada) {
        panelFormulario.removeAll();
        camposGenerados = new ArrayList<>();
        claseActiva = claseSeleccionada;

        Constructor<?>[] constructores = claseSeleccionada.getConstructors();
        Constructor<?> constructorPrincipal = constructores[0];
        for (Constructor<?> c : constructores) {
            if (c.getParameterCount() > constructorPrincipal.getParameterCount()) constructorPrincipal = c;
        }

        Parameter[] parametros = constructorPrincipal.getParameters();
        String[] etiquetasAmigables = diccionario.obtenerEtiquetas(claseSeleccionada);

        for (int i = 0; i < parametros.length; i++) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
            String tipoDato = parametros[i].getType().getSimpleName();
            String textoEtiqueta = (etiquetasAmigables != null && i < etiquetasAmigables.length) ? etiquetasAmigables[i] : "Parámetro " + (i + 1);

            JLabel etiqueta = new JLabel(textoEtiqueta + " (" + tipoDato + "): ");
            etiqueta.setPreferredSize(new Dimension(200, 25));
            fila.add(etiqueta);

            if (configuracionDesplegables != null && configuracionDesplegables.containsKey(textoEtiqueta)){
                JComboBox<String> comboDesplegable = new JComboBox<>(configuracionDesplegables.get(textoEtiqueta));
                camposGenerados.add(comboDesplegable);
                fila.add(comboDesplegable);
            } else {
                JTextField campoTexto = new JTextField(20);
                // Automatización de valores por defecto (ej: Códigos, Fechas)
                if (textoEtiqueta.equalsIgnoreCase("Codigo del tour") || textoEtiqueta.equalsIgnoreCase("Código de Servicio")) {
                    String prefijo = determinarPrefijo(claseSeleccionada.getSimpleName());
                    if (!prefijo.isEmpty()) {
                        campoTexto.setText(GeneradorCodigosServicios.calcularProximoCodigo(cargador.getBaseDatosLlanquihueTour(), prefijo));
                        campoTexto.setEditable(false);
                    }
                }
                else if (textoEtiqueta.equalsIgnoreCase("Número de Orden de Compra") || textoEtiqueta.equalsIgnoreCase("Orden de Compra")) {
                    campoTexto.setText(GeneradorCodigosServicios.calcularProximoCodigo(cargador.getBaseDatosLlanquihueTour(), "ORD-"));
                    campoTexto.setEditable(false);
                }
                else if (textoEtiqueta.equalsIgnoreCase("Fecha") || textoEtiqueta.equalsIgnoreCase("Fecha de Registro")) {
                    campoTexto.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    campoTexto.setEditable(false);
                }
                else if (textoEtiqueta.equalsIgnoreCase("Fecha Reserva") || textoEtiqueta.equalsIgnoreCase("Fecha de la Reserva")) {
                    PanelFechaSelector panelFecha = new PanelFechaSelector();
                    camposGenerados.add(panelFecha);
                    fila.add(panelFecha);
                    panelFormulario.add(fila);
                    continue;
                }

                camposGenerados.add(campoTexto);
                fila.add(campoTexto);
            }
            panelFormulario.add(fila);
        }
        btnGuardar.setEnabled(true);
        panelFormulario.revalidate();
        panelFormulario.repaint();
    }

    private String determinarPrefijo(String nombreClase) {
        return switch (nombreClase) {
            case "RutaGastronomica" -> "GAS-";
            case "ExcursionCultural" -> "EXC-";
            case "PaseoLacustre" -> "LAC-";
            case "TrekkingAltaMontania" -> "TREK-";
            default -> "";
        };
    }

    /**
     * Panel auxiliar para la selección de fechas mediante tres combos.
     */
    class PanelFechaSelector extends JPanel {
        private JComboBox<String> comboDia, comboMes, comboAnio;

        public PanelFechaSelector() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
            String[] dias = new String[31];
            for (int i = 0; i < 31; i++) dias[i] = String.format("%02d", i + 1);
            String[] meses = new String[12];
            for (int i = 0; i < 12; i++) meses[i] = String.format("%02d", i + 1);
            String[] anios = new String[11];
            int anioActual = LocalDate.now().getYear();
            for (int i = 0; i < 11; i++) anios[i] = String.valueOf(anioActual + i);

            comboDia = new JComboBox<>(dias);
            comboMes = new JComboBox<>(meses);
            comboAnio = new JComboBox<>(anios);
            add(comboDia); add(new JLabel("/")); add(comboMes); add(new JLabel("/")); add(comboAnio);
        }

        public String getFechaFormateada() {
            return comboDia.getSelectedItem() + "-" + comboMes.getSelectedItem() + "-" + comboAnio.getSelectedItem();
        }

        public void reiniciar() {
            comboDia.setSelectedIndex(0);
            comboMes.setSelectedIndex(0);
            comboAnio.setSelectedIndex(0);
        }
    }
}