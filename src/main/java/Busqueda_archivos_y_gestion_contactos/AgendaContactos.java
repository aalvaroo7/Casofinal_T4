package Busqueda_archivos_y_gestion_contactos;
import java.util.ArrayList;
import java.util.List;

public class AgendaContactos {
    private List<Contacto> contactos;

    public AgendaContactos() {
        this.contactos = new ArrayList<>();
    }

    public void agregarContacto(Contacto contacto) {
        this.contactos.add(contacto);
    }

    public void eliminarContacto(Contacto contacto) {
        this.contactos.remove(contacto);
    }

    public Contacto buscarContactoPorNombre(String nombre) {
        for (Contacto contacto : this.contactos) {
            if (contacto.getNombre().equals(nombre)) {
                return contacto;
            }
        }
        return null;
    }

    // Método para obtener los nombres de todos los contactos
    public List<String> getNombresContactos() {
        List<String> nombres = new ArrayList<>();
        for (Contacto contacto : this.contactos) {
            nombres.add(contacto.getNombre());
        }
        return nombres;
    }

    // Método para obtener la lista de contactos
    public List<Contacto> getContactos() {
        return this.contactos;
    }
}