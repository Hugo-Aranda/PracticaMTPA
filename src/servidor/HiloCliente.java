package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import modelo.Mensaje;
import modelo.Salon;
import modelo.Usuario;
import persistencia.GestorPersistencia;

public class HiloCliente extends Thread {

    private Socket socket;
    private GestorClientes gestorClientes;
    private GestorSalones gestorSalones;
    private BufferedReader entrada;
    private PrintWriter salida;
    private Usuario usuarioActual;

    public HiloCliente(Socket socket, GestorClientes gestorClientes, GestorSalones gestorSalones) {
        this.socket = socket;
        this.gestorClientes = gestorClientes;
        this.gestorSalones = gestorSalones;
        this.usuarioActual = null;
    }

    @Override
    public void run() {

        try {

            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            salida.println("OK;CONNECT;Cliente conectado al servidor");

            String linea;

            while ((linea = entrada.readLine()) != null) {

                System.out.println("Cliente: " + linea);

                String[] partes = linea.split(";");

                if (partes[0].equals("REGISTER")) {

                    if (partes.length != 2) {
                        salida.println("ERROR;REGISTER;INVALID_FORMAT");
                    } else {
                        String nombre = partes[1];
                        String clave = String.valueOf(gestorClientes.getUsuarios().size() + 1001);

                        if (gestorClientes.registrarUsuario(nombre, clave)) {
                            usuarioActual = gestorClientes.buscarUsuario(nombre);
                            GestorPersistencia.guardarUsuario(usuarioActual);
                            salida.println("OK;REGISTER;" + nombre + ";" + clave);
                        } else {
                            salida.println("ERROR;REGISTER;USER_ALREADY_EXISTS");
                        }
                    }

                } else if (partes[0].equals("LOGIN")) {

                    if (partes.length != 3) {
                        salida.println("ERROR;LOGIN;INVALID_FORMAT");
                    } else {
                        String nombre = partes[1];
                        String clave = partes[2];

                        if (gestorClientes.login(nombre, clave)) {
                            usuarioActual = gestorClientes.buscarUsuario(nombre);
                            salida.println("OK;LOGIN;" + nombre);
                        } else {
                            salida.println("ERROR;LOGIN;INVALID_LOGIN");
                        }
                    }

                } else if (partes[0].equals("GET_ROOMS")) {

                    String respuesta = "ROOM_LIST";

                    for (Salon salon : gestorSalones.getSalones()) {
                        respuesta = respuesta + ";" + salon.getNombre();
                    }

                    salida.println(respuesta);

                } else if (partes[0].equals("JOIN_ROOM")) {

                    if (partes.length != 2) {
                        salida.println("ERROR;JOIN_ROOM;INVALID_FORMAT");
                    } else if (usuarioActual == null) {
                        salida.println("ERROR;JOIN_ROOM;INVALID_LOGIN");
                    } else {
                        String nombreSalon = partes[1];

                        if (gestorSalones.entrarSalon(nombreSalon, usuarioActual)) {
                            salida.println("OK;JOIN_ROOM;" + nombreSalon);
                        } else {
                            salida.println("ERROR;JOIN_ROOM;ROOM_NOT_FOUND");
                        }
                    }

                } else if (partes[0].equals("LEAVE_ROOM")) {

                    if (partes.length != 2) {
                        salida.println("ERROR;LEAVE_ROOM;INVALID_FORMAT");
                    } else if (usuarioActual == null) {
                        salida.println("ERROR;LEAVE_ROOM;INVALID_LOGIN");
                    } else {
                        String nombreSalon = partes[1];

                        if (gestorSalones.salirSalon(nombreSalon, usuarioActual)) {
                            salida.println("OK;LEAVE_ROOM;" + nombreSalon);
                        } else {
                            salida.println("ERROR;LEAVE_ROOM;NOT_IN_ROOM");
                        }
                    }

                } else if (partes[0].equals("SEND_ROOM")) {

                    if (partes.length != 3) {
                        salida.println("ERROR;SEND_ROOM;INVALID_FORMAT");
                    } else if (usuarioActual == null) {
                        salida.println("ERROR;SEND_ROOM;INVALID_LOGIN");
                    } else {
                        String nombreSalon = partes[1];
                        String contenido = partes[2];

                        if (contenido.length() > 190) {
                            salida.println("ERROR;SEND_ROOM;MESSAGE_TOO_LONG");
                        } else if (!gestorSalones.usuarioEstaEnSalon(nombreSalon, usuarioActual)) {
                            salida.println("ERROR;SEND_ROOM;NOT_IN_ROOM");
                        } else {
                            Mensaje mensaje = new Mensaje(usuarioActual, nombreSalon, contenido);
                            gestorSalones.guardarMensaje(nombreSalon, mensaje);
                            GestorPersistencia.guardarMensaje(mensaje);
                            salida.println("ROOM_MSG;" + nombreSalon + ";" + usuarioActual.getNombre() + ";" + mensaje.getFechaHora() + ";" + contenido);
                        }
                    }

                } else if (partes[0].equals("PRIVATE_MSG")) {

                    if (partes.length != 3) {
                        salida.println("ERROR;PRIVATE_MSG;INVALID_FORMAT");
                    } else if (usuarioActual == null) {
                        salida.println("ERROR;PRIVATE_MSG;INVALID_LOGIN");
                    } else {
                        String usuarioDestino = partes[1];

                        if (!gestorClientes.estaConectado(usuarioDestino)) {
                            salida.println("ERROR;PRIVATE_MSG;USER_NOT_CONNECTED");
                        } else {
                            salida.println("OK;PRIVATE_MSG;" + usuarioDestino);
                        }
                    }

                } else {

                    salida.println("ERROR;UNKNOWN;INVALID_FORMAT");

                }

            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
