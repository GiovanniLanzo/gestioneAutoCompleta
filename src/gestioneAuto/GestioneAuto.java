package gestioneAuto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class GestioneAuto implements Serializable {
	
	 //zxzx 
	private List<Auto> autoList;
	private String filePath;
	
	public GestioneAuto(String file) {
		this.filePath=file;
		this.autoList=caricaDaFile();
	}
	
	public void aggiungiAuto(String targa, String marca, String modello, int annoImmatricolazione, Double chilometraggio, Double prezzo){
        Auto nuova = new Auto(targa, marca, modello, annoImmatricolazione, chilometraggio, prezzo);
        if (autoList.contains(nuova)){
            throw new IllegalArgumentException("Auto con targa " + targa + " già presente nella lista.");
        }
        autoList.add(nuova);
        salvaSuFile();
    }

	
	public void rimuoviAuto(String targa) {
		Auto daRimuovere=null;
		for(Auto auto:autoList) {
			if(auto.getTarga().equalsIgnoreCase(targa)) {
				daRimuovere=auto;
				break;
			}
		}
		if(daRimuovere!=null) {
			autoList.remove(daRimuovere);
		}else {
			throw new IllegalArgumentException("Auto con targa "+ targa+" non è presente in lista");
		}
		  salvaSuFile();
	}
	
	public void modificaAuto(String targa, String nuovaMarca, String nuovoModello,int nuovoAnnoImmatricolazione,double nuovoChilometraggio,double nuovoPrezzo) {
		boolean trovata = false; 
		for(Auto auto:autoList) {
			if(auto.getTarga().equals(targa)) {
				auto.setMarca(nuovaMarca);
				auto.setModello(nuovoModello);
				auto.setAnnoImmatricolazione(nuovoAnnoImmatricolazione);
				auto.setChilometraggio(nuovoChilometraggio);
				auto.setPrezzo(nuovoPrezzo);
				trovata=true;
				break;
			}
		}
		if (!trovata) {
			throw new IllegalArgumentException("Auto con targa "+ targa +" non è presente in lista");
		}
		  salvaSuFile();
	}
	
	public void stampaTutteLeAuto() {
		for(Auto auto:autoList) {
			System.out.println(auto);
		}
		
	}
	
	public Auto cercaAutoPerTarga(String targa) {
		Auto trovata=null;
		for(Auto auto:autoList) {
			if(auto.getTarga().equals(targa)) {
				trovata=auto;
				break;
			}
	}
		return trovata;
	}
	
	public List<Auto> cercaAutoPerUtente(String codiceFiscale) {
		List<Auto> risultato=new ArrayList<>();
		for(Auto a:autoList) {
			if(a.getCodiceFiscale().equalsIgnoreCase(codiceFiscale)) {
				risultato.add(a);
			}
		}
		return risultato;
	}
	
	public void associaAutoUtente(String targa,String codiceFiscale,GestioneUtente gestioneUtente) {
		Auto a=cercaAutoPerTarga(targa);
		if(a==null) {
			throw new IllegalArgumentException("Nessuna auto con targa "+ targa +" è presente in lista");
		}
		Utente u=gestioneUtente.cercaUtentePerCodiceFiscale(codiceFiscale);
		if(u==null) {
			throw new IllegalArgumentException("Nessun utente con codice fiscale "+ codiceFiscale +" è presente in lista");
		}else {
			a.setCodiceFiscale(codiceFiscale);			
		}
		salvaSuFile();
	}
	
	
	
	public List<Auto> filtroMarca(String marca) {
		List<Auto> risultato=new ArrayList<>();
		for(Auto auto:autoList) {
			if(auto.getMarca().equalsIgnoreCase(marca)) {
				risultato.add(auto);
			}
		}
		return risultato;
	}
	
	
	public List<Auto> trovaAutoSopraKm(double chilometraggio) {
		List<Auto> risultato=new ArrayList<>();
		for(Auto auto:autoList) {
			if(auto.getChilometraggio()>chilometraggio) {
				risultato.add(auto);
			}
		}
		return risultato;
	}
	
	
	public double calcolaChilometraggioMedio() {
		double somma=0;
		double conta=0;
		double media=somma/conta;
		for(Auto auto:autoList) {
			somma+=auto.getChilometraggio();
			conta++;
		}
		return media;
	}
	
	
	public List<Auto> ordinaPerPrezzoMax() {
		if (autoList.isEmpty()) {
			throw new IllegalArgumentException("Parco auto vuoto");
		}
		List<Auto> listaCopia = new ArrayList<>(autoList);
		List<Auto> autoOrdinate=new ArrayList<>();
		while(listaCopia.isEmpty()) {
			autoOrdinate.add(trovaMaxPrezzo(listaCopia));
			listaCopia.remove(trovaMaxPrezzo(listaCopia));
		}
		return autoOrdinate;
	}
	
	
	public Auto trovaMaxPrezzo(List<Auto> listaCopia) {
		if (listaCopia.isEmpty()) {
			return null;
		}
		double max=listaCopia.get(0).getPrezzo();
		Auto maxAuto=null;
		for(Auto a:listaCopia) {
			if(a.getPrezzo()>max) {
				max=a.getPrezzo();
				maxAuto=a;
			}
		}
		return maxAuto;
	}
	
	
	public List<Auto> ordinaPerPrezzoMin() {
		if (autoList.isEmpty()) {
			throw new IllegalArgumentException("Parco auto vuoto");
		}
		List<Auto> listaCopia=new ArrayList<>(autoList);
		List<Auto> autoOrdinate=new ArrayList<>();
		while(listaCopia.isEmpty()) {
			autoOrdinate.add(trovaMinPrezzo(listaCopia));
			listaCopia.remove(trovaMinPrezzo(listaCopia));
		}
		return autoOrdinate;
	}
	
	
	public Auto trovaMinPrezzo(List<Auto> listaCopia) {
		if (listaCopia.isEmpty()) {
			return null;
		}
		double min=listaCopia.get(0).getPrezzo();
		Auto minAuto=null;
		for(Auto a:listaCopia) {
			if(a.getPrezzo()>min) {
				min=a.getPrezzo();
				minAuto=a;
			}
		}
		return minAuto;
	}	
	
	
	public List<Auto> ordinaPerChilometriMax() {
		if (autoList.isEmpty()) {
			throw new IllegalArgumentException("Parco auto vuoto");
		}
		List<Auto> listaCopia=new ArrayList<>(autoList);
		List<Auto> autoOrdinate=new ArrayList<>();
		while(listaCopia.isEmpty()) {
			autoOrdinate.add(trovaMaxPrezzo(listaCopia));
			listaCopia.remove(trovaMaxPrezzo(listaCopia));
		}
		return autoOrdinate;
	}
	
	
	public Auto trovaMaxChilometri(List<Auto> listaCopia) {
		if (listaCopia.isEmpty()) {
			return null;
		}
		double max=listaCopia.get(0).getChilometraggio();
		Auto maxAuto=null;
		for(Auto a:listaCopia) {
			if(a.getChilometraggio()>max) {
				max=a.getChilometraggio();
				maxAuto=a;
			}
		}
		return maxAuto;
	}
	
	
	public List<Auto> ordinaPerChilometriMin() {
		if (autoList.isEmpty()) {
			throw new IllegalArgumentException("Parco auto vuoto");
		}
		List<Auto> listaCopia=new ArrayList<>(autoList);
		List<Auto> autoOrdinate=new ArrayList<>();
		while(listaCopia.isEmpty()) {
			autoOrdinate.add(trovaMaxPrezzo(listaCopia));
			listaCopia.remove(trovaMaxPrezzo(listaCopia));
		}
		return autoOrdinate;
	}
	
	
	public Auto trovaMinChilometri(List<Auto> listaCopia) {
		if (listaCopia.isEmpty()) {
			return null;
		}
		double max=listaCopia.get(0).getChilometraggio();
		Auto maxAuto=null;
		for(Auto a:listaCopia) {
			if(a.getChilometraggio()<max) {
				max=a.getChilometraggio();
				maxAuto=a;
			}
		}
		return maxAuto;
	}
	
	
	public List<Auto> autoRecenti(int anni) {
		List<Auto> risultato=new ArrayList<>();
		int annoCorrente= Calendar.getInstance().get(Calendar.YEAR);
		for(Auto auto:autoList) {
			int differenza =annoCorrente-auto.getAnnoImmatricolazione();
			if(differenza <= anni) {
				risultato.add(auto);
			}
		}
		return risultato;
	}
	

	public Auto trovaAutoPiuVecchia() {
		Auto vecchia=null;
		for(Auto a:autoList) {
			if(vecchia.getAnnoImmatricolazione()>a.getAnnoImmatricolazione()) {
				vecchia=a;
			}
		}
		return vecchia;
	}

	public Auto trovaAutoPiuCara() {
		Auto cara=null;
		for(Auto a:autoList) {
			if(cara.getPrezzo()<a.getPrezzo()) {
				cara=a;
			}
		}
		return cara;
	}
    
	
	
	
	public void salvaSuFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filePath))) {
            oos.writeObject(autoList);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il salvataggio su file: " + e.getMessage());
        }
    }
   
    
	  private List<Auto> caricaDaFile() {
	        File file = new File(filePath);
	        if (!file.exists()) return new ArrayList<>();
	        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
	            return (List<Auto>) in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
	            System.out.println("Errore durante il caricamento: " + e.getMessage());
	            return new ArrayList<>();
	        }
	  }
}
