package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

	private static final int PUERTO = 1234;

	public static void main(String[] args){

		GestorClientes gestorClientes = new GestorClientes();
		GestorSalones gestorSalones = new GestorSalones();

		try {
			ServerSocket servidor = new ServerSocket(PUERTO);


			System.out.println("Servidor iniciado en el puerto "+ PUERTO);

			while(true){
				Socket socketCliente = servidor.accept();

				System.out.println("Nuevo cliente conectado");

			}


		}catch(IOException e){

			System.out.println("Error en el servidor");

		}






	}





}
