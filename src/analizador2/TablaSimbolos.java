package analizador2;

public class TablaSimbolos {
	private String tipo;
	private int posicion;
	private Object valor;
	private String alcance;

	public TablaSimbolos(String tipo, int posicion, Object valor, String alcance) {
		this.tipo = tipo;
		this.posicion = posicion;
		this.valor = valor;
		this.alcance = alcance;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public Object getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getAlcance() {
		return alcance;
	}
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
}
