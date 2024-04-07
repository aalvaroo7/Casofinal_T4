import Editor_texto.EditorTextoInteractivo;
import Busqueda_archivos_y_gestion_contactos.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.border.EmptyBorder;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400); // Aumenta el tamaño del marco
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Etiquetas para mostrar la posición del ratón
        JLabel mouseXLabel = new JLabel("X: ");
        JLabel mouseYLabel = new JLabel("Y: ");

        // Agregar un MouseMotionListener para actualizar las etiquetas con la posición del ratón
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mouseXLabel.setText("X: " + e.getX());
                mouseYLabel.setText("Y: " + e.getY());
            }
        });

        AgendaContactos agenda = new AgendaContactos();

        Color backgroundColor = new Color(238, 238, 238); // Color de fondo del panel

        JButton registrarContactoButton = new JButton("Registrar Contacto");
        registrarContactoButton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Reduce el grosor del borde
        registrarContactoButton.setFont(new Font("Arial", Font.BOLD, 14)); // Aumenta el tamaño del texto
        registrarContactoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón
        registrarContactoButton.setBackground(backgroundColor); // Establece el color de fondo del botón
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
        verContactosButton.setBackground(backgroundColor); // Establece el color de fondo del botón
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
        abrirEditorTextoButton.setBackground(backgroundColor); // Establece el color de fondo del botón
        abrirEditorTextoButton.addActionListener(e -> {
            new EditorTextoInteractivo();
        });

        JButton buscarArchivoButton = new JButton("Buscar en Archivo");
        buscarArchivoButton.setBorder(new EmptyBorder(5, 5, 5, 5)); // Reduce el grosor del borde
        buscarArchivoButton.setFont(new Font("Arial", Font.BOLD, 14)); // Aumenta el tamaño del texto
        buscarArchivoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra el botón
        buscarArchivoButton.setBackground(backgroundColor); // Establece el color de fondo del botón
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

        ImageIcon logoIcon = new ImageIcon(new ImageIcon("C:/maxresdefault.jpg").getImage().getScaledInstance(500, 400, Image.SCALE_DEFAULT));
        JPanel panel = new JPanel(new GridBagLayout()); // Cambia a GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        panel.add(mouseXLabel, gbc);
        panel.add(mouseYLabel, gbc);
        panel.add(registrarContactoButton, gbc);
        panel.add(verContactosButton, gbc);
        panel.add(abrirEditorTextoButton, gbc);
        panel.add(buscarArchivoButton, gbc);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400); // Aumenta el tamaño del marco
            frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
            // Agregar un MouseMotionListener para actualizar las etiquetas con la posición del ratón
            frame.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(java.awt.event.MouseEvent e) {
                    mouseXLabel.setText("X: " + e.getX());
                    mouseYLabel.setText("Y: " + e.getY());
                }
            });
            // Crear los botones y agregarlos a un panel
            JPanel optionsPanel = new JPanel(new GridBagLayout());

            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weighty = 1;

            optionsPanel.add(mouseXLabel, gbc);
            optionsPanel.add(mouseYLabel, gbc);
            // Agrega los botones al panel de opciones aquí...

            // Crear el panel de la imagen
            JPanel imagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    try {
                        BufferedImage image = ImageIO.read(new File("C:/maxresdefault.jpg"));
                        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            // Crear el panel principal y agregar los subpaneles
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(imagePanel, BorderLayout.WEST);
            mainPanel.add(optionsPanel, BorderLayout.EAST);

            frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        }
    }
