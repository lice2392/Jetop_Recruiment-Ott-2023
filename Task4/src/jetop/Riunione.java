package jetop;
import java.time.LocalDate;

public class Riunione implements Comparable <Riunione>{
	private String nome;
	private LocalDate data;
	private String luogo;
	
	
	/**
	 * Costruttore.
	 * 
	 * @param nome nome della riunione
	 * @param data data della riunione
	 * @param luogo luogo della riunione
	 */
	public Riunione(String nome, LocalDate data, String luogo) {
		this.nome = nome;
		this.data = data;
		this.luogo = luogo;
	}
	

	/**
	 * Metodo <b>{@code get}</b> per ottenere il nome della riunione.
	 * 
	 * @return nome della riunione
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * Metodo <b>{@code get}</b> per ottenere la data della riunione.
	 * 
	 * @return data della riunione
	 */
	public LocalDate getData() {
		return data;
	}


	/**
	 * Metodo <b>{@code get}</b> per ottenere il luogo della riunione.
	 * 
	 * @return luogo della riunione
	 */
	public String getLuogo() {
		return luogo;
	}


	/**
	 * Implementazione del metodo <b>{@code compareTo}</b> dell'interfaccia {@link Comparable}.</br>
	 * 
	 * Le riunioni vengono confrontate e ordinate per data.
	 * 
	 * @param o riunione con cui confrontare la riunione attuale ({@code this})
	 * @return  <b>0</b> se le date delle due riunioni({@code o} e {@code this}) sono uguali; 
	 *          <b>un valore maggiore di 0</b> se la riunione attuale ({@code this}) ha una data 
	 *          che viene dopo di quella della riunione {@code o}; <b>un valore minore di 0</b>
	 *          se la riunione attuale ({@code this}) ha una data che viene prima di quella
	 *          della riunione {@code o}
	 */
	@Override
	public int compareTo(Riunione o) {
		return this.data.compareTo(o.data);
	}
}
