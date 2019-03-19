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
	
	public GenerarTabla(String codigo) {
		
		Leer(codigo);
		LlenarTablaSimbolos();
		imprimirTablaSimbolos();
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
	
	
}


