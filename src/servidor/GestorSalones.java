package servidor;

import java.util.ArrayList;
import modelo.Mensaje;
import modelo.Salon;
import modelo.Usuario;
import persistencia.GestorPersistencia;

public class GestorSalones {
    private ArrayList<Salon> salones;

    public GestorSalones() {
        salones = new ArrayList<>();
        salones.add(new Salon("IA"));
        salones.add(new Salon("DEPORTES"));
        salones.add(new Salon("THERIAN"));
        salones.add(new Salon("MANGA"));
        salones.add(new Salon("UEMC"));
    }

    public synchronized Salon buscarSalon(String nombre) {
        for (Salon salon : salones) {
            if (salon.getNombre().equals(nombre)) return salon;
        }
        return null;
    }

    public synchronized boolean entrarSalon(String nombreSalon, Usuario usuario) {
        Salon salon = buscarSalon(nombreSalon);
        if (salon == null) return false;
        if (usuarioEstaEnSalon(nombreSalon, usuario)) return true;
        salon.agregarUsuario(usuario);
        return true;
    }

    public synchronized boolean salirSalon(String nombreSalon, Usuario usuario) {
        Salon salon = buscarSalon(nombreSalon);
        if (salon == null) return false;
        if (!usuarioEstaEnSalon(nombreSalon, usuario)) return false;
        salon.eliminarUsuario(usuario);
        return true;
    }

    public synchronized boolean usuarioEstaEnSalon(String nombreSalon, Usuario usuario) {
        Salon salon = buscarSalon(nombreSalon);
        if (salon == null) return false;
        return salon.getUsuarios().contains(usuario);
    }

    public synchronized boolean guardarMensaje(String nombreSalon, Mensaje mensaje) {
        Salon salon = buscarSalon(nombreSalon);
        if (salon == null) return false;
        salon.agregarMensaje(mensaje);
        GestorPersistencia.guardarMensaje(mensaje);
        return true;
    }

    public synchronized ArrayList<Salon> getSalones() { return salones; }

    public synchronized int contarUsuariosSalon(String nombreSalon) {
        Salon salon = buscarSalon(nombreSalon);
        if (salon == null) return 0;
        return salon.getUsuarios().size();
    }

    public synchronized int contarMensajesSalon(String nombreSalon) {
        Salon salon = buscarSalon(nombreSalon);
        if (salon == null) return 0;
        return salon.getMensajes().size();
    }
}
