import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Editor_texto extends JFrame {
    private JTextArea textArea;
    private JButton saveButton;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;

    public Editor_texto() {
        textArea = new JTextArea();
        saveButton = new JButton("Guardar");
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);

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

    public static void main(String[] args) {
        new Editor_texto();
    }
}