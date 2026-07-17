package view;

import data.CargarDatos;
import data.DiccionarioFormularios;
import data.GestorEscritura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Set;

import java.util.Map;
import java.util.ArrayList;

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
    private Runnable accionActualizarVista;

    private ArrayList<JComponent> camposGenerados = new ArrayList<>();
    private Map<String, String[]> configuracionDesplegables;

    private String[] obtenerEspecialidadesDinamicas() {
        ArrayList<String> opcionesFormateadas = new ArrayList<>();

        // 1. Definimos la ruta estructural del paquete
        String paquete = "model.entities.activities";
        String rutaDirectorio = paquete.replace('.', '/');

        try {
            // 2. Localizamos la carpeta en el sistema de archivos
            java.net.URL recurso = Thread.currentThread().getContextClassLoader().getResource(rutaDirectorio);

            if (recurso != null) {
                java.io.File directorio = new java.io.File(recurso.getFile());

                if (directorio.exists()) {
                    // 3. Iteramos sobre los archivos compilados
                    for (String archivo : directorio.list()) {
                        if (archivo.endsWith(".class")) {

                            // Quitamos la extensión ".class" (6 caracteres)
                            String nombreClase = archivo.substring(0, archivo.length() - 6);

                            // Aplicamos la expresión regular para el espaciado
                            String nombreFormateado = nombreClase.replaceAll("(?<!^)(?=[A-Z])", " ");

                            opcionesFormateadas.add(nombreFormateado);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al escanear el paquete de actividades: " + e.getMessage());
        }

        // 4. Transformamos la lista dinámica en un arreglo estático
        return opcionesFormateadas.toArray(new String[0]);
    }

    public VentanaRegistro(CargarDatos cargador, GestorEscritura escritor) {
        this.cargador = cargador;
        this.escritor = escritor;
        this.diccionario = new DiccionarioFormularios();
        this.accionActualizarVista = accionActualizarVista;

        this.configuracionDesplegables = new HashMap<>();
        this.configuracionDesplegables.put("Nivel de Inglés", new String[]{"A1","A2","B1","B2","C1","C2"});
        this.configuracionDesplegables.put("Especialidad", obtenerEspecialidadesDinamicas());


        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Registro Dinámico de Entidades");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        JPanel panelSeleccion = new JPanel(new FlowLayout());
        panelSeleccion.add(new JLabel("Seleccione entidad a registrar:"));

        Set<Class<?>> clasesCargadas = cargador.obtenerClasesCargadas();
        comboClases = new JComboBox<>();
        arregloClases = new Class<?>[clasesCargadas.size()];

        int index = 0;
        for (Class<?> clase : clasesCargadas) {
            arregloClases[index] = clase;
            String nombreSeparado = clase.getSimpleName().replaceAll("([a-z])([A-Z]+)", "$1 $2");
            comboClases.addItem(nombreSeparado);
            index++;
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

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                StringBuilder constructorTexto = new StringBuilder();
                for (int i = 0; i < camposGenerados.size(); i++) {

                    JComponent componenteActivo = camposGenerados.get(i);
                    String textoIngresado = "";

                    // Evaluación empírica de la clase del componente
                    if (componenteActivo instanceof JTextField) {
                        textoIngresado = ((JTextField) componenteActivo).getText().trim();
                    }
                    else if (componenteActivo instanceof JComboBox) {
                        textoIngresado = (String) ((JComboBox<?>) componenteActivo).getSelectedItem();
                    }

                    constructorTexto.append(textoIngresado);

                    // Lógica para añadir el separador, excepto en el último elemento
                    if (i < camposGenerados.size() - 1) {
                        constructorTexto.append(";");
                    }
                }

                String lineaDatos = constructorTexto.toString();
                String nombreClase = claseActiva.getSimpleName();

                boolean guardadoExitoso = escritor.registrarNuevaInstancia(nombreClase, lineaDatos);

                if (guardadoExitoso) {
                    JOptionPane.showMessageDialog(VentanaRegistro.this,
                            "Registro añadido a la base de datos exitosamente.",
                            "Operación Completada", JOptionPane.INFORMATION_MESSAGE);

                    for (JComponent componente : camposGenerados) {
                        if (componente instanceof JTextField) {
                            ((JTextField) componente).setText("");
                        }
                        else if (componente instanceof JComboBox) {
                            // Devolver el menú desplegable a su primera opción
                            ((JComboBox<?>) componente).setSelectedIndex(0);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(VentanaRegistro.this,
                            "Error al intentar escribir en el archivo .txt.",
                            "Fallo de I/O", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        comboClases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexSeleccionado = comboClases.getSelectedIndex();
                if (indexSeleccionado >= 0) {
                    generarFormulario(arregloClases[indexSeleccionado]);
                }
            }
        });

        if (comboClases.getItemCount() > 0) {
            comboClases.setSelectedIndex(0);
        }
    }

    private void generarFormulario(Class<?> claseSeleccionada) {
        panelFormulario.removeAll();

        camposGenerados = new java.util.ArrayList<>();
        claseActiva = claseSeleccionada;

        Constructor<?>[] constructores = claseSeleccionada.getConstructors();
        Constructor<?> constructorPrincipal = constructores[0];

        for (Constructor<?> c : constructores) {
            if (c.getParameterCount() > constructorPrincipal.getParameterCount()) {
                constructorPrincipal = c;
            }
        }

        Parameter[] parametros = constructorPrincipal.getParameters();
        String[] etiquetasAmigables = diccionario.obtenerEtiquetas(claseSeleccionada);

        for (int i = 0; i < parametros.length; i++) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));

            String tipoDato = parametros[i].getType().getSimpleName();

            String nombreParametro = parametros[i].getName();

            String textoEtiqueta;
            if (etiquetasAmigables != null && i < etiquetasAmigables.length) {
                textoEtiqueta = etiquetasAmigables[i];
            } else {
                textoEtiqueta = "Parámetro " + (i + 1);
            }

            JLabel etiqueta = new JLabel(textoEtiqueta + " (" + tipoDato + "): ");
            etiqueta.setPreferredSize(new Dimension(200, 25));
            fila.add(etiqueta);

            if (configuracionDesplegables != null && configuracionDesplegables.containsKey(textoEtiqueta)){
                String[] opciones = configuracionDesplegables.get(textoEtiqueta);
                JComboBox<String> comboDesplegable = new JComboBox<>(opciones);

                camposGenerados.add(comboDesplegable);
                fila.add(comboDesplegable);
            } else {
                JTextField campoTexto = new JTextField(20);
                camposGenerados.add(campoTexto);
                fila.add(campoTexto);
            }
            panelFormulario.add(fila);
        }
        btnGuardar.setEnabled(true);
        panelFormulario.revalidate();
        panelFormulario.repaint();
    }
}