import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class  Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }

    public static class Editor_texto extends JFrame {
        private JButton compareButton;
        private JTextArea textArea;
        private JButton saveButton;
        private JList<String> fileList;
        private DefaultListModel<String> listModel;



        public Editor_texto() {

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

        public static void main(String[] args) {
            new Editor_texto();
        }
    }
}