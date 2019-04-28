package analizador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import analizador.TablaSimbolos;

public class GenerarTabla {
	public static HashMap<String, TablaSimbolos> tablaSimbolos = new HashMap<String, TablaSimbolos>();
	ArrayList<String> linea = new ArrayList<String>();
	public static ArrayList<String> variables = new ArrayList<>();
	private ArrayList<String> listaErroresSemanticos = new ArrayList<String>();
	private ArrayList<String> operadoreslogicos = new ArrayList<>(), operadoresaritmeticos = new ArrayList<>();
	private ArrayList<String> operaciones = new ArrayList<String>();
	private ArrayList<Triplo> t = new ArrayList<Triplo>();
	char inicial;
	
	public GenerarTabla(String codigo) {
		
		Leer(codigo);
		LlenarTablaSimbolos();
		imprimirTablaSimbolos();
		if (!AnalisisSemantico.errores)
			triplos(); // si no hay errores semanticos procedemos a hacer los triplos
		for(int i=0;i<t.size();i++) {
			System.out.println(t.get(i).getNum() + " = " + t.get(i).getOp());
		}
		System.out.println(inicial+"="+t.get(t.size()-1).getNum());
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
		int pos = 0, cont = 1;
		String triplo = "";
		for (int i = 0; i < operaciones.size(); i++) {
			operacion = operaciones.get(i).replaceAll("\\s", ""); // quitamos espacios en blanco
			operacionAux = quitaIgual(operacion);
			System.out.println("operación: " + operacion);
			while (!operacionAux.isEmpty()) {
				//verificamos si contiene un numero negativo
				if((operacionAux.contains("-")) && esOperador(operacionAux.charAt(operacionAux.indexOf("-") - 1))) {
							pos=operacionAux.indexOf("-");
							t.add(new Triplo("T"+cont,Character.toString(operacionAux.charAt(pos))+Character.toString(operacionAux.charAt(pos+1))));
							operacionAux = operacionAux.replace(Character.toString(operacionAux.charAt(pos))+Character.toString(operacionAux.charAt(pos+1)), "");
							cont++;
				}
				// Agregamos primero lo que se encuentra en parentesis
				if (operacionAux.contains("(")) {
					pos = operacionAux.indexOf("(") + 1;
					while (operacionAux.charAt(pos) != ')') {
						triplo = triplo + operacionAux.charAt(pos);
						pos++;
					}
					operacionAux = operacionAux.replace("(", "");
					operacionAux = operacionAux.replace(")", "");
					t.add(new Triplo("T" + cont, triplo));
					cont++;
					operacionAux = operacionAux.replace(triplo, "");
				} else {
					//System.out.println(operacionAux.length());
					// cuando la longitud es de 1, se realiza la operacion con los dos ultimos
					// triplos
					if (operacionAux.length() == 1) {
						triplo = t.get(cont-3).getNum() + operacionAux.charAt(0) + t.get(cont-2).getNum();
						t.add(new Triplo("T" + cont, triplo));
						operacionAux = operacionAux.replace(Character.toString(operacionAux.charAt(0)), "");
					} else {
						// Cuando la longitud es 2, se realiza la operacion con el operando y el ultimo
						// triplo
						if (operacionAux.length() == 2) {
							System.out.println(cont);
							triplo = Character.toString(operacionAux.charAt(0)) + Character.toString(operacionAux.charAt(1)) + t.get(cont - 2).getNum();
							t.add(new Triplo("T" + cont, triplo));
							operacionAux = operacionAux.replace(Character.toString(operacionAux.charAt(0))
									+ Character.toString(operacionAux.charAt(1)), "");
							cont++;
						} else {
							// Si es un operando se hace un triplo
							if (!esOperador(operacionAux.charAt(operacionAux.length() - 1))) {
								triplo = Character.toString(operacionAux.charAt(operacionAux.length() - 1));
								System.out.println(triplo);
								t.add(new Triplo("T" + cont, triplo));
								operacionAux = operacionAux.replace(triplo, "");
								cont++;
							} else {
								// Si es un operador y su antecesor es un operador se realiza la operacion con
								// los dos ultimos triplos
								if (esOperador(operacionAux.charAt(operacionAux.length() - 2))) {
									triplo = t.get(cont - 3).getNum() + Character.toString(operacionAux.charAt(operacionAux.length() - 1))
											+ t.get(cont - 2).getNum();
									t.add(new Triplo("T" + cont, triplo));
									operacionAux = operacionAux.substring(0, operacionAux.length() - 1);
									/*operacionAux = operacionAux.replace(
											Character.toString(operacionAux.charAt(operacionAux.length() - 1)), "");*/
									cont++;
								} else {
									// Si es un operando entonces se agrega un nuevo triplo
									if (!esOperador(operacionAux.charAt(operacionAux.length() - 2))) {
										triplo = Character.toString(operacionAux.charAt(0))
												+ Character.toString(operacionAux.charAt(1))
												+ Character.toString(operacionAux.charAt(2));
										t.add(new Triplo("T" + cont, triplo));
										operacionAux = operacionAux.replace(triplo, "");
										cont++;
									}
								}

							}

						}
					}
				}
			}

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

	boolean esOperador(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/')
			return true;
		return false;
	}
	
	

	
}


