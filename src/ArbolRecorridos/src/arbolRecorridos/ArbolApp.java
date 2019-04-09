package arbolRecorridos;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class ArbolApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion=0, elemento;
		String nombre;
		Arbol arbolbin = new Arbol();
		Scanner entrada = new Scanner (System.in);
		
		do {
		System.out.print("Implementación de árbol binario. Elija una opción válida: \n"
				+ "1.- Agregar un nodo \n"
				+ "2.- Mostrar recorrido InOrden\n"
				+ "3.- Mostrar recorrido PreOrden\n"
				+ "4.- Mostrar recorrido PostOrden\n"
				+ "5.- Salir\n\n"
				+ "Opción: ");
		opcion=entrada.nextInt();
		
		while(opcion<1 || opcion>5) {
			System.out.print("Opción incorrecta. Elija una opción válida: ");
			opcion=entrada.nextInt();
		}
		
		switch(opcion) {
		case 1:
			System.out.print("Valor del nodo: ");
			elemento = entrada.nextInt();
			System.out.print("Nombre del nodo: ");
			nombre = entrada.next();
			arbolbin.insertarNodo(elemento, nombre);
			System.out.println("Nodo agregado...\n");
			break;
			
		case 2:
			if (!arbolbin.vacio()){
				arbolbin.inOrden(arbolbin.raiz);
			}
			else {
				System.out.println("Arbol vacío. Agrega un nodo");
			}
			break;	
			
		case 3:
			if (!arbolbin.vacio()){
				arbolbin.preOrden(arbolbin.raiz);
			}
			else {
				System.out.println("Arbol vacío. Agrega un nodo");
			}
			break;	
			
		case 4:
			if (!arbolbin.vacio()){
				arbolbin.postOrden(arbolbin.raiz);
			}
			else {
				System.out.println("Arbol vacío. Agrega un nodo");
			}
			break;	
			
		case 5:
			break;	
		}
		}while (opcion!=5);
	}

}
