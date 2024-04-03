import Editor_texto.EditorTextoInteractivo;
import Busqueda_archivos_y_gestion_contactos.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.border.EmptyBorder;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400); // Aumenta el tamaño del marco

        AgendaContactos agenda = new AgendaContactos();

        JButton registrarContactoButton = new JButton("Registrar Contacto");
        registrarContactoButton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Reduce el grosor del borde
        registrarContactoButton.setFont(new Font("Arial", Font.BOLD, 14)); // Aumenta el tamaño del texto
        registrarContactoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón
        registrarContactoButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del contacto");
            String correo = JOptionPane.showInputDialog("Ingrese el correo del contacto");
            String telefono = JOptionPane.showInputDialog("Ingrese el teléfono del contacto");
            Contacto contacto = new Contacto(nombre, correo, telefono);
            agenda.agregarContacto(contacto);
            JOptionPane.showMessageDialog(null, "Contacto registrado exitosamente");
        });

        JButton verContactosButton = new JButton("Ver Contactos Registrados");
        verContactosButton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Reduce el grosor del borde
        verContactosButton.setFont(new Font("Arial", Font.BOLD, 14)); // Aumenta el tamaño del texto
        verContactosButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón
        verContactosButton.addActionListener(e -> {
            StringBuilder contactos = new StringBuilder();
            for (Contacto contacto : agenda.getContactos()) {
                contactos.append("Nombre: ").append(contacto.getNombre()).append(", ")
                        .append("Correo: ").append(contacto.getCorreoElectronico()).append(", ")
                        .append("Teléfono: ").append(contacto.getNumeroTelefono()).append("\n");
            }
            JOptionPane.showMessageDialog(null, contactos.toString());
        });

        JButton abrirEditorTextoButton = new JButton("Abrir Editor de Texto");
        abrirEditorTextoButton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Reduce el grosor del borde
        abrirEditorTextoButton.setFont(new Font("Arial", Font.BOLD, 14)); // Aumenta el tamaño del texto
        abrirEditorTextoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón
        abrirEditorTextoButton.addActionListener(e -> {
            new EditorTextoInteractivo();
        });

        JButton buscarArchivoButton = new JButton("Buscar en Archivo");
        buscarArchivoButton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Reduce el grosor del borde
        buscarArchivoButton.setFont(new Font("Arial", Font.BOLD, 14)); // Aumenta el tamaño del texto
        buscarArchivoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón
        buscarArchivoButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String palabraBuscada = JOptionPane.showInputDialog("Ingrese la palabra a buscar");
                try {
                    String texto = new String(Files.readAllBytes(Paths.get(fileChooser.getSelectedFile().getAbsolutePath())));
                    int ocurrencias = new BuscadorPalabras().buscar(texto, palabraBuscada);
                    JOptionPane.showMessageDialog(null, "La palabra '" + palabraBuscada + "' aparece " + ocurrencias + " veces en el archivo.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        ImageIcon logoIcon = new ImageIcon(new ImageIcon("C:/Captura de pantalla 2024-04-03 182800.png").getImage().getScaledInstance(500, 400, Image.SCALE_DEFAULT));
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(logoIcon.getImage(), 0, 0, this);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(registrarContactoButton);
        panel.add(verContactosButton);
        panel.add(abrirEditorTextoButton);
        panel.add(buscarArchivoButton);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}