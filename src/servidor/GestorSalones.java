package servidor;

import java.util.ArrayList;
import modelo.Mensaje;
import modelo.Salon;
import modelo.Usuario;

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

    public Salon buscarSalon(String nombre) {
        for (Salon salon : salones) {
            if (salon.getNombre().equals(nombre)) {
                return salon;
            }
        }
        return null;
    }

    public boolean entrarSalon(String nombreSalon, Usuario usuario) {
        Salon salon = buscarSalon(nombreSalon);

        if (salon == null) {
            return false;
        }

	if (!usuarioEstaEnSalon(nombreSalon, usuario)) {
        	return false;
    	}

        salon.agregarUsuario(usuario);
        return true;
    }

    public boolean salirSalon(String nombreSalon, Usuario usuario) {
        Salon salon = buscarSalon(nombreSalon);

        if (salon == null) {
            	return false;
        }

	if (!usuarioEstaEnSalon(nombreSalon, usuario)) {
        	return false;
    	}

        salon.eliminarUsuario(usuario);
        return true;
    }

    public boolean usuarioEstaEnSalon(String nombreSalon, Usuario usuario) {
        
	Salon salon = buscarSalon(nombreSalon);

        if (salon == null) {
            return false;
        }	

        return salon.getUsuarios().contains(usuario);
    }

    public boolean guardarMensaje(String nombreSalon, Mensaje mensaje) {
        Salon salon = buscarSalon(nombreSalon);

        if (salon == null) {
            return false;
        }

        salon.agregarMensaje(mensaje);
        return true;
    }

    public ArrayList<Salon> getSalones() {
        return salones;
    }
}
