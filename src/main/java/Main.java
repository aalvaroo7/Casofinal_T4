import contactos.Contacto;
import contactos.Contacto;
import contactos.Gestorcontactos;

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
    }
}