package gestioneAuto;

import java.io.Serializable;
import java.util.Objects;

public class Utente implements Serializable{
	
	private String nome;
	private String cognome;
	private int età;
	private String codiceFiscale;	
	private String ruolo;
	private String username;
	private String password;
	
	public Utente(String nome, String cognome, int età, String codiceFiscale,String username,String password) {
		this.nome = nome;
		this.cognome = cognome;
		this.età = età;
		this.codiceFiscale = codiceFiscale;
		this.ruolo="user";
		this.username=username;
		this.password=password;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public int getEtà() {
		return età;
	}
	public void setEtà(int età) {
		this.età = età;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public String toString() {
		return "Utente [nome=" + nome + ", cognome=" + cognome + ", età=" + età + ", codiceFiscale=" + codiceFiscale
				+ ", ruolo=" + ruolo + ", username=" + username + "]";
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && Objects.equals(cognome, other.cognome)
				&& età == other.età && Objects.equals(nome, other.nome) && Objects.equals(password, other.password)
				&& Objects.equals(ruolo, other.ruolo) && Objects.equals(username, other.username);
	}

}
