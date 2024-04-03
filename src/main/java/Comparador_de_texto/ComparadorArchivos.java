package Comparador_de_texto;
import Editor_texto.GestorArchivos;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ComparadorArchivos {

    private GestorArchivos gestorArchivos;

    public ComparadorArchivos() {
        gestorArchivos = new GestorArchivos();
    }

    public boolean comparar(String rutaArchivo1, String rutaArchivo2) {
        String contenidoArchivo1 = gestorArchivos.abrirArchivo(rutaArchivo1);
        String contenidoArchivo2 = gestorArchivos.abrirArchivo(rutaArchivo2);

        if (contenidoArchivo1 != null && contenidoArchivo2 != null) {
            return contenidoArchivo1.equals(contenidoArchivo2);
        }

        return false;
    }
}