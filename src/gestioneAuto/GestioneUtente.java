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

public class GestioneUtente implements Serializable{
	
	List<Utente> listaUtente=new ArrayList<>();
	private String filePath1;
	
	public GestioneUtente(String file) {
		this.filePath1=file;
		this.listaUtente=caricaDaFile();
	}
	
	public List<Utente> getListaUtente() {
		return listaUtente;
	}

	
	public void creaUtente(String nome,String cognome,int età,String codiceFiscale,String username,String password) {
		Utente nuovo= new Utente(nome,cognome,età,codiceFiscale,username,password);
		Utente cercaUsername=cercaUtenteDaUsername(username);
		Utente cercaCodiceFiscale=cercaUtentePerCodiceFiscale(codiceFiscale);
	    if (cercaCodiceFiscale!=null){
            throw new IllegalArgumentException("Utente con codice fiscale " + codiceFiscale + " già esistente.");
        }
	    if(cercaUsername!=null) {
	    	throw new IllegalArgumentException("Utente con username " + username + " già esistente.");
	    }
        listaUtente.add(nuovo);
        salvaSuFile();
	}
	
	public Utente accedi(String username,String password) {
		Utente cerca=cercaUtenteDaUsername(username);
		boolean verifica=false;
		if(cerca==null) {
			throw new IllegalArgumentException("Account non esistente.");
		}else {
			for(Utente a:listaUtente) {
				String user=a.getUsername();
				String pass=a.getPassword();
				if(user.equals(username) && pass.equals(password)) {
					verifica=true;
					break;
				}
			}
		}
		if(!verifica){
			cerca=null;	
		}
		return cerca;
	}
	
	public void modificaPassword(String username,String vecchiaPassword,String nuovaPassword) {
		Utente trovato=cercaUtenteDaUsername(username);
		if(trovato!=null && trovato.getPassword().equals(vecchiaPassword)) {
			trovato.setPassword(nuovaPassword);
		}else {
			throw new IllegalArgumentException("I dati inseriti non sono corretti");
		}
		salvaSuFile();
	}
	
	public void modificaUsername(String vecchioUsername,String password,String nuovoUsername) {
		Utente trovato=cercaUtenteDaUsername(vecchioUsername);
		Utente verifica=cercaUtenteDaUsername(nuovoUsername);
		if(trovato!=null && verifica==null && trovato.getPassword().equals(password)) {
			trovato.setUsername(nuovoUsername);
		}
		if(trovato==null) {
			throw new IllegalArgumentException("Nessun utente trovato con username: "+ vecchioUsername+".");
		}
		if(verifica!=null) {
			throw new IllegalArgumentException("L'username: "+ nuovoUsername+" è già utilizzato da un altro utente.");
		}
		if(!trovato.getPassword().equals(password)) {
			throw new IllegalArgumentException("La password inserita non è corretta.");
		}
		salvaSuFile();
	}
	
	public Utente cercaUtenteDaUsername(String username) {
		Utente trovato=null;
		for(Utente u:listaUtente) {
			if(u.getUsername().equals(username)) {
				trovato=u;
				break;
			}
		}
		return trovato;
	}
	
	public void modificaUtente(String codiceFiscale,String nuovoNome,String nuovoCognome,int nuovaEtà) {
		boolean trovata = false; 
		for(Utente u:listaUtente) {
			if(u.getCodiceFiscale().equals(codiceFiscale)) {
				u.setNome(nuovoNome);
				u.setCognome(nuovoCognome);
				u.setEtà(nuovaEtà);
				trovata=true;
				break;
			}
		}
		if (!trovata) {
			throw new IllegalArgumentException("Utente con codice fiscale "+ codiceFiscale +" non è presente in lista");
		}
		  salvaSuFile();
	}
	
	
	
	public void eliminaUtente(String codiceFiscale,GestioneAuto gestioneAuto) {
		Utente daRimuovere=null;
		List<Auto> trovate=gestioneAuto.cercaAutoPerUtente(codiceFiscale);
		for(Utente u:listaUtente) {
			if(u.getCodiceFiscale().equalsIgnoreCase(codiceFiscale)) {
				daRimuovere=u;
				break;
			}
		}
		if(daRimuovere!=null) {
			listaUtente.remove(daRimuovere);
		}else {
			throw new IllegalArgumentException("Utente con codice fiscale " + codiceFiscale + " non è presente nella lista.");
		}
		
		if(daRimuovere!=null && trovate!=null) {
			for(Auto a:trovate) {
				a.setCodiceFiscale(null);
			}
		}
		  salvaSuFile();
		  gestioneAuto.salvaSuFile();
	}
	
	
	
	public void visualizzaUtenti() {
		for(Utente u:listaUtente) {
			System.out.println(u);
		}
	}
	
	
	public Utente cercaUtentePerCodiceFiscale(String codiceFiscale) {
		Utente trovato=null;
		for(Utente u:listaUtente) {
			if(u.getCodiceFiscale().equals(codiceFiscale)) {
				trovato=u;
				break;
			}
		}
		return trovato;
	}
	
	
	public void associaUtenteRuolo(GestioneRuolo gestioneRuolo,String codiceFiscale,String ruolo) {
		Utente u=cercaUtentePerCodiceFiscale(codiceFiscale);
		Ruolo r=gestioneRuolo.cercaRuoloPerNome(ruolo);
		if(u==null) {
			throw new IllegalArgumentException("Utente con codice fiscale " + codiceFiscale + " non è presente nella lista.");
		}
		if(r==null) {
			throw new IllegalArgumentException("Il ruolo non esiste");
		}else {
			u.setRuolo(ruolo);
		}
		salvaSuFile();
	}
	
	public List<Utente> cercaUtentePerRuolo(String ruolo) {
		List<Utente> trovati=new ArrayList<>();
		for(Utente u:listaUtente) {
			if(u.getRuolo().equalsIgnoreCase(ruolo)){
				trovati.add(u);
			}
		}
		return trovati;
	}
	
	public String cercaRuoloDaCodiceFiscale(String codiceFiscale,GestioneRuolo gestioneRuolo) {
		Utente cercaU=cercaUtentePerCodiceFiscale(codiceFiscale);
		if(cercaU==null) {
			throw new IllegalArgumentException("Utente non esiste");
		}
		String trovato=cercaU.getRuolo();
		if(trovato==null) {
			throw new IllegalArgumentException("L'utente non ha nessun ruolo");
		}
			
		return trovato;
	}
	
	
	public List<String> getMenu(String codiceFiscale,GestioneRuolo gestioneRuolo,GestioneGruppo gestioneGruppo,GestioneUtente gestioneUtente) {
		List<String> gruppiUtente=gestioneGruppo.appartenenzaRuoloGruppo(codiceFiscale,gestioneRuolo,gestioneUtente);
		return gruppiUtente;
	}
	
	public List<String> getSottoMenu(String codiceFiscale,String nomeGruppo,GestioneRuolo gestioneRuolo,GestioneGruppo gestioneGruppo,GestioneUtente gestioneUtente) {
		List<String> permessi=new ArrayList<>();
		List<String> permessiRuolo=gestioneRuolo.cercaPermessiRuolo(codiceFiscale, gestioneUtente, gestioneRuolo); 
		List<String> permessiGruppo=gestioneGruppo.cercaPermessiDaNome(nomeGruppo,codiceFiscale, gestioneRuolo, gestioneGruppo, gestioneUtente);
		
		for(String p:permessiGruppo) {
			for(String p1:permessiRuolo) {
				if(p.equalsIgnoreCase(p1)) {
					permessi.add(p);
				}
			}
		}
		return permessi;
	}
	
	
	

	public void salvaSuFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filePath1))) {
            oos.writeObject(listaUtente);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio su file: " + e.getMessage());
        }
    }
 
	private List<Utente> caricaDaFile() {
        File file = new File(filePath1);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Utente>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante il caricamento: " + e.getMessage());
            return new ArrayList<>();
        }
		
    }
	
}
