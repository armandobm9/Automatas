package expresiones;

import java.util.Scanner;
import javax.swing.JOptionPane;
public class Principal {

	public static void main(String[] args){
		Scanner entrada = new Scanner(System.in);
		System.out.println("Introduce una expresión algebraica (infija): ");
		String infija= entrada.next();
		String postfija= Convertidor.convertirApostfija(infija);
		String prefija= Convertidor.convertirAprefija(infija);
		System.out.println();
		System.out.println("La expresión Postfija es: "+postfija);
		System.out.println("La expresión Prefija es: "+prefija);
		
	}
}