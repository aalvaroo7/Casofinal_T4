import Editor_texto.EditorTextoInteractivo;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Crear varias ventanas
        for (int i = 0; i < 3; i++) {
            final int windowNumber = i + 1;
            SwingUtilities.invokeLater(() -> {
                EditorTextoInteractivo editor = new EditorTextoInteractivo(); // Crear una nueva instancia de EditorTextoInteractivo
                JFrame frame = new JFrame("Editor de Texto " + windowNumber);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(editor);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        }
    }
}