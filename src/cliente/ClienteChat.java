package cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salidaServidor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            new Thread(new Runnable() {
                public void run() {
                    try {
                        String linea = entradaServidor.readLine();
                        while (linea != null) {
                            System.out.println(linea);
                            linea = entradaServidor.readLine();
                        }
                    } catch (Exception e) {
                        System.out.println("Conexion cerrada");
                    }
                }
            }).start();

            String linea = teclado.readLine();
            while (linea != null) {
                salidaServidor.println(linea);
                linea = teclado.readLine();
            }
            socket.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar con el servidor");
        }
    }
}
