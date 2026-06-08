package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

                salida.println("OK;RECEIVED;Mensaje recibido");

            }

	    socket.close();

        } catch (IOException e) {

            System.out.println("Error con cliente");

        }

    }

}
