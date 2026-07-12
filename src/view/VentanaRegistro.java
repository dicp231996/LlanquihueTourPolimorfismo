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
import java.util.Set;

public class VentanaRegistro extends JFrame {

    private CargarDatos cargador;
    private GestorEscritura escritor;
    private DiccionarioFormularios diccionario;
    private java.util.List<JTextField> camposGenerados;
    private Class<?> claseActiva;

    private JComboBox<String> comboClases;
    private JPanel panelFormulario;
    private JButton btnGuardar;
    private JButton btnFinalizar;

    private Class<?>[] arregloClases;

    public VentanaRegistro(CargarDatos cargador, GestorEscritura escritor) {
        this.cargador = cargador;
        this.escritor = escritor;
        this.diccionario = new DiccionarioFormularios();

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
                    String textoIngresado = camposGenerados.get(i).getText().trim();
                    constructorTexto.append(textoIngresado);

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

                    for (JTextField campo : camposGenerados) {
                        campo.setText("");
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

            String textoEtiqueta;
            if (etiquetasAmigables != null && i < etiquetasAmigables.length) {
                textoEtiqueta = etiquetasAmigables[i];
            } else {
                textoEtiqueta = "Parámetro " + (i + 1);
            }

            JLabel etiqueta = new JLabel(textoEtiqueta + " (" + tipoDato + "): ");
            etiqueta.setPreferredSize(new Dimension(200, 25));

            JTextField campoTexto = new JTextField(20);

            camposGenerados.add(campoTexto);

            fila.add(etiqueta);
            fila.add(campoTexto);
            panelFormulario.add(fila);
        }

        btnGuardar.setEnabled(true);
        panelFormulario.revalidate();
        panelFormulario.repaint();
    }
}