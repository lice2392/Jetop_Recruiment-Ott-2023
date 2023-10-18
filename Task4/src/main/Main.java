package main;
import jetop.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {

	public static void main(String[] args) {
		Jetop j;
		
		//Creo l'oggetto 'j' di tipo 'Jetop' tramite il metodo statico 'JetopFromFile' della 
		//classe 'Jetop'.
		//Se 'j' è pari a 'null' o se vengono lanciate le eccezioni 'WrongDateFormat'
		//e 'WrongFileFormat' significa che la lettura da file non è andata a buon fine:
		//il programma termina.
		try {
			j = Jetop.JetopFromFile("meetings.csv");
		} catch (WrongDateFormat | WrongFileFormat e) {
			System.err.println(e.getMessage());
			return;
		}
		if(j==null)
			return;
		
		//Setto la data odierna tramite il metodo 'setDataAttuale' della classe 'Jetop'.
		//Il programma termina se viene lanciata l'eccezione 'WrongDateFormat' (la data 
		//inserita non è nel formato corretto 'dd/mm/aaaa').
		try {
			j.setDataAttuale(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			//j.setDataAttuale("1/10/2022");
		} catch (WrongDateFormat e) {
			System.err.println(e.getMessage());
			return;
		}
		
		//Permetto all'utente di inserire un'area tra quelle disponibili fino a quando egli non inserisce
		//la parola 'FINE'.
		String area;
		Scanner input=new Scanner(System.in);
		try {
			do {
				System.out.println("A quale area appartieni? (IT, M&C, D&V, C&L, AUDIT, HR)");
				System.out.println("1) Digita 'IT, M&C, D&V, C&L, AUDIT o HR' se vuoi inserire la tua area di appartenenza;");
				System.out.println("2) Digita 'FINE' per terminare.");
				System.out.print("Inserisci: ");
				area=input.next();
				area=area.toUpperCase();
				if(area.equals("FINE")!=true) {
					//Se il contenuto della stringa 'area' non è né 'FINE' né un'area tra quelle disponibili 
					//stampo un messaggio di errore.
					if(j.getAreeDisponibili().contains(area)==false) {
						System.out.println("\nArea inesistente.\n");
					}
					//In caso contrario, stampo le riunioni programmate per l'area inserita dall'utente che non si
					//sono ancora svolte. La lista di tali riunioni è ottenuta tramite il metodo 'getRiunioniArea'
					//della classe 'Jetop'.
					else {
						System.out.println("\nOggi è il "+j.getDataAttuale()+" - Riuniuni programmate per l'area "+area+":");
					    System.out.println(j.getRiunioniArea(area)+"\n");
					}
				}
			}while(area.equals("FINE")!=true);
			System.out.println("\nPROGRAMMA TERMINATO.");
	    } finally {
	        input.close();
	    }
	}
}
