package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import modelo.Salon;

public class HiloCliente extends Thread {

    private Socket socket;
    private GestorClientes gestorClientes;
    private GestorSalones gestorSalones;
    private BufferedReader entrada;
    private PrintWriter salida;

    public HiloCliente(Socket socket, GestorClientes gestorClientes, GestorSalones gestorSalones) {
        this.socket = socket;
        this.gestorClientes = gestorClientes;
        this.gestorSalones = gestorSalones;
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
