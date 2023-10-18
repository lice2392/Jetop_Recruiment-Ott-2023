package jetop;
import java.util.*;
import static java.util.stream.Collectors.toList;
import java.io.*;
import java.time.LocalDate;

/**
 * Classe di facciata che interagisce con i clienti.
 * Rappresenta il sistema che gestisce le attività di Jetop.
 *
 */
public class Jetop {
	private List<Riunione> riunioni;
	private List<String> areeDisponibili;
	private LocalDate dataOggi;
	
	
	/**
	 * Costruttore.
	 */
	public Jetop() {
		this.riunioni=new ArrayList<>();
		this.areeDisponibili=new ArrayList<>(Arrays.asList("IT", "M&C",  "D&V", "C&L", "AUDIT", "HR"));
	}
	
	
	/**
	 * Setta la data corrente.
	 * 
	 * @param date stringa che rappresenta la data odierna
	 * @throws WrongDateFormat nel caso in cui la data non sia nel formato 'dd/mm/aaaa'
	 */
	public void setDataAttuale(String date) throws WrongDateFormat{
		if(Jetop.isFormatDataCorrect(date)==false) {
			throw new WrongDateFormat("La data deve essere nella forma dd/mm/aaaa.");
		}
		//Se sono arrivata fin qua significa che 'date' è nel giusto formato 'dd/mm/aaaa'.
		//Pongo l'attributo 'dataOggi' (che è di tipo 'LocalDate') pari alla data 'date' passata
		//come parametro mediante il metodo statico 'parse' della classe 'LocalDate'. Poiché
		//tale metodo accetta una stringa data nel formato 'aaaa-mm-gg', tramite il metodo 
		//statico 'getRightFormatToParse' della classe 'Jetop' trasformo 'date' dal formato
		//'dd/mm/aaaa' al formato 'aaaa-mm-gg'.
		this.dataOggi=LocalDate.parse(Jetop.getRightFormatToParse(date));
		return;
	} 
	
	
	/**
	 * Controlla che la data sia nel formato corretto (dd/mm/aaaa).
	 * 
	 * @param date stringa che rappresenta una data
	 */
	private static boolean isFormatDataCorrect(String date) {
		//Check 1: 'date' deve essere formata da tre sottostringhe, tutte
		//          separate da '/'.
		//Tramite il metodo 'split' salvo in 'dataDivisa' le sottostringhe di
		//'date' che sono separate da '/'.
		String[] dataDivisa=date.split("/");
		//Ritorno 'false' se 'dataDivisa' non è composta da 3 stringhe.
		if(dataDivisa.length!=3) {
			return false;
		}
		//Check 2:  le tre sottostringhe che compongono 'date' devono essere numeriche.
		//Ritorno 'false' se una delle tre stringhe di 'dataDivisa' non è un numero.
		//(Tale controllo viene effettuato tramite il metodo statico 'isNumeric' della
		//classe 'Jetop').
		if(Jetop.isNumeric(dataDivisa[0])==false) {
			return false;
		}
		if(Jetop.isNumeric(dataDivisa[1])==false) {
			return false;
		}
		if(Jetop.isNumeric(dataDivisa[2])==false) {
			return false;
		}
		//Check 3: correttezza di 'giorno' e 'mese'.
		//Salvo nelle variabili intere 'giorno', 'mese' e 'anno' rispettivamente la prima,
		//la seconda e la terza stringa di 'dataDivisa' tramite il metodo statico 'parseInt' 
		//della classe 'Integer'.
		int giorno=Integer.parseInt(dataDivisa[0]);
		int mese=Integer.parseInt(dataDivisa[1]);
		int anno=Integer.parseInt(dataDivisa[2]);
		//Ritorno 'false' se il mese non è un numero compreso tra 1 e 12.
		if(mese<1 || mese>12) {
			return false;
		}
		//Ritorno 'false' se il mese è 'Gennaio', 'Marzo', 'Maggio', 'Luglio', 'Agosto', 'Ottobre',
		//o 'Dicembre' e il giorno non è un numero compreso tra 1 e 31.
		if(mese==1 || mese==3 || mese==5 || mese==7 || mese==8 || mese==10 || mese==12) {
			if(giorno<1 || giorno>31) {
				return false;
			}
		}
		//Ritorno 'false' se il mese è 'Aprile', 'Giugno', 'Settembre' o 'Novembre'
		//e il giorno non è un numero compreso tra 1 e 30.
		else if(mese==4 || mese==6 || mese==9 || mese==11) {
			if(giorno<1 || giorno>30) {
				return false;
			}
		}
		//Ritorno 'false' se il mese è 'Febbraio' e:
		//a) il giorno non è un numero tra 1 e 29 in caso di anno bisestile;
		//b) il giorno non è un numero tra 1 e 28 in caso di anno non bisestile.
		else if(mese==2) {
			if((anno%4==0 && anno%100==0 && anno%400==0) || (anno%4==0 && anno%100!=0)) {
				if(giorno<1 || giorno>29) {
					return false;
				}
			}
			else {
				if(giorno<1 || giorno>28) {
					return false;
				}
			}
		}
		//Se sono arrivata fin qua significa che la data è nel formato corretto.
		return true;
	}
	
	
	/**
	 * Controlla che una stringa sia numerica.
	 * 
	 * @param s stringa generica
	 */
	private static boolean isNumeric(String s) {
		//Tramite il metodo statico 'parseInt' della classe 'Integer' cerco di 
		//convertire in intero la stringa 's' passata come parametro.
		//Se la conversione va a buon fine, ritorno 'true'; se, invece, viene 
		//lanciata l'eccezione 'NumberFormatException' significa che la stringa 's'
		//non è numerica: ritorno 'false'.
		try {
			Integer.parseInt(s);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Converte una data nel formato 'dd/mm/aaaa' in una data nel formato
	 * 'aaaa-mm-gg' (giusto formato per utilizzare il metodo statico 
	 * {@code parse} della classe {@code LocalDate}.
	 * 
	 * @param date stringa che rappresenta una data nel formato dd/mm/aaaa
	 * @return stringa che rappresenta una data nel formato aaaa-mm-gg
	 */
	private static String getRightFormatToParse(String date) {
		int giorno=Integer.parseInt(date.split("/")[0]);
		int mese=Integer.parseInt(date.split("/")[1]);
		int anno=Integer.parseInt(date.split("/")[2]);
		return String.format("%04d-%02d-%02d", anno, mese, giorno);
	}
	
	
	/**
	 * Crea un nuovo oggetto Jetop mediante la lettura delle sue riunioni da file.
	 * 
	 * Il file deve essere un file CSV e deve contenere i seguenti campi:
	 * <ul>
	 * <li>{@code "Nome della riunione"},
	 * <li>{@code "Data della riunione"},
	 * <li>{@code "Luogo della riunione"}
	 * </ul>
	 * 
	 * I campi sono separati dal punto e virgola (';') e devono essere tutti pieni.
	 * 
	 * @param file path del file
	 * @throws WrongFileFormat nel caso in cui il file non sia nella forma
	 *         sopra descritta o se uno dei campi è vuoto
	 * @throws WrongDateFormat se la data letta non è nella forma 'dd/mm/aaaa'
	 * @return oggetto Jetop creato
	 */
	public static Jetop JetopFromFile (String file) throws WrongDateFormat, WrongFileFormat{
		List<String> fileRead = new ArrayList<>();
		String[]campiRiga;
		String nomeRiunione, data, luogo; 
		//Tramite il metodo statico 'readData' della classe 'Jetop' leggo le righe del file 
		//e le salvo nella lista di stringhe 'fileRead'.
		fileRead=Jetop.readData(file);
		//Se 'fileRead' è pari a 'null' significa che la lettura non è andata a buon fine:
		//ritorno 'null'.
		if(fileRead==null) {
			return null;
		}
		//Creo un nuovo oggetto 'j' di tipo 'Jetop'.
		Jetop j=new Jetop();
		//Scorro le linee del file lette e presenti nella lista 'fileRead'.
		for(int i=0; i<fileRead.size(); i++) {
			//I campi di un file CSV sono separati dal punto e virgola (';'). 
			//Salvo in 'campiRiga' i campi della riga i-esima del file tramite
			//il metodo 'split'.
			campiRiga=fileRead.get(i).split(";");
			//Lancio un eccezione se i campi della riga i-esima non sono 3.
			if(campiRiga.length!=3) {
				throw new WrongFileFormat("Ogni riga del file deve essere nella forma <NOME_RIUNIONE><DATA><LUOGO_RIUNIONE>.");
			}
			//Salvo in 'nomeRiunione', 'data' e 'luogo' rispettivamente il primo, il secondo e il
			//terzo elemento di 'campiRiga'.
			nomeRiunione=campiRiga[0];
			data=campiRiga[1];
			luogo=campiRiga[2];
			//Lancio un eccezione se uno dei tre campi è vuoto e se la data letta non è nel 
			//formato corretto (dd/mm/aaaa).
			if(nomeRiunione.isBlank()==true) {
				throw new WrongFileFormat("Il campo NOME_RIUNIONE del file non può essere vuoto.");	
			}
			if(data.isBlank()==true) {
				throw new WrongFileFormat("Il campo DATA del file non può essere vuoto.");	
			}
			if(luogo.isBlank()==true) {
				throw new WrongFileFormat("Il campo LUOGO_RIUNIONE del file non può essere vuoto.");	
			}
			if(Jetop.isFormatDataCorrect(data)==false) {
				throw new WrongDateFormat("Il campo DATA del file deve essere nella forma dd/mm/aaaa.");	
			}
			//Se sono arrivata fin qua significa che la riga i-esima del file non crea problemi.
			//Creo un oggetto riunione e lo aggiungo alla lista delle riunioni dell'oggetto 'j' 
			//di tipo 'Jetop'.
			Riunione r=new Riunione(nomeRiunione.toUpperCase(), LocalDate.parse(Jetop.getRightFormatToParse(data)), luogo);
			j.riunioni.add(r);
			
		}
		//Ordino le riunioni dell'oggetto 'j' di tipo 'Jetop' in ordine cronologico.
		Collections.sort(j.riunioni);
		return j;
	}

	
	/**
	 * Legge le righe del file di testo e le salva in una lista di stringhe.
	 * 
	 * @param file path del file
	 * @return una lista contenente le linee del file
	 */
	private static List<String> readData(String file) {
		//Leggo il file di testo.
		//Se la lettura va a buon fine ritorno la lista di stringhe contenente le 
		//righe del file letto; in caso contrario viene lanciata l'eccezione 
		//'IOException' e ritorno 'null.
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Seleziona la lista di riunioni programmate per una certa area che non si sono ancora
     * svolte.
     * 
     * La lista di tali riunioni è una stringa avente una riunione per riga (le righe sono
	 * (terminate da un a-capo "\n").
	 * Ogni riunione è descritta da nome della riunione, data e luogo della riunione 
	 * separati da spazi.
	 * 
	 * @param area area di appartenenza di un associato
	 * @return lista di riunioni programmate per una certa area che non si sono ancora
     *         svolte.
	 */
	public String getRiunioniArea(String area) {
		//Se la lista 'riunioni' non contiene nessuna riunione, 
		//ritorno 'Nessuna riunione programmata.'
		if(this.riunioni.size()==0) {
			return "Nessuna riunione programmata.";
		}
		int maxName=0;
		String output="";
		//Determino la lunghezza massima tra le lunghezze dei nomi delle
		//riunioni presenti.
		//Questo valore mi serve per stampare in colonna i campi delle 
		//riunioni.
		for(Riunione r: this.riunioni) {
			if(r.getNome().length()>maxName) {
				maxName=r.getNome().length();
			}
		}
		//Ciclo su tutte le riunioni presenti nella lista 'riunioni'.
		//Tra tutte le riunioni, considero quelle la cui data è pari o successiva alla 
		//data odierna e il cui nome o contiene 'GENERALE' (RIUNIONE GENERALE) oppure 
		//contiene l'area passata come parametro.
		for(Riunione r: this.riunioni) {
			if(r.getData().isEqual(dataOggi)==true || r.getData().isAfter(dataOggi)==true) {
				if(r.getNome().contains(area)==true) {
					if(area.equals("IT")==false || (area.equals("IT")==true && r.getNome().contains("AUDIT")==false)) {
						output+=String.format("%-"+maxName+"s", r.getNome());
						output+="\t"+r.getData().toString()+"\t"+r.getLuogo()+"\n";
					}
				}
				else if(r.getNome().contains("GENERALE")==true) {
					output+=String.format("%-"+maxName+"s", r.getNome());
					output+="\t"+r.getData().toString()+"\t"+r.getLuogo()+"\n";
				}
			}
		}
		//Se 'output' è una stringa vuota significa che per l'area
		//passata come parametro non sono presenti riunioni: ritorno
		//'Nessuna riunione programmata.'
		if(output.equals("")==true) {
			return "Nessuna riunione programmata.";
		}
		return output;
	}
	
	
	/**
	 * Metodo <b>{@code get}</b> per ottenere la lista delle aree disponibili.
	 * 
	 * @return lista delle aree disponibili
	 */
	public List<String> getAreeDisponibili(){
		return this.areeDisponibili;
	}
	
	
	/**
	 * Metodo <b>{@code get}</b> per ottenere la data attuale.
	 * 
	 * @return data attuale in formato stringa
	 */
	public String getDataAttuale() {
		return this.dataOggi.toString();
	}
}
