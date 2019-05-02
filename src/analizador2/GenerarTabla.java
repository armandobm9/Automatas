package analizador2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import analizador2.TablaSimbolos;

public class GenerarTabla {
	public static HashMap<String, TablaSimbolos> tablaSimbolos = new HashMap<String, TablaSimbolos>();
	ArrayList<String> linea = new ArrayList<String>();
	public static ArrayList<String> variables = new ArrayList<>();
	private ArrayList<String> operaciones = new ArrayList<String>();
	static int address=10;
    static int index=1;
	char inicial;
	
	public GenerarTabla(String codigo) {
		
		Leer(codigo);
		LlenarTablaSimbolos();
		imprimirTablaSimbolos();
		if (!AnalisisSemantico.errores)
			triplos(); // si no hay errores semanticos procedemos a hacer los triplos
		
	}
	
	public void Leer(String codigo) {
		String renglon = "";
		for (int i = 0; i < codigo.length(); i++) {
			if (codigo.charAt(i) == '\n') {
				linea.add(renglon);
				renglon = "";
			} else
				renglon += Character.toString(codigo.charAt(i));
		}
	}
	
	public void LlenarTablaSimbolos() {
		String renglon;
		CharSequence sInt = "int", sIgual = "=", sString = "String", sDouble = "double", sChar = "char";
		for (int i = 0; i < linea.size(); i++) {
			renglon = linea.get(i);
			if (renglon.contains(sInt) || renglon.contains(sDouble) || renglon.contains(sChar)
					|| renglon.contains(sString)) {
				AgregaVariable(renglon, sInt, i + 1);
				AgregaVariable(renglon, sString, i + 1);
				AgregaVariable(renglon, sDouble, i + 1);
				AgregaVariable(renglon, sChar, i + 1);
			
			} else {
				if (renglon.contains(sIgual)) {
					/* Si contiene igual y algun operador entonces es una operacion */
					if (renglon.contains("+") || renglon.contains("-") || renglon.contains("/")
							|| renglon.contains("+"))
						operaciones.add(renglon);
					// Si contiene parentesis los omitimos, ya que estos solo los ocupamos en las
					// operaciones
					if (renglon.contains("(") && renglon.contains(")")) {
						renglon = renglon.replaceAll("\\(", "");
						renglon = renglon.replaceAll("\\)", "");
					}
					//Operaciones(renglon, i + 1);
					}
				}
			}
	}
	
	public void AgregaVariable(String renglon, CharSequence Tipo, int pos) {
		String parrafoAux = "", variable = "", valor = "";
		CharSequence sIgual = "=", sPublic = "public", sPrivate = "private";
		// Llenado de la declaración de las variables
		if (renglon.contains(Tipo)) {
			for (int j = 0; j < renglon.length(); j++) {
				if (parrafoAux.contains(Tipo)) {
					if (parrafoAux.contains(sIgual)) {
						if (renglon.charAt(j)!= '=' && renglon.charAt(j)!= ' ' && renglon.charAt(j)!= ';') {
							valor += Character.toString(renglon.charAt(j));
						}
					} else {
						if (renglon.charAt(j)!= '=' && renglon.charAt(j)!= ' ' && renglon.charAt(j)!= ';') {
							variable += Character.toString(renglon.charAt(j));
						}
					}
				}
				parrafoAux += Character.toString(renglon.charAt(j));
			}
			if (tablaSimbolos.containsKey(variable)) {
				AnalisisSemantico.errores = true;
				comp.textarea3.append("Error: La variable " + "'" + variable + "'" + " ya se encuentra declarada en el renglon "
						+ tablaSimbolos.get(variable).getPosicion() + "."); comp.textarea3.append(System.getProperty("line.separator"));
			}
			else {
			if (renglon.contains(sPublic) || renglon.contains(sPrivate)) {
				tablaSimbolos.put(variable, new TablaSimbolos(Tipo.toString(), pos, valor, "global"));
				variables.add(variable);
			} else {
				tablaSimbolos.put(variable, new TablaSimbolos(Tipo.toString(), pos, valor, "local"));
				variables.add(variable);
			}
			}
		}
	}
	
	public void imprimirTablaSimbolos() {
		for (int i = 0; i < variables.size(); i++) {
			for (int j=0; j<5; j++) {
				switch (j) {
				case 0:
					comp.dtm.setValueAt(variables.get(i), i, j);
					break;
				case 1:
					comp.dtm.setValueAt(tablaSimbolos.get(variables.get(i)).getTipo(), i, j);
					break;
				case 2:
					comp.dtm.setValueAt(tablaSimbolos.get(variables.get(i)).getValor(), i, j);
					break;
				case 3:
					comp.dtm.setValueAt(tablaSimbolos.get(variables.get(i)).getPosicion(), i, j);
					break;
				case 4:
					comp.dtm.setValueAt(tablaSimbolos.get(variables.get(i)).getAlcance(), i, j);
					break;
				default:
					break;
				}
			}
		}
	}
	
	public void triplos() { // Generación de triplos
		String operacion, operacionAux;
		
		for (int i = 0; i < operaciones.size(); i++) {
			index = 1;
			address = 10;
			operacion = operaciones.get(i).replaceAll("\\s", ""); // quitamos espacios en blanco
			operacionAux = quitaIgual(operacion);
			comp.textarea4.append("Operación: " + operacion+"\n");
			Shunting parentNode=shunt(operacionAux);
	       
	        postOrder(parentNode);
	       
	        dfs(parentNode);
	        comp.textarea4.append(Character.toString(operacion.charAt(0))+" = T"+(index-1)+"\n\n");
		}
	}
	
	public static void dfs(Shunting root){
        if (isOperator(root.charac)){
            dfs(root.operand1);
            dfs(root.operand2);
//            System.out.println(++memory  +" : "+ root.charac +" " + root.operand1.charac +" " + root.operand2.charac);
            comp.textarea4.append(root.name +" = " + root.operand1.name + " "+ root.charac  + " " + root.operand2.name+"\n");
            
        }
    }


    public static void postOrder(Shunting root){
        if (root.operand1!=null){
            postOrder(root.operand1);
        }

        if (root.operand2!=null){
            postOrder(root.operand2);
        }
        System.out.println(root.charac +" ");
    }



    private static Shunting shunt(String inputString) {


        Stack1 myStack=new Stack1();
        Operator opr=new Operator();

//        String inputString="5*3+(4+2*2)";

        Stack<Character> operatorStack= new Stack();
        Stack<Shunting> expressionStack=new Stack();

        Character c;

        for (int i=0;i<inputString.length();i++){
        	
            c=inputString.charAt(i);
            	

            if (c=='('){
                operatorStack.push(c);
            }

            else if (!isOperator(c) && c!='(' && c!=')'){
                expressionStack.push(new Shunting(c));
            }

            else if (isOperator(c)){

                    while (opr.getOperatorPrecedence(myStack.getTopOfOperator(operatorStack)) >= opr.getOperatorPrecedence(c)) {
                        Character operator = operatorStack.pop();
                        Shunting e2 = expressionStack.pop();
                        Shunting e1 = expressionStack.pop();

                        expressionStack.push(new Shunting(operator,e1,e2,"T"+index++));
                    }

                operatorStack.push(c);
            }

            else if (c==')'){

                    while (myStack.getTopOfOperator(operatorStack) != '(') {

                        Character operator = operatorStack.pop();
                        Shunting e2 = expressionStack.pop();
                        Shunting e1 = expressionStack.pop();

                        expressionStack.push(new Shunting(operator,e1,e2,"T"+index++));
                    }

                operatorStack.pop();
            }

            
            else{
                System.out.println("error error");
            }
        }

        while(!operatorStack.empty()){
            Character operator=operatorStack.pop();
            Shunting e2=expressionStack.pop();
            Shunting e1=expressionStack.pop();
            expressionStack.push(new Shunting(operator,e1,e2,"T"+index++));
        }


        return expressionStack.pop();
    }

    public static boolean isOperator(Character c){
        if (c=='+' || c=='-' || c=='/' || c=='*'|| c=='%'){
            return true;
        }
        else{
            return false;
        }
    }
			

	String quitaIgual(String operacion) {
		int indice;
		indice = operacion.indexOf("=");
		inicial= operacion.charAt(indice-1);
		operacion = operacion.replace(Character.toString(operacion.charAt(indice - 1)), "");
		operacion = operacion.replace("=", "");
		operacion = operacion.replace(";", "");
		return operacion;
	}

	
	
	

	
}


