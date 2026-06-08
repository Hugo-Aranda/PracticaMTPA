package modelo;

import java.util.ArrayList;

public class Salon {
    private String nombre;
    private ArrayList<Usuario> usuarios;
    private ArrayList<Mensaje> mensajes;

    public Salon(String nombre) {
        this.nombre = nombre;
        usuarios = new ArrayList<>();
        mensajes = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public ArrayList<Usuario> getUsuarios() { return usuarios; }
    public ArrayList<Mensaje> getMensajes() { return mensajes; }
    public void agregarUsuario(Usuario usuario) 
    { 
        if (!usuarios.contains(usuario)) usuarios.add(usuario); 
    }
    public void eliminarUsuario(Usuario usuario) 
    {
        usuarios.remove(usuario);
    }
    public void agregarMensaje(Mensaje mensaje)
    {
        mensajes.add(mensaje); 
    }
}
