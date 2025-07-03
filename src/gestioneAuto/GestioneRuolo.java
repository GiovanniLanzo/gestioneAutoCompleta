package gestioneAuto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class GestioneRuolo implements Serializable{

	
	private List<Ruolo> listaRuoli;
	private String filePath2;

	public GestioneRuolo(String file) {
		this.filePath2=file;
		this.listaRuoli=caricaDaFile();
	}
	
	public List<Ruolo> getListaRuoli() {
		return listaRuoli;
	}

	public void creaRuolo(String nome) {
		Ruolo nuovo=new Ruolo(nome);
		if(listaRuoli.contains(nuovo)) {
			throw new IllegalArgumentException("Ruolo già esistente");
		}
		listaRuoli.add(nuovo);
		  salvaSuFile();
	}
	
	public void modificaRuolo(String nome,String nuovoNome) {
		 Ruolo r1=cercaRuoloPerNome(nome);
		 if(r1!=null) {
			 r1.setNome(nuovoNome);
		 }else {
			 throw new IllegalArgumentException("Il ruolo non esiste");
		 }
		  salvaSuFile();
	 }
	 
	 public void eliminaRuolo(String nome,GestioneUtente gestioneUtente) {
		 Ruolo daRimuovere=null;
		 List<Utente> trovati=gestioneUtente.cercaUtentePerRuolo(nome);
			for(Ruolo r:listaRuoli) {
				if(r.getNome().equalsIgnoreCase(nome)) {
					daRimuovere=r;
					break;
				}
			}
			if(daRimuovere!=null) {
				listaRuoli.remove(daRimuovere);
			}else {
				throw new IllegalArgumentException("Il ruolo non esiste");
			}
			
			if(trovati!=null) {
				for(Utente u:trovati) {
					u.setRuolo(null);
				}
			}
			  salvaSuFile();
			  gestioneUtente.salvaSuFile();
	 }
	 
	 public void visualizzaRuoli() {
		 for(Ruolo r:listaRuoli) {
			 System.out.println(r);
		 }
	 }
	 
	 public Ruolo cercaRuoloPerNome(String nome) {
		 Ruolo ruolo = null;
		 for(Ruolo r:listaRuoli) {
			 if(r.getNome().equalsIgnoreCase(nome)) {
				 ruolo=r;
				 break;
			 }
		 }
		 return ruolo;
	 }
	 
	public List<String> cercaPermessiRuolo(String codiceFiscale,GestioneUtente gestioneUtente,GestioneRuolo gestioneRuolo){
		List<String> permessi=new ArrayList<>();
		Ruolo ruolo=cercaRuoloPerNome(gestioneUtente.cercaRuoloDaCodiceFiscale(codiceFiscale, gestioneRuolo));//gestioneRuolo va messo anche se mi trovo in  questa gestione adesso?
		for(String p:ruolo.getPermessi()) {
			if(!permessi.contains(p)) {				
				permessi.add(p);
			}
		}
		return permessi;
	}
	 

		public void salvaSuFile() {
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filePath2))) {
	            oos.writeObject(listaRuoli);
	        } catch (IOException e) {
	            throw new RuntimeException("Errore durante il salvataggio su file: " + e.getMessage());
	        }
	    }
	 
		private List<Ruolo> caricaDaFile() {
	        File file = new File(filePath2);
	        if (!file.exists()) return new ArrayList<>();
	        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
	            return (List<Ruolo>) in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Errore durante il caricamento: " + e.getMessage());
	            return new ArrayList<>();
	        }
	  }
		
}
