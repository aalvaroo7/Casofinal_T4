package Editor_texto;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class GestorArchivos {

    public String[] listarArchivos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File dir = fileChooser.getSelectedFile();
            try (Stream<Path> paths = Files.walk(Paths.get(dir.getAbsolutePath()))) {
                return paths
                        .filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .toArray(String[]::new);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public String abrirArchivo(String rutaArchivo) {
        if (rutaArchivo != null) {
            try {
                return new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}