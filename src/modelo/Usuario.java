package modelo;

public class Usuario {

	private String nombre;
	private String clave;
	private boolean conectado;


	public Usuario(String nombre, String clave){
		this.nombre = nombre;
		this.clave = clave;
		this.conectado = false;

	}

	public String getNombre(){
		return nombre;
	}

	public String getClave() {
        return clave;
 	}

    	public boolean isConectado() {
        return conectado;
    	}
	
	public void setConectado(boolean conectado) {
        this.conectado = conectado;
    	}	

}

