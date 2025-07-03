package gestioneAuto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gruppo implements Serializable{
	
	private String nome;
	List<String> permessi;

	public Gruppo(String nome) {
		this.nome = nome;
		this.permessi=new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getPermessi() {
		return permessi;
	}

	public void setPermessi(List<String> permessi) {
		this.permessi = permessi;
	}

	@Override
	public String toString() {
		return "Gruppo [nome=" + nome + ", permessi=" + permessi + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gruppo other = (Gruppo) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(permessi, other.permessi);
	}

}
