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

public class GestioneGruppo implements Serializable{

	private List<Gruppo> listaGruppi;
	private String filePath4;
	
	public GestioneGruppo(String file) {
		this.filePath4=file;
		this.listaGruppi=caricaDaFile();
	}
	
	public List<Gruppo> getListaGruppi() {
		return listaGruppi;
	}

	public void aggiungiGruppo(String nome) {
		Gruppo nuovo=new Gruppo(nome);
		Gruppo trovato=cercaGruppoPerNome(nome);
		
		if(trovato!=null) {
			throw new IllegalArgumentException("Gruppo già esistente");
		}else {
			listaGruppi.add(nuovo);
		}
		salvaSuFile();
	}
	
	public void modificaGruppo(String nome, String nuovoNome) {
		Gruppo trovato=cercaGruppoPerNome(nome);
		
		if(trovato!=null) {
			trovato.setNome(nuovoNome);
		}else {
			throw new IllegalArgumentException("Gruppo non trovato");
		}
		salvaSuFile();
	}
	
	public void eliminaGruppo(String nome) {
		Gruppo trovato=cercaGruppoPerNome(nome);
		if(trovato!=null) {
			listaGruppi.remove(trovato);
		}else {
			throw new IllegalArgumentException("Il Gruppo non esistente");
		}
		salvaSuFile();
	}
	
	public void visualizzaGruppo() {
		for(Gruppo g:listaGruppi) {
			System.out.println(g);
		}
	}
	
	public Gruppo cercaGruppoPerNome(String nome) {
		Gruppo trovato=null;
		for(Gruppo g:listaGruppi) {
			if(g.getNome().equalsIgnoreCase(nome)) {
				trovato=g;
				break;
			}
		}
		return trovato;
	}
	
	 public List<String> appartenenzaRuoloGruppo(String codiceFiscale,GestioneRuolo gestioneRuolo,GestioneUtente gestioneUtente) {
		 List<String> trovato=new ArrayList<>();
		 Ruolo ruolo=gestioneRuolo.cercaRuoloPerNome(gestioneUtente.cercaRuoloDaCodiceFiscale(codiceFiscale,gestioneRuolo));
		 if(ruolo.getPermessi()==null) {
			 throw new IllegalArgumentException("Questo ruolo non ha nessun permesso");
		 }
			 for(String p:ruolo.getPermessi()) {				 
					 for(Gruppo g:listaGruppi) {
						 for(String pg:g.getPermessi()) {
							 if(pg.equalsIgnoreCase(p) && !trovato.contains(g.getNome())) {					
								 trovato.add(g.getNome());					 									 
						 
					 }
				 }
			 }
		 }
		 return trovato;
	 }
	 
	 public List<String> cercaPermessiDaNome(String nomeGruppo,String codiceFiscale,GestioneRuolo gestioneRuolo,GestioneGruppo gestioneGruppo,GestioneUtente gestioneUtente){
		 List<String> trovati=new ArrayList<>();
		 List<String> listaGruppiMenu=gestioneUtente.getMenu(codiceFiscale,gestioneRuolo, gestioneGruppo, gestioneUtente);
		 for(String p:listaGruppiMenu) {
			 if(p==nomeGruppo) {
				trovati=cercaGruppoPerNome(p).getPermessi();				 
			 }
		 }
		 return trovati;
	 }
	
	public void salvaSuFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filePath4))) {
            oos.writeObject(listaGruppi);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio su file: " + e.getMessage());
        }
    }
   
	  private List<Gruppo> caricaDaFile() {
	        File file = new File(filePath4);
	        if (!file.exists()) return new ArrayList<>();
	        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
	            return (List<Gruppo>) in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Errore durante il caricamento: " + e.getMessage());
	            return new ArrayList<>();
	        }
	  }
}
