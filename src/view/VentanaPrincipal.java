package view;

import data.CargarDatos;
import data.GestorEntidades;
import data.GestorEscritura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.lang.Runnable;

public class VentanaPrincipal extends JFrame {

    private CargarDatos cargador;
    private GestorEscritura escritor;
    private GestorEntidades gestorEntidades;

    private JComboBox<String> comboFiltro;
    private JTextArea areaVisualizacion;
    private JButton btnAgregarRegistro;
    private JButton btnFinalizar;
    private JButton btnActualizar;


    public VentanaPrincipal(CargarDatos cargador, GestorEscritura escritor) {
        this.cargador = cargador;
        this.escritor = escritor;
        this.gestorEntidades = new GestorEntidades(cargador);

        configurarVentana();
        inicializarComponentes();
        cargarDatosEnPantalla("Todos");
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión - Llanquihue Tour");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.add(new JLabel("Filtrar por Entidad:"));

        comboFiltro = new JComboBox<>();
        comboFiltro.addItem("Todos");

        Set<Class<?>> clasesCargadas = cargador.obtenerClasesCargadas();
        for (Class<?> clase : clasesCargadas) {
            comboFiltro.addItem(clase.getSimpleName());
        }

        panelFiltro.add(comboFiltro);
        add(panelFiltro, BorderLayout.NORTH);

        areaVisualizacion = new JTextArea();
        areaVisualizacion.setEditable(false);
        areaVisualizacion.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollArea = new JScrollPane(areaVisualizacion);
        scrollArea.setBorder(BorderFactory.createTitledBorder("Inventario Activo"));
        add(scrollArea, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnAgregarRegistro = new JButton("Agregar Nuevo Registro");
        btnActualizar = new JButton("Actualizar Pantalla");
        btnFinalizar = new JButton("Finalizar");


        panelBotones.add(btnAgregarRegistro);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnFinalizar);

        add(panelBotones, BorderLayout.SOUTH);

        comboFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboFiltro.getSelectedItem();
                cargarDatosEnPantalla(seleccion);
            }
        });

        btnAgregarRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Definimos el comportamiento que ocurrirá de forma asíncrona (Callback)
                Runnable instruccionRecarga = new Runnable() {
                    @Override
                    public void run() {
                        cargador.recargar();
                        String seleccionActual = (String) comboFiltro.getSelectedItem();
                        cargarDatosEnPantalla(seleccionActual);
                    }
                };

                VentanaRegistro ventanaRegistro = new VentanaRegistro(cargador, escritor);
                ventanaRegistro.setVisible(true);
            }
        });

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Sobreescritura en RAM: Obligar al sistema a leer los archivos nuevamente
                cargador.recargar();

                // 2. Conservar el estado del filtro visual
                String seleccionActual = (String) comboFiltro.getSelectedItem();

                // 3. Sobreescritura Visual: Reemplazar todo el texto del JTextArea
                cargarDatosEnPantalla(seleccionActual);
            }
        });
    }

    private void cargarDatosEnPantalla(String filtro) {
        String textoProcesado = gestorEntidades.generarReportePolimorfico(filtro);

        // 2. Actualizamos el componente visual
        areaVisualizacion.setText(textoProcesado);

        // 3. Forzamos al scroll a volver arriba después de cargar el texto
        areaVisualizacion.setCaretPosition(0);
    }
}
