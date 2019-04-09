package arbolRecorridos;

public class Arbol {
	Nodo raiz;
	
	public Arbol(){
		raiz=null;
	}
	
	//Insertar un nodo en el arbol
	public void insertarNodo(int d, String nom){
		Nodo nuevo = new Nodo(d, nom);
		if(raiz==null){
			raiz=nuevo;
		}
		else{
			Nodo aux=raiz;
			Nodo padre;
			while(true){
				padre=aux;
				if(d<aux.dato) {
					aux=aux.hijoIzq;
					if(aux==null){
						padre.hijoIzq=nuevo;
						return;
					}
				}
				else {
					aux=aux.hijoDer;
					if(aux==null){
						padre.hijoDer=nuevo;
						return;
					}
				}
			}
		}
	}
	
	public boolean vacio(){
		return raiz==null;
	}
	
	public void inOrden(Nodo r){
		if (r!=null){
			inOrden(r.hijoIzq);
			System.out.print(r.dato + " ");
			inOrden(r.hijoDer);
		}
		System.out.println();
	}
	
	public void preOrden(Nodo r){
		if (r!=null){
			System.out.print(r.dato + " ");
			preOrden(r.hijoIzq);
			preOrden(r.hijoDer);
		}
		System.out.println();
	}
	
	public void postOrden(Nodo r){
		if (r!=null){
			postOrden(r.hijoIzq);
			postOrden(r.hijoDer);
			System.out.print(r.dato + " ");
		}
		System.out.println();
	}
}
