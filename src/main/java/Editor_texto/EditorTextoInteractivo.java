package Editor_texto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class EditorTextoInteractivo extends JFrame {
    private JTextArea textArea;
    private JButton saveButton;
    private JButton openButton;
    private JComboBox<String> fileList;

    public EditorTextoInteractivo() {
        this.setLayout(new BorderLayout());

        textArea = new JTextArea();
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);

        saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> guardarArchivo());
        this.add(saveButton, BorderLayout.SOUTH);

        openButton = new JButton("Abrir");
        openButton.addActionListener(e -> abrirArchivo());
        this.add(openButton, BorderLayout.NORTH);

        fileList = new JComboBox<>();
        fileList.addActionListener(e -> cargarArchivo((String) fileList.getSelectedItem()));
        this.add(fileList, BorderLayout.EAST);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void guardarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void abrirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            cargarArchivo(file.getPath());
        }
    }

    private void cargarArchivo(String filePath) {
        if (filePath != null) {
            try {
                textArea.setText(new String(Files.readAllBytes(Paths.get(filePath))));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new EditorTextoInteractivo();
    }
}