import Editor_texto.EditorTextoInteractivo;
import Busqueda_archivos_y_gestion_contactos.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        AgendaContactos agenda = new AgendaContactos();

        JButton registrarContactoButton = new JButton("Registrar Contacto");
        registrarContactoButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del contacto");
            String correo = JOptionPane.showInputDialog("Ingrese el correo del contacto");
            String telefono = JOptionPane.showInputDialog("Ingrese el teléfono del contacto");
            Contacto contacto = new Contacto(nombre, correo, telefono);
            agenda.agregarContacto(contacto);
            JOptionPane.showMessageDialog(null, "Contacto registrado exitosamente");
        });

        JButton verContactosButton = new JButton("Ver Contactos Registrados");
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
        abrirEditorTextoButton.addActionListener(e -> {
            new EditorTextoInteractivo();
        });

        JButton buscarArchivoButton = new JButton("Buscar en Archivo");
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

        ImageIcon logoIcon = new ImageIcon(new ImageIcon("C:/Captura de pantalla 2024-04-03 182800.png").getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT));
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