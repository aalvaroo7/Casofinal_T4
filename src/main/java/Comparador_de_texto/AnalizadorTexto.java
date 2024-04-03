package Comparador_de_texto;
import java.util.*;

public class AnalizadorTexto {

    public int contarPalabras(String texto) {
        if (texto == null || texto.isEmpty()) {
            return 0;
        }

        String[] palabras = texto.split("\\s+");
        return palabras.length;
    }

    public Map<String, Integer> estadisticasUso(String texto) {
        Map<String, Integer> estadisticas = new HashMap<>();

        if (texto != null && !texto.isEmpty()) {
            String[] palabras = texto.split("\\s+");

            for (String palabra : palabras) {
                palabra = palabra.toLowerCase();
                estadisticas.put(palabra, estadisticas.getOrDefault(palabra, 0) + 1);
            }
        }

        return estadisticas;
    }
}