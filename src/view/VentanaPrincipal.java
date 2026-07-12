package view;

import data.CargarDatos;
import data.GestorEntidades;
import data.GestorEscritura;
import model.interfaces.Registrable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class VentanaPrincipal extends JFrame {

    private CargarDatos cargador;
    private GestorEscritura escritor;
    private GestorEntidades gestorEntidades;

    private JComboBox<String> comboFiltro;
    private JTextArea areaVisualizacion;
    private JButton btnAgregarRegistro;
    private JButton btnFinalizar;

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
        btnFinalizar = new JButton("Finalizar");

        panelBotones.add(btnAgregarRegistro);
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
    }

    private void cargarDatosEnPantalla(String filtro) {
        areaVisualizacion.setText("");

        ArrayList<Registrable> inventarioGlobal = gestorEntidades.obtenerInventarioCompleto();

        if (inventarioGlobal.isEmpty()) {
            areaVisualizacion.append("No hay datos registrados en el sistema.\n");
            return;
        }

        int contador = 0;
        for (Registrable instancia : inventarioGlobal) {
            String nombreClaseInstancia = instancia.getClass().getSimpleName();

            if (filtro.equals("Todos") || nombreClaseInstancia.equals(filtro)) {
                ;
                areaVisualizacion.append(instancia.mostrarResumen()+"\n");
                areaVisualizacion.append("----------------------------------------------------------------------\n");
                contador++;
            }
        }

        areaVisualizacion.append("\nTotal de registros mostrados: " + contador);

        // Forzar al scroll a volver arriba después de cargar texto largo
        areaVisualizacion.setCaretPosition(0);
    }
}