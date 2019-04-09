package arbolRecorridos;

public class Nodo{	
	int dato;
	String nombre;
	Nodo hijoIzq, hijoDer;
	
	public Nodo(int d, String nom){
		this.dato=d;
		this.nombre=nom;
		this.hijoIzq=null;
		this.hijoDer=null;
	}

	public String Nombrar(){
		return nombre + " el dato es: "+ dato;	
	}
}
