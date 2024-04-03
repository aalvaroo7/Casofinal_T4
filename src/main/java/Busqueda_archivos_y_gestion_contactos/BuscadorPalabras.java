package Busqueda_archivos_y_gestion_contactos;
public class BuscadorPalabras {

    public int buscar(String texto, String palabraBuscada) {
        int contador = 0;

        if (texto != null && !texto.isEmpty() && palabraBuscada != null && !palabraBuscada.isEmpty()) {
            String[] palabras = texto.split("\\s+");

            for (String palabra : palabras) {
                if (palabra.equalsIgnoreCase(palabraBuscada)) {
                    contador++;
                }
            }
        }

        return contador;
    }
}