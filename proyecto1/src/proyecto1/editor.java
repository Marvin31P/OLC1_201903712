package proyecto1;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class editor extends javax.swing.JFrame {

    private JTabbedPane tabbedPane;
    private JFileChooser fileChooser;

    public editor() {
        initComponents(); // el de NetBeans

        setTitle("AutómataLab - Editor con Pestañas");
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Pestañas
        tabbedPane = new JTabbedPane();
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        nuevaPestana("Nuevo");

        // Explorador de archivos
        fileChooser = new JFileChooser();

        // Crear menús
        crearMenu();
    }

    private void nuevaPestana(String titulo) {
        JTextArea textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        tabbedPane.addTab(titulo, scroll);
        tabbedPane.setSelectedComponent(scroll);
    }

    private JTextArea getTextAreaActual() {
        JScrollPane scroll = (JScrollPane) tabbedPane.getSelectedComponent();
        return (JTextArea) scroll.getViewport().getView();
    }

    private void nuevoArchivo() { nuevaPestana("Nuevo"); }

    private void abrirArchivo() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                JTextArea textArea = new JTextArea();
                textArea.read(br, null);
                JScrollPane scroll = new JScrollPane(textArea);
                tabbedPane.addTab(archivo.getName(), scroll);
                tabbedPane.setSelectedComponent(scroll);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        JTextArea textArea = getTextAreaActual();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                textArea.write(bw);
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), archivo.getName());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ejecutarCodigo() {
        JTextArea textArea = getTextAreaActual();
        String codigo = textArea.getText();
        JOptionPane.showMessageDialog(this, "Ejecutando análisis del código...", "Ejecutar", JOptionPane.INFORMATION_MESSAGE);
        // Aquí se conecta con el analizador léxico y sintáctico
    }

    private void mostrarReporte(String tipo) {
        JOptionPane.showMessageDialog(this, "Mostrando Reporte de " + tipo, "Reporte", JOptionPane.INFORMATION_MESSAGE);
        // Aquí se abre el reporte generado
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initComponent() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }

    /** Aquí armamos los menús, para no tocar el initComponents */
    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem nuevo = new JMenuItem("Nuevo");
        JMenuItem abrir = new JMenuItem("Abrir");
        JMenuItem guardar = new JMenuItem("Guardar");

        nuevo.addActionListener(e -> nuevoArchivo());
        abrir.addActionListener(e -> abrirArchivo());
        guardar.addActionListener(e -> guardarArchivo());

        menuArchivo.add(nuevo);
        menuArchivo.add(abrir);
        menuArchivo.add(guardar);

        JMenu menuHerramientas = new JMenu("Herramientas");
        JMenuItem ejecutar = new JMenuItem("Ejecutar");
        ejecutar.addActionListener(e -> ejecutarCodigo());
        menuHerramientas.add(ejecutar);

        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem tokens = new JMenuItem("Reporte de Tokens");
        JMenuItem errores = new JMenuItem("Reporte de Errores");

        tokens.addActionListener(e -> mostrarReporte("Tokens"));
        errores.addActionListener(e -> mostrarReporte("Errores"));

        menuReportes.add(tokens);
        menuReportes.add(errores);

        menuBar.add(menuArchivo);
        menuBar.add(menuHerramientas);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new editor().setVisible(true));
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

