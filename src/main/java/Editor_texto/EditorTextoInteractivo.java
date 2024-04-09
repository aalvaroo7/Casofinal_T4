package Editor_texto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EditorTextoInteractivo extends JFrame {
    private JTextArea textArea;
    private JButton saveButton;
    private JScrollBar scrollBar;

    public EditorTextoInteractivo() {
        this.setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea); // Envuelve textArea en un JScrollPane
        this.add(scrollPane, BorderLayout.CENTER); // Agrega el JScrollPane en lugar de textArea

        saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        this.add(saveButton, BorderLayout.SOUTH);

        // Agrega la barra de desplazamiento
        scrollBar = new JScrollBar(JScrollBar.VERTICAL);
        scrollBar.setModel(scrollPane.getVerticalScrollBar().getModel());
        this.add(scrollBar, BorderLayout.EAST);

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

    public static void main(String[] args) {
        new EditorTextoInteractivo();
    }
}