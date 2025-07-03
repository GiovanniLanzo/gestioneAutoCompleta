package gestioneAuto;

import java.io.Serializable;

public class Auto implements Serializable{
	private String targa;
	private String marca;
	private String modello;
	private int annoImmatricolazione;
	private double chilometraggio;
	private double prezzo;
	private String codiceFiscale;
	
	
	public Auto(String targa, String marca, String modello,int annoImmatricolazione,double chilometraggio,double prezzo) {
		this.targa=targa;
		this.marca=marca;
		this.modello=modello;
		this.annoImmatricolazione=annoImmatricolazione;
		this.chilometraggio=chilometraggio;
		this.prezzo=prezzo;
		this.codiceFiscale=null;
	}	
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public int getAnnoImmatricolazione() {
		return annoImmatricolazione;
	}
	public void setAnnoImmatricolazione(int annoImmatricolazione) {
		this.annoImmatricolazione = annoImmatricolazione;
	}
	public double getChilometraggio() {
		return chilometraggio;
	}
	public void setChilometraggio(double chilometraggio) {
		this.chilometraggio = chilometraggio;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public String getTarga() {
		return targa;
	}
    public void setTarga(String targa) {
        this.targa = targa;
    }
	
    
    public String getCodiceFiscale() {
    	return codiceFiscale;
    }
    
    public void setCodiceFiscale(String codiceFiscale) {
    	this.codiceFiscale = codiceFiscale;
    }
    
    
	

	@Override
	public String toString() {
		return "Auto [targa=" + targa + ", marca=" + marca + ", modello=" + modello + ", annoImmatricolazione="
				+ annoImmatricolazione + ", chilometraggio=" + chilometraggio + ", prezzo=" + prezzo
				+ ", codiceFiscale=" + codiceFiscale + "]";
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Auto)) return false;
		Auto auto = (Auto) o;
		boolean flag = targa.equals(auto.targa);
		return flag;
	}
	
	
}
