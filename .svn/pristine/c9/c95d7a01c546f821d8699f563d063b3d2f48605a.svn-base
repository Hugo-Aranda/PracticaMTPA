package servidor;

import java.util.ArrayList;
import modelo.Usuario;

public class GestorClientes {

    private ArrayList<Usuario> usuarios;

    public GestorClientes() {
        usuarios = new ArrayList<>();
    }

    public boolean registrarUsuario(String nombre, String clave) {
        if (buscarUsuario(nombre) != null) {
            return false;
        }

        Usuario usuario = new Usuario(nombre, clave);
        usuarios.add(usuario);
        return true;
    }

    public Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean login(String nombre, String clave) {
        Usuario usuario = buscarUsuario(nombre);

        if (usuario == null) {
            return false;
        }

        if (!usuario.getClave().equals(clave)) {
            return false;
        }

        usuario.setConectado(true);
        return true;
    }

    public void logout(String nombre) {
        Usuario usuario = buscarUsuario(nombre);

        if (usuario != null) {
            usuario.setConectado(false);
        }
    }

    public boolean estaConectado(String nombre) {
        Usuario usuario = buscarUsuario(nombre);
        return usuario != null && usuario.isConectado();
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
