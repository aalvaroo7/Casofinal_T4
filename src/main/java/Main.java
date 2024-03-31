import contactos.Contacto;
import contactos.Gestorcontactos;
import Editor_texto.Editortexto;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Gestorcontactos gestorContactos = new Gestorcontactos();

        // Agregar un contacto
        Contacto contacto = new Contacto("Nombre", "Correo", "Telefono");
        gestorContactos.agregarContacto(contacto);

        // Eliminar un contacto
        gestorContactos.eliminarContacto(contacto);

        // Buscar un contacto por nombre
        Contacto contactoBuscado = gestorContactos.buscarContactoPorNombre("Nombre");
        if (contactoBuscado != null) {
            System.out.println("Contacto encontrado: " + contactoBuscado.getNombre());
        } else {
            System.out.println("Contacto no encontrado");
        }

        // Crear varias ventanas
        for (int i = 0; i < 3; i++) {
            final int windowNumber = i + 1;
            SwingUtilities.invokeLater(() -> {
                Editortexto editor = new Editortexto(); // Crear una nueva instancia de Editortexto
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