package servidor;

import java.util.ArrayList;
import modelo.Usuario;
import persistencia.GestorPersistencia;

public class GestorClientes {
    private ArrayList<Usuario> usuarios;

    public GestorClientes() {
        usuarios = GestorPersistencia.cargarUsuarios();
    }

    public synchronized boolean registrarUsuario(String nombre, String clave) {
        if (buscarUsuario(nombre) != null) return false;
        Usuario usuario = new Usuario(nombre, clave);
        usuarios.add(usuario);
        GestorPersistencia.guardarUsuario(usuario);
        return true;
    }

    public synchronized Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) return usuario;
        }
        return null;
    }

    public synchronized boolean login(String nombre, String clave) {
        Usuario usuario = buscarUsuario(nombre);
        if (usuario == null) return false;
        if (!usuario.getClave().equals(clave)) return false;
        usuario.setConectado(true);
        usuario.actualizarHeartbeat();
        return true;
    }

    public synchronized void logout(String nombre) {
        Usuario usuario = buscarUsuario(nombre);
        if (usuario != null) usuario.setConectado(false);
    }

    public synchronized boolean estaConectado(String nombre) {
        Usuario usuario = buscarUsuario(nombre);
        return usuario != null && usuario.isConectado();
    }

    public synchronized String generarClave() {
        return String.valueOf(1001 + usuarios.size());
    }

    public synchronized ArrayList<Usuario> getUsuarios() { return usuarios; }

    public synchronized int contarConectados() {
        int contador = 0;
        for (Usuario usuario : usuarios) if (usuario.isConectado()) contador++;
        return contador;
    }
}

