package Busqueda_archivos_y_gestion_contactos;


import java.util.ArrayList;
import java.util.List;
public class Contacto {
    private String nombre;
    private String correoElectronico;
    private String numeroTelefono;

    public Contacto(String nombre, String correoElectronico, String numeroTelefono) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
    }

    // Getters y setters para los campos

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }
}