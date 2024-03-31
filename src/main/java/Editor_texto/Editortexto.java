package Editor_texto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Editortexto extends JFrame {
    private JTextArea textArea;
    private JButton saveButton;
    private JButton compareButton;
    private JButton countWordsButton;
    private JButton listFilesButton;
    private JButton searchWordButton;
    private JLabel mousePositionLabel; // Etiqueta para mostrar la posición del ratón


    public Editortexto() {
        mousePositionLabel = new JLabel();
        this.add(mousePositionLabel, BorderLayout.SOUTH);
        this.setLayout(new BorderLayout());

        textArea.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePositionLabel.setText("Posición del ratón: " + e.getX() + ", " + e.getY());
            }
        });

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

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

        listFilesButton = new JButton("Listar documentos");
        listFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String directory = JOptionPane.showInputDialog("Ingrese la ruta del directorio");
                listFiles(directory);
            }
        });
        this.add(listFilesButton, BorderLayout.NORTH);

        searchWordButton = new JButton("Buscar palabra");
        searchWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = JOptionPane.showInputDialog("Ingrese la ruta del archivo");
                String word = JOptionPane.showInputDialog("Ingrese la palabra a buscar");
                int wordCount = searchWordInFile(file, word);
                JOptionPane.showMessageDialog(null, "La palabra '" + word + "' aparece " + wordCount + " veces en el archivo");
            }
        });
        this.add(searchWordButton, BorderLayout.NORTH);

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

    public void listFiles(String directory) {
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> {
                        int response = JOptionPane.showConfirmDialog(null, "¿Desea abrir el archivo " + file.getFileName() + "?", "Seleccione una opción", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            openFile(file.toString());
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            StringBuilder content = new StringBuilder();

            while (line != null) {
                content.append(line).append("\n");
                line = reader.readLine();
            }

            textArea.setText(content.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int searchWordInFile(String file, String word) {
        int wordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while (line != null) {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (w.equals(word)) {
                        wordCount++;
                    }
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordCount;
    }
}