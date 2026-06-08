package persistencia;

import java.io.FileWriter;
import java.io.IOException;
import modelo.Mensaje;
import modelo.Usuario;

public class GestorPersistencia {

    private static final String FICHERO_USUARIOS = "data/usuarios.txt";
    private static final String FICHERO_MENSAJES = "data/mensajes.txt";

    public static void guardarUsuario(Usuario usuario) {
        try {
            FileWriter fw = new FileWriter(FICHERO_USUARIOS, true);
            fw.write(usuario.getNombre() + ";" + usuario.getClave() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error guardando usuario");
        }
    }

    public static void guardarMensaje(Mensaje mensaje) {
        try {
            FileWriter fw = new FileWriter(FICHERO_MENSAJES, true);
            fw.write(
                mensaje.getSalon() + ";" +
                mensaje.getUsuario().getNombre() + ";" +
                mensaje.getFechaHora() + ";" +
                mensaje.getContenido() + "\n"
            );
            fw.close();
        } catch (IOException e) {
            System.out.println("Error guardando mensaje");
        }
    }
}
