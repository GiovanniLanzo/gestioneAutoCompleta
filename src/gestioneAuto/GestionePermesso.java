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

public class GestionePermesso implements Serializable{
	
	List<Permesso> listaPermessi=new ArrayList<>();
	private String filePath3;
	
	public GestionePermesso(String file) {
		this.filePath3=file;
		this.listaPermessi = caricaDaFile();
	}
	
	public List<Permesso> getListaPermessi() {
		return listaPermessi;
	}


	public void aggiungiPermesso(String nome) {
		Permesso nuovo=new Permesso(nome);
		Permesso trovato=cercaPermessoPerNome(nome);
		if(trovato!=null) {
			throw new IllegalArgumentException("Permesso già esistente");
		}else {
			listaPermessi.add(nuovo);
		}
		salvaSuFile();
	}
	
	public void modificaPermesso(String nome,String nuovoNome) {
		Permesso trovato=cercaPermessoPerNome(nome);
		if(trovato!=null) {
			trovato.setNome(nuovoNome);
		}
		salvaSuFile();
	}
	
	public void eliminaPermesso(String nomePermesso,GestioneRuolo gestioneRuolo,GestioneGruppo gestioneGruppo) {
		Permesso trovato=cercaPermessoPerNome(nomePermesso);
		List<Ruolo> listaR=gestioneRuolo.getListaRuoli();
		List<Gruppo> listaG=gestioneGruppo.getListaGruppi();
		
		if(trovato!=null) {
			listaPermessi.remove(trovato);
			
			for(Ruolo r:listaR) {
				dissociaPermessoRuolo(r.getNome(),nomePermesso,gestioneRuolo);		
			}
			
			for(Gruppo g:listaG) {
				dissociaPermessoGruppo(g.getNome(),nomePermesso,gestioneGruppo);						
			}
			
		}else {
			throw new IllegalArgumentException("Il permesso non esistente");
		}
		
		salvaSuFile();
	}
	
	public void visualizzaPermessi() {
		for(Permesso p:listaPermessi) {
			System.out.println(p);
		}
	}
	
	public Permesso cercaPermessoPerNome(String nome) {
		Permesso trovato=null;
		for(Permesso p:listaPermessi) {
			if(p.getNome().equalsIgnoreCase(nome)) {
				trovato=p;
				break;
			}
		}
		return trovato;
	}
	
	public void associaPermessoGruppo(String nomeGruppo,String nomePermesso,GestioneGruppo gestioneGruppo) {
		Gruppo trovaGruppo=gestioneGruppo.cercaGruppoPerNome(nomeGruppo);
		Permesso trovaPermesso=cercaPermessoPerNome(nomePermesso);
		boolean esiste=false;
		
		for(String g:trovaGruppo.getPermessi()) {
			 if(g.equalsIgnoreCase(nomePermesso)) {
				 esiste=true;
				 break;
			 }
		 }
		if(trovaGruppo==null) {
			throw new IllegalArgumentException("Il Gruppo non esiste");
		}
		if(trovaPermesso==null) {
			throw new IllegalArgumentException("Il Permesso non esiste");
		}
		if(esiste) {
			throw new IllegalArgumentException("Il permesso già associato a questo gruppo");
		}else {
			trovaGruppo.getPermessi().add(nomePermesso);			
		}		
			
		gestioneGruppo.salvaSuFile();
	}
	
	
	public void dissociaPermessoGruppo(String nomeGruppo,String nomePermesso,GestioneGruppo gestioneGruppo) {
		Gruppo trovaGruppo=gestioneGruppo.cercaGruppoPerNome(nomeGruppo);
		Permesso trovaPermesso=cercaPermessoPerNome(nomePermesso);
		boolean esiste=false;
		
		
		for(String g:trovaGruppo.getPermessi()) {
			 if(g.equalsIgnoreCase(nomePermesso)) {
				 esiste=true;
				 break;
			 }
		 }
		
		if(trovaGruppo==null) {
			throw new IllegalArgumentException("Il Gruppo non esiste");
		}
		if(trovaPermesso==null) {
			throw new IllegalArgumentException("Il Permesso non esiste");
		}
		if(!esiste) {
			throw new IllegalArgumentException("Il permesso non è associato a questo gruppo");
		}else {
			trovaGruppo.getPermessi().remove(nomePermesso);
		}		
		gestioneGruppo.salvaSuFile();
	}
	

	 public void associaPermessoRuolo(String nomeRuolo,String nomePermesso,GestioneRuolo gestioneRuolo) {
		 Ruolo trovaRuolo=gestioneRuolo.cercaRuoloPerNome(nomeRuolo);
		 Permesso trovaPermesso=cercaPermessoPerNome(nomePermesso);
		 boolean esiste=false;
		
		 for(String p:trovaRuolo.getPermessi()) {
			 if(p.equalsIgnoreCase(nomePermesso)) {
				 esiste=true;
				 break;
			 }
		 }
		 
		 if(trovaRuolo==null) {
			 throw new IllegalArgumentException("Il Ruolo non esiste");
		 }
		 if(trovaPermesso==null) {
			 throw new IllegalArgumentException("Il permesso non esiste");
		 }
		 if(esiste) {
			 throw new IllegalArgumentException("Permesso già presente per questo ruolo");
		 }else {
			trovaRuolo.getPermessi().add(nomePermesso);
		 }
		 gestioneRuolo.salvaSuFile();
	 }
	 
	 public void dissociaPermessoRuolo(String nomeRuolo,String nomePermesso,GestioneRuolo gestioneRuolo) {
		 Ruolo trovaRuolo=gestioneRuolo.cercaRuoloPerNome(nomeRuolo);
		 Permesso trovaPermesso=cercaPermessoPerNome(nomePermesso);
		 boolean esiste=false;
		 
		 for(String p:trovaRuolo.getPermessi()) {
			 if(p.equalsIgnoreCase(nomePermesso)) {
				 esiste=true;
				 break;
			 }
		 }
		 
		 if(trovaRuolo==null) {
			 throw new IllegalArgumentException("Il Ruolo non esiste");
		 }
		 if(trovaPermesso==null) {
			 throw new IllegalArgumentException("Il permesso non esiste");
		 }
		 if(!esiste) {
			 throw new IllegalArgumentException("Il permesso non è associato a questo ruolo");
		 }else {
			trovaRuolo.getPermessi().remove(nomePermesso);
		 }
		 gestioneRuolo.salvaSuFile();
	 }
	
	 
	
	public void salvaSuFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filePath3))) {
            oos.writeObject(listaPermessi);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio su file: " + e.getMessage());
        }
    }
 
	private List<Permesso> caricaDaFile() {
        File file = new File(filePath3);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Permesso>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Errore durante il caricamento: " + e.getMessage());
            return new ArrayList<>();
        }
	}
 
}
