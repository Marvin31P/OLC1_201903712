package proyecto1;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class editor extends JFrame {

    private JTabbedPane tabbedPane;
    private JFileChooser fileChooser;

    
    private JSplitPane mainSplitPane;
    private JSplitPane centralSplitPane;
    private JPanel salidaPanel;
    private JPanel reportePanel;
    private JTextArea salidaArea;
    private JTextArea reporteArea;

    public editor() {
        
        initComponentsCustom(); 
        
        setTitle("AutómataLab - Editor con Pestañas");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        fileChooser = new JFileChooser();

     
        crearMenu();

      
        nuevaPestana("Nuevo");
    }

   
    private void initComponentsCustom() {
        
        getContentPane().setLayout(new BorderLayout());

        
        salidaPanel = new JPanel(new BorderLayout());
        salidaPanel.setBorder(BorderFactory.createTitledBorder("Salida"));
        salidaArea = new JTextArea();
        salidaPanel.add(new JScrollPane(salidaArea), BorderLayout.CENTER);

        
        centralSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        
        JPanel entradaPanel = new JPanel(new BorderLayout());
        entradaPanel.setBorder(BorderFactory.createTitledBorder("Entrada"));
        tabbedPane = new JTabbedPane();
        entradaPanel.add(tabbedPane, BorderLayout.CENTER);

        
        reportePanel = new JPanel(new BorderLayout());
        reportePanel.setBorder(BorderFactory.createTitledBorder("Reporte"));
        reporteArea = new JTextArea();
        reporteArea.setEditable(false);
        reportePanel.add(new JScrollPane(reporteArea), BorderLayout.CENTER);

        
        centralSplitPane.setLeftComponent(entradaPanel);
        centralSplitPane.setRightComponent(reportePanel);
        
        centralSplitPane.setDividerLocation(0.8);

        
        mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setTopComponent(centralSplitPane);
        mainSplitPane.setBottomComponent(salidaPanel);
        
        mainSplitPane.setDividerLocation(0.7);

       
        getContentPane().add(mainSplitPane, BorderLayout.CENTER);
        
        
        pack();
    }
    
  
    private void nuevaPestana(String titulo) {
        JTextArea textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        tabbedPane.addTab(titulo, scroll);
        tabbedPane.setSelectedComponent(scroll);
    }

    private JTextArea getTextAreaActual() {
        try {
            JScrollPane scroll = (JScrollPane) tabbedPane.getSelectedComponent();
            return (JTextArea) scroll.getViewport().getView();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No hay pestañas abiertas.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
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
                JOptionPane.showMessageDialog(this,
                        "Error al abrir archivo", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this,
                        "Error al guardar archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ejecutarCodigo() {
        JTextArea textArea = getTextAreaActual();
        if (textArea == null) return;
        String codigo = textArea.getText();
        
        
        salidaArea.setText("Analizando el código...\n");
        salidaArea.append("¡Análisis completado!\n");
    }

    private void mostrarReporte(String tipo) {
        
        reporteArea.setText("Generando reporte de " + tipo + "...\n");
        reporteArea.append("¡Reporte de " + tipo + " generado exitosamente!");
    }

    
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(editor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new editor().setVisible(true));
    }
}