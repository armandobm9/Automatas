package expresiones;

public class Convertidor {

	public static String convertirApostfija(String infijo){
		Pila p1 = InfijoAPosfijo(infijo);
		String text = "";
		while (p1.i > 0)
			text = p1.pop() + text;
		return text;
	}
   public static Pila InfijoAPosfijo(String infijo) {
		infijo +=')'; // Agregamos al final del infijo un &#8216;)&#8217
		int tama�o = infijo.length();
		Pila PilaDefinitiva = new Pila(tama�o);
		Pila PilaTemp = new Pila(tama�o);
		PilaTemp.push('('); // Agregamos a la pila temporal un &#8216;(&#8216;<br />
		for (int i = 0; i < tama�o; i++) {
			char caracter = infijo.charAt(i);
			switch (caracter) {
			case '(':
				PilaTemp.push(caracter);
				break;
			case '+':case '-':case '^':case '*':case '/':
				while (Jerarquia(caracter) <= Jerarquia(PilaTemp.nextPop()))
					PilaDefinitiva.push(PilaTemp.pop());
				PilaTemp.push(caracter);
				break;
			case ')':
				while (PilaTemp.nextPop() != '(')
					PilaDefinitiva.push(PilaTemp.pop());
				PilaTemp.pop();
				break;
			default:
				PilaDefinitiva.push(caracter);
			}
		}
		return PilaDefinitiva;
	}
   
   public static String convertirAprefija(String infijo){
       Pila p1 = Infijo2Prefijo(infijo);
       String text = "";
       while (p1.i > 0)
           text += p1.pop();
       return text;

   }

   public static Pila Infijo2Prefijo(String infijo) {
       infijo = '(' + infijo ; // Agregamos al final del infijo un ')'
       int tama�o = infijo.length();
       Pila PilaDefinitiva = new Pila(tama�o);
       Pila PilaTemp = new Pila(tama�o);
       PilaTemp.push(')'); // Agregamos a la pila temporal un '('
       for (int i = tama�o-1; i > -1; i--) {
           char caracter = infijo.charAt(i);
           switch (caracter) {
           case ')':
               PilaTemp.push(caracter);
               break;
           case '+':case '-':case '^':case '*':case '/':
               while (Jerarquia(caracter) > Jerarquia(PilaTemp.nextPop()))
                   PilaDefinitiva.push(PilaTemp.pop());
               PilaTemp.push(caracter);
               break;
           case '(':
               while (PilaTemp.nextPop() != ')')
                   PilaDefinitiva.push(PilaTemp.pop());
               PilaTemp.pop();
               break;
           default:
               PilaDefinitiva.push(caracter);
           }
       }
       return PilaDefinitiva;
   }
   
   public static int Jerarquia(char elemento) {
		int res = 0;
		switch (elemento) {
		case ')':
			res = 5; break;
		case '^':
			res = 4; break;
		case '*': case '/':
			res = 3; break;
		case '+': case '-':
			res = 2; break;
		case '(':
			res = 1; break;
		}
		return res;
	}
}