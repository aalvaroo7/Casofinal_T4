package Editor_texto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Editor_de_texto extends JFrame {
    private JTextArea textArea;
    private JButton saveButton;
    private JButton compareButton;
    private JButton countWordsButton;

    public Editor_de_texto() {
        this.setLayout(new BorderLayout());

        textArea = new JTextArea();
        this.add(new JScrollPane(textArea), BorderLayout.CENTER);

        saveButton = new JButton("Guardar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        this.add(saveButton, BorderLayout.SOUTH);

        compareButton = new JButton("Comparar archivos");
        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file1 = JOptionPane.showInputDialog("Ingrese la ruta del primer archivo");
                String file2 = JOptionPane.showInputDialog("Ingrese la ruta del segundo archivo");
                boolean areEqual = compareFiles(file1, file2);
                JOptionPane.showMessageDialog(null, "Los archivos son " + (areEqual ? "iguales" : "diferentes"));
            }
        });
        this.add(compareButton, BorderLayout.WEST);

        countWordsButton = new JButton("Contar palabras");
        countWordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = JOptionPane.showInputDialog("Ingrese la ruta del archivo");
                int wordCount = countWordsInFile(file);
                JOptionPane.showMessageDialog(null, "El archivo tiene " + wordCount + " palabras");
            }
        });
        this.add(countWordsButton, BorderLayout.EAST);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean compareFiles(String file1, String file2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2))) {

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null || line2 != null) {
                if (!line1.equals(line2)) {
                    return false;
                }

                line1 = reader1.readLine();
                line2 = reader2.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public int countWordsInFile(String file) {
        int wordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                String[] words = line.split("\\s+");
                wordCount += words.length;
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordCount;
    }
}