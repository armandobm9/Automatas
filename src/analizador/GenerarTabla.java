package analizador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import analizador.TablaSimbolos;

public class GenerarTabla {
	private HashMap<String, TablaSimbolos> tablaSimbolos = new HashMap<String, TablaSimbolos>();
	ArrayList<String> linea = new ArrayList<String>();
	private ArrayList<String> variables = new ArrayList<>();
	
	public GenerarTabla(String codigo) {
		
		recorreCodigo(codigo);
		LlenaTabla();
		
		System.out.println("\t" + "TABLA DE SIMBOLOS" + "\t");
		System.out.println("Variable\t" + "Tipo de dato\t" + "Valor\t" + "Posición\t" + "Alcance\t");
		imprimeTabla();
		System.out.println();
	}
	
	public void recorreCodigo(String codigo) {
		String parrafo = "";
		for (int i = 0; i < codigo.length(); i++) {
			if (codigo.charAt(i) == '{' || codigo.charAt(i) == '}' || codigo.charAt(i) == ';') {
				linea.add(parrafo);
				parrafo = "";
			} else
				parrafo += Character.toString(codigo.charAt(i));
		}
	}
	
	public void LlenaTabla() {
		String parrafo;
		CharSequence sInt = "int", sIgual = "=";
		CharSequence sString = "String";
		CharSequence sDouble = "double";
		CharSequence sChar = "char";
		for (int i = 0; i < linea.size(); i++) {
			parrafo = linea.get(i);
			if (parrafo.contains(sInt) || parrafo.contains(sDouble) || parrafo.contains(sChar)
					|| parrafo.contains(sString)) {
				AgregaVariable(parrafo, sInt, i + 1);
				AgregaVariable(parrafo, sString, i + 1);
				AgregaVariable(parrafo, sDouble, i + 1);
				AgregaVariable(parrafo, sChar, i + 1);
			
			}
			}
	}
	
	public void AgregaVariable(String parrafo, CharSequence Tipo, int pos) {
		String parrafoAux = "", variable = "", valor = "";
		CharSequence sIgual = "=", sPublic = "public", sPrivate = "private";
		// Llenado de la declaración de las variables
		if (parrafo.contains(Tipo)) {
			for (int j = 0; j < parrafo.length(); j++) {
				if (parrafoAux.contains(Tipo)) {
					if (parrafoAux.contains(sIgual)) {
						valor += Character.toString(parrafo.charAt(j));
					} else {
						if (parrafo.charAt(j) == ' ') {
							variable = Character.toString(parrafo.charAt(j + 1));
						}
					}
				}
				parrafoAux += Character.toString(parrafo.charAt(j));
			}
			if (tablaSimbolos.containsKey(variable)) {
				comp.textarea3.append("Error: La variable " + "'" + variable + "'" + " ya se encuentra declarada en el renglon "
						+ tablaSimbolos.get(variable).getPosicion() + "."); comp.textarea3.append(System.getProperty("line.separator"));
			}
			else {
			if (parrafo.contains(sPublic) || parrafo.contains(sPrivate)) {
				tablaSimbolos.put(variable, new TablaSimbolos(Tipo.toString(), pos, valor, "global"));
				variables.add(variable);
			} else {
				tablaSimbolos.put(variable, new TablaSimbolos(Tipo.toString(), pos, valor, "local"));
				variables.add(variable);
			}
			}
		}
	}
	
	/*public void imprimeTabla() {
		for (int i = 0; i < variables.size(); i++) {
			System.out.println(variables.get(i) + "\t\t" + tablaSimbolos.get(variables.get(i)).getTipoDato() + "\t\t"
					+ tablaSimbolos.get(variables.get(i)).getValor() + "\t\t"
					+ tablaSimbolos.get(variables.get(i)).getPosicion() + "\t"
					+ tablaSimbolos.get(variables.get(i)).getAlcance());
		}

	}*/
	public void imprimeTabla() {
		for (int i = 0; i < variables.size(); i++) {
			for (int j=0; j<5; j++) {
				switch (j) {
				case 0:
					comp.dtm.setValueAt(variables.get(i), i, j);
					break;
				case 1:
					comp.dtm.setValueAt(tablaSimbolos.get(variables.get(i)).getTipoDato(), i, j);
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


