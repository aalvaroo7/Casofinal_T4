import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static class Editor_texto extends JFrame {
        private JList<Contacto> contactList;
        private DefaultListModel<Contacto> contactListModel;
        private JTextField nameField;
        private JTextField emailField;
        private JTextField phoneField;
        private JButton addContactButton;
        private JButton compareButton;
        private JTextArea textArea;
        private JButton saveButton;
        private JList<String> fileList;
        private DefaultListModel<String> listModel;
        private JButton analyzeButton;
        private JButton searchButton;
        private JTextField searchField;

        public Editor_texto() {

            contactListModel = new DefaultListModel<>();
            contactList = new JList<>(contactListModel);

            nameField = new JTextField(20);
            emailField = new JTextField(20);
            phoneField = new JTextField(20);

            addContactButton = new JButton("Agregar contacto");
            addContactButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addContacto();
                }
            });

            JPanel contactPanel = new JPanel();
            contactPanel.add(new JLabel("Nombre:"));
            contactPanel.add(nameField);
            contactPanel.add(new JLabel("Email:"));
            contactPanel.add(emailField);
            contactPanel.add(new JLabel("Teléfono:"));
            contactPanel.add(phoneField);
            contactPanel.add(addContactButton);

            this.add(new JScrollPane(contactList), BorderLayout.WEST);
            this.add(contactPanel, BorderLayout.SOUTH);

            compareButton = new JButton("Comparar");
            compareButton.addActionListener(e -> compareFiles());

            textArea = new JTextArea();
            saveButton = new JButton("Guardar");
            listModel = new DefaultListModel<>();
            fileList = new JList<>(listModel);
            this.add(compareButton, BorderLayout.NORTH);
            saveButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(textArea.getText());
                        refreshFileList();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            analyzeButton = new JButton("Analizar");
            analyzeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    analyzeText();
                }
            });
            this.add(analyzeButton, BorderLayout.NORTH);

            fileList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        String selectedFile = fileList.getSelectedValue();
                        loadFile(selectedFile);
                    }
                }
            });

            this.setLayout(new BorderLayout());
            this.add(new JScrollPane(textArea), BorderLayout.CENTER);
            this.add(saveButton, BorderLayout.SOUTH);
            this.add(new JScrollPane(fileList), BorderLayout.EAST);
            this.setSize(700, 500);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);

            refreshFileList();
            searchField = new JTextField(20);
            searchButton = new JButton("Buscar");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchWord();
                }
            });

            JPanel searchPanel = new JPanel();
            searchPanel.add(searchField);
            searchPanel.add(searchButton);

            this.add(searchPanel, BorderLayout.NORTH);
        }

        private void analyzeText() {
            String text = textArea.getText();
            String[] words = text.split("\\s+");
            int wordCount = words.length;

            Map<String, Integer> wordFrequency = new HashMap<>();
            for (String word : words) {
                word = word.toLowerCase();
                wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
            }

            StringBuilder stats = new StringBuilder();
            stats.append("Número total de palabras: ").append(wordCount).append("\n");
            stats.append("Estadísticas de uso de palabras:\n");
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                stats.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }

            JOptionPane.showMessageDialog(this, stats.toString());
        }

        private void loadFile(String fileName) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                textArea.setText(reader.lines().collect(Collectors.joining("\n")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void refreshFileList() {
            try {
                listModel.clear();
                Files.list(Paths.get("."))
                        .map(Path::toString)
                        .filter(s -> s.endsWith(".txt"))
                        .forEach(listModel::addElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void compareFiles() {
            JFileChooser fileChooser1 = new JFileChooser();
            JFileChooser fileChooser2 = new JFileChooser();
            if (fileChooser1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION
                    && fileChooser2.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file1 = fileChooser1.getSelectedFile();
                File file2 = fileChooser2.getSelectedFile();
                try {
                    String content1 = readFile(file1);
                    String content2 = readFile(file2);
                    if (content1.equals(content2)) {
                        JOptionPane.showMessageDialog(this, "Los archivos son iguales.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Los archivos son diferentes.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String readFile(File file) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        }

        private void searchWord() {
            String text = textArea.getText();
            String[] words = text.split("\\s+");
            String searchWord = searchField.getText();

            long count = Arrays.stream(words)
                    .filter(word -> word.equalsIgnoreCase(searchWord))
                    .count();

            JOptionPane.showMessageDialog(this, "La palabra '" + searchWord + "' aparece " + count + " veces.");
        }

        private void addContacto() {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();

            Contacto contact = new Contacto(name, email, phone);
            contactListModel.addElement(contact);

            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
        }

        public static void main(String[] args) {
            new Editor_texto();
        }
    }
}