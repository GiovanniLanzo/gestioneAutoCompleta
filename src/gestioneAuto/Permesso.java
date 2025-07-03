package gestioneAuto;

import java.io.Serializable;
import java.util.Objects;

public class Permesso implements Serializable{
	
	private String nome;

	public Permesso(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permesso other = (Permesso) obj;
		return Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Permesso [nome=" + nome + "]";
	}
	
	

}
