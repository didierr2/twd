package twd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class Perso implements Serializable {

	private static final long serialVersionUID = 1L;
	public String nom;
	public int attaque;
	public int defense;
	public List<String> equipiers = new ArrayList<>();
	public Profil profil;

	public Equipement equipement;
	
	public Perso(String nom, int attaque, int defense, Profil profil, String... equipiers) {
		this.nom = nom.toLowerCase();
		this.attaque = attaque;
		this.defense = defense;
		this.profil = profil;
		for (String eq : equipiers) {
			this.equipiers.add(eq.toLowerCase());
		}
		equipement = new Equipement(this);
	}
	
	
	public void equipe(List<Insigne> insignes, int e1, int e2, int e3, int e4, int e5, int e6) {
		equipement.insignes = new HashMap<>();
		for (Insigne insigne : insignes) {
			if (insigne.csvLine == e1) equipement.insignes.put(1, insigne);
			if (insigne.csvLine == e2) equipement.insignes.put(2, insigne);
			if (insigne.csvLine == e3) equipement.insignes.put(3, insigne);
			if (insigne.csvLine == e4) equipement.insignes.put(4, insigne);
			if (insigne.csvLine == e5) equipement.insignes.put(5, insigne);
			if (insigne.csvLine == e6) equipement.insignes.put(6, insigne);
		}
	}
	
	
}
