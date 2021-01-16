package reproductor.mainClasses;

import java.io.Serializable;

/** Clase para gestionar contadores
 */
public class Counter implements Serializable{
	
	private int cont = 0;  // Contador entero
	
	/** Inicializa un contador a cero
	 */
	public Counter() {
	}
	/** Inicializa un contador con el valor indicado
	 * @param valor	Valor de inicio del contador
	 */
	public Counter( int valor ) {
		cont = valor;
	}
	/** Devuelve el valor del contador
	 * @return	Valor actual del contador
	 */
	public int get() { return cont; }
	/** Incrementa el contador
	 */
	public void inc() { cont++; }
	
	public void inc( int inc ) { cont += inc; }
	
	public void dec() { cont--; }
	
	public void dec( int dec ) { cont -= dec; }
	
	public void reset() { cont = 0;}
	
	@Override
	public String toString() { return "" + cont; }
	
}
