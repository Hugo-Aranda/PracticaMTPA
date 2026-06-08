package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import persistencia.GestorPersistencia;

public class ServidorChat {
    private static final int PUERTO = 1234;
    private static boolean aceptarClientes = true;

    public static void main(String[] args) {
        GestorPersistencia.prepararFicheros();
        GestorClientes gestorClientes = new GestorClientes();
        GestorSalones gestorSalones = new GestorSalones();

        new Thread(new Runnable() {
            public void run() {
                leerComandosServidor(gestorClientes, gestorSalones);
            }
        }).start();

        try {
            ServerSocket servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            GestorPersistencia.log("Servidor iniciado");

            while (true) {
                Socket socketCliente = servidor.accept();
                if (aceptarClientes) {
                    System.out.println("Nuevo cliente conectado");
                    HiloCliente hiloCliente = new HiloCliente(socketCliente, gestorClientes, gestorSalones);
                    hiloCliente.start();
                } else socketCliente.close();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor");
        }
    }

    private static void leerComandosServidor(GestorClientes gestorClientes, GestorSalones gestorSalones) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String comando = sc.nextLine();
            if (comando.equals("STOP_ACCEPTING_CLIENTS")) {
                aceptarClientes = false;
                System.out.println("El servidor deja de aceptar nuevos clientes");
            } else if (comando.equals("START_ACCEPTING_CLIENTS")) {
                aceptarClientes = true;
                System.out.println("El servidor vuelve a aceptar clientes");
            } else if (comando.equals("SHOW_CONNECTED_USERS")) {
                System.out.println("Usuarios conectados: " + gestorClientes.contarConectados());
            } else if (comando.equals("SHOW_ROOM_USERS")) {
                System.out.println("IA: " + gestorSalones.contarUsuariosSalon("IA"));
                System.out.println("DEPORTES: " + gestorSalones.contarUsuariosSalon("DEPORTES"));
                System.out.println("THERIAN: " + gestorSalones.contarUsuariosSalon("THERIAN"));
                System.out.println("MANGA: " + gestorSalones.contarUsuariosSalon("MANGA"));
                System.out.println("UEMC: " + gestorSalones.contarUsuariosSalon("UEMC"));
            } else if (comando.equals("SHOW_ROOM_STATS")) {
                System.out.println("IA: " + gestorSalones.contarMensajesSalon("IA"));
                System.out.println("DEPORTES: " + gestorSalones.contarMensajesSalon("DEPORTES"));
                System.out.println("THERIAN: " + gestorSalones.contarMensajesSalon("THERIAN"));
                System.out.println("MANGA: " + gestorSalones.contarMensajesSalon("MANGA"));
                System.out.println("UEMC: " + gestorSalones.contarMensajesSalon("UEMC"));
            } else {
                System.out.println("Comando de servidor no reconocido");
            }
        }
    }
}

