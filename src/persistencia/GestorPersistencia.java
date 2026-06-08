package persistencia;

import java.io.*;
import java.util.ArrayList;
import modelo.Mensaje;
import modelo.Usuario;

public class GestorPersistencia {
    private static final String CARPETA = "data";
    private static final String FICHERO_USUARIOS = "data/usuarios.txt";
    private static final String FICHERO_MENSAJES = "data/mensajes.txt";
    private static final String FICHERO_LOG = "data/servidor.log";

    public static void prepararFicheros() {
        try {
            File carpeta = new File(CARPETA);
            if (!carpeta.exists()) carpeta.mkdirs();
            new File(FICHERO_USUARIOS).createNewFile();
            new File(FICHERO_MENSAJES).createNewFile();
            new File(FICHERO_LOG).createNewFile();
        } catch (IOException e) {
            System.out.println("Error preparando ficheros");
        }
    }

    public static void guardarUsuario(Usuario usuario) {
        try {
            FileWriter fw = new FileWriter(FICHERO_USUARIOS, true);
            fw.write(usuario.getNombre() + ";" + usuario.getClave() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error guardando usuario");
        }
    }

    public static ArrayList<Usuario> cargarUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            prepararFicheros();
            BufferedReader br = new BufferedReader(new FileReader(FICHERO_USUARIOS));
            String linea = br.readLine();
            while (linea != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) usuarios.add(new Usuario(partes[0], partes[1]));
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error cargando usuarios");
        }
        return usuarios;
    }

    public static void guardarMensaje(Mensaje mensaje) {
        try {
            FileWriter fw = new FileWriter(FICHERO_MENSAJES, true);
            fw.write(mensaje.getSalon() + ";" + mensaje.getUsuario().getNombre() + ";" + mensaje.getFechaHora() + ";" + mensaje.getContenido() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error guardando mensaje");
        }
    }

    public static ArrayList<String> cargarMensajes(String salon, String fecha) {
        ArrayList<String> mensajes = new ArrayList<>();
        try {
            prepararFicheros();
            BufferedReader br = new BufferedReader(new FileReader(FICHERO_MENSAJES));
            String linea = br.readLine();
            while (linea != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 4 && partes[0].equals(salon) && partes[2].startsWith(fecha)) mensajes.add(linea);
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error cargando mensajes");
        }
        return mensajes;
    }

    public static void log(String texto) {
        try {
            FileWriter fw = new FileWriter(FICHERO_LOG, true);
            fw.write(java.time.LocalDateTime.now() + " - " + texto + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error escribiendo log");
        }
    }
}
