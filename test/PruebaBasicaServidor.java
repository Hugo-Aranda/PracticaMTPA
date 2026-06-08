import servidor.GestorClientes;
import servidor.GestorSalones;
import modelo.Usuario;

public class PruebaBasicaServidor {
    public static void main(String[] args) {
        GestorClientes clientes = new GestorClientes();
        GestorSalones salones = new GestorSalones();
        clientes.registrarUsuario("TEST", "9999");
        if (clientes.login("TEST", "9999")) System.out.println("Login correcto");
        Usuario usuario = clientes.buscarUsuario("TEST");
        if (salones.entrarSalon("IA", usuario)) System.out.println("Entrada en salon correcta");
        if (salones.usuarioEstaEnSalon("IA", usuario)) System.out.println("Usuario dentro del salon");
    }
}
