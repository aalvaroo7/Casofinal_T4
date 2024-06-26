import Editor_texto.EditorTextoInteractivo;
import Busqueda_archivos_y_gestion_contactos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List; // Correct import
import java.util.ArrayList; // Correct import

import static Validacion_email.Validador_email.EMAIL_PATTERN;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400); // Aumenta el tamaño del marco
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Etiquetas para mostrar la posición del ratón
        JLabel mouseXLabel = new JLabel("X: ");
        JLabel mouseYLabel = new JLabel("Y: ");

        // Crear los botones y agregarlos a un panel
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        optionsPanel.add(mouseXLabel, gbc);
        optionsPanel.add(mouseYLabel, gbc);

        // Botón para abrir la herramienta de dibujo
        JButton abrirHerramientaDibujoButton = new JButton("Abrir Herramienta de Dibujo");
        abrirHerramientaDibujoButton.addActionListener(e -> {
            JFrame drawingFrame = new JFrame("Herramienta de Dibujo") {
                List<Line2D.Double> lines = new ArrayList<>(); // Use Line2D.Double
                Point pointStart = null;

                {
                    addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            pointStart = e.getPoint();
                        }

                        public void mouseReleased(MouseEvent e) {
                            Point pointEnd = e.getPoint();
                            lines.add(new Line2D.Double(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y)); // Use Line2D.Double
                            pointStart = null;
                            repaint();
                        }
                    });

                    addMouseMotionListener(new MouseMotionAdapter() {
                        public void mouseDragged(MouseEvent e) {
                            if (pointStart != null) {
                                Point pointEnd = e.getPoint();
                                lines.add(new Line2D.Double(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y)); // Use Line2D.Double
                                pointStart = pointEnd;
                                repaint();
                            }
                        }
                    });
                }

                public void paint(Graphics g) {
                    super.paint(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(Color.RED);
                    for (Line2D.Double line : lines) {
                        g2d.draw(line);
                    }
                }
            };

            drawingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            drawingFrame.setSize(500, 500);
            drawingFrame.setVisible(true);
        });
        optionsPanel.add(abrirHerramientaDibujoButton, gbc);


        // Agregar un MouseMotionListener para actualizar las etiquetas con la posición del ratón
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mouseXLabel.setText("X: " + e.getX());
                mouseYLabel.setText("Y: " + e.getY());
            }
        });

        AgendaContactos agenda = new AgendaContactos();

        // Botón para registrar un contacto
        JButton registrarContactoButton = new JButton("Registrar Contacto");
        registrarContactoButton.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del contacto");
            String telefono = JOptionPane.showInputDialog("Ingrese el teléfono del contacto");
            String correo = JOptionPane.showInputDialog("Ingrese el correo electrónico del contacto");

            // Validar el correo electrónico antes de crear el contacto
            if (EMAIL_PATTERN.matcher(correo).matches()) {
                Contacto contacto = new Contacto(nombre, correo, telefono);
                agenda.agregarContacto(contacto);
                JOptionPane.showMessageDialog(null, "Contacto registrado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Correo electrónico inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        optionsPanel.add(registrarContactoButton, gbc);

        // Botón para ver contactos registrados
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
        optionsPanel.add(verContactosButton, gbc);

        // Botón para abrir el editor de texto
        JButton abrirEditorTextoButton = new JButton("Abrir Editor de Texto");
        abrirEditorTextoButton.addActionListener(e -> {
            new EditorTextoInteractivo();
        });
        optionsPanel.add(abrirEditorTextoButton, gbc);

        // Botón para buscar en archivos
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
        optionsPanel.add(buscarArchivoButton, gbc);

        // Crear el panel con fondo azul y texto "UAX"
        JPanel uaxPanel = new JPanel();
        uaxPanel.setBackground(Color.BLUE);
        JLabel uaxLabel = new JLabel("UAX");
        uaxLabel.setForeground(Color.WHITE);
        uaxLabel.setFont(new Font("Arial", Font.BOLD, 50));
        uaxPanel.add(uaxLabel);

        // Crear el panel principal y agregar los subpaneles
        JPanel mainPanel = new JPanel(new GridLayout(1, 2)); // Cambia a GridLayout
        mainPanel.add(uaxPanel);
        mainPanel.add(optionsPanel);

        // Establecer diferentes colores de fondo para cada panel
        uaxPanel.setBackground(Color.BLUE);
        optionsPanel.setBackground(Color.lightGray); // Más claro que el azul

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}