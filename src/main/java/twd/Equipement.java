package twd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Equipement implements Serializable {

	public final static double PONDERATION_ATTAQUE_DEFENSE = 1.3;
	public final static int NB_MAX_EFFET = 3;
	public final static int SET_BONUS_NUMBER = 4;
	public final static double SET_BONUS_VALUE = 1.2;
	public final static int NB_MAX_OR_PAR_PERSO = 6;
	
	double totalAttaque = 0;
	double totalDefense = 0;
	double totalRd = 0;
	double totalCc = 0;
	double totalDc = 0;

	private static final long serialVersionUID = 1L;
	public final Perso perso;
	public Map<Integer, Insigne> insignes = new HashMap<>();
	
	public void equipe(Insigne insigne) {
		if (insigne.equipe) throw new IllegalStateException("L'insigne est deja equipe : " + insigne);
		if (insignes.containsKey(insigne.emplacement)) throw new IllegalStateException("Un insigne de meme emplacement est deja equipe : " + insigne);
		insigne.equipe = true;
		insignes.put(insigne.emplacement, insigne);
	}

	public void desequipe(Insigne insigne) {
		if (!insigne.equipe) throw new IllegalStateException("L'insigne n'est pas equipe : " + insigne);
		if (!insignes.containsKey(insigne.emplacement)) throw new IllegalStateException("Aucun insigne de meme emplacement est equipe : " + insigne);
		insigne.equipe = false;
		insignes.remove(insigne.emplacement, insigne);
	}
	
	/**
	 * Calcul le total de points d'un équipement donné
	 * @return
	 */
	public int total() {
		totalAttaque = perso.attaque;
		totalDefense = perso.defense;
		totalRd = perso.defense;
		totalCc = perso.attaque;
		totalDc = perso.attaque;
		Profil profil = perso.getProfil();
		
		for (Insigne insigne : insignes.values()) {
			switch (insigne.effet) {
			case "degats" :
				totalAttaque = addEffet(totalAttaque, insigne);
				break;
			case "sante" : 
				totalDefense = addEffet(totalDefense, insigne);
				break;
			case "degats critiques" : 
				totalDc = addEffet(totalDc, insigne);
				break;
			case "coups critiques" : 
				totalCc = addEffet(totalCc, insigne);
				break;
			case "reduction degats" : 
				totalRd = addEffet(totalRd, insigne);
				break;
			default : 
				throw new IllegalStateException("L'effet de l'insigne n'est pas reconnu : " + insigne);
			}
		}
		return (int)((totalAttaque * profil.pdegats) + (totalDefense * profil.psante) + (totalRd * profil.prd) + (totalDc * profil.pdc) + (totalCc * profil.pcc));
	}

	/**
	 * Ajoute l'effet et l'extra effet
	 */
	public double addEffet(double montant, Insigne insigne) {
		
		int nbSet = 0;
		// On compte le nombre d'insignes du meme set
		for(Insigne ins : insignes.values()) {
			if (ins.set.equals(insigne.set)) {
				nbSet++;
			}
		}
		double puissance = insigne.puissance;
		double extraPuissance = insigne.extraPuissance;
		if (nbSet >= SET_BONUS_NUMBER) {
			puissance *= SET_BONUS_VALUE;
			extraPuissance *= SET_BONUS_VALUE;
		}
		
		// Effet
		if (insigne.puissance <= 1) {
			montant = montant * (1 + puissance);
		}
		else {
			montant += puissance;
		}
		
		// Extra effet
		if (perso.equipiers.contains(insigne.extra)) {
			if (insigne.extraPuissance <= 1) {
				montant = montant * (1 + extraPuissance);
			}
			else {
				montant += extraPuissance;
			}
		}
		
		return montant;
	}


	public boolean isEquipable(Insigne insigne, int emplacement, Profil profil) {
		
		// On compte le max d'insignes et le nombre d'insignes or
		int nbOr = 0;
		int nbEffet = 0;
		for(Insigne ins : insignes.values()) {
			if (ins.effet.equals(insigne.effet)) {
				nbEffet++;
			}
			if (ins.etoiles == 5) {
				nbOr++;
			}
		}
		
		if (insigne.etoiles == 5) {
			nbOr++;
		}
		
		return !insigne.equipe 
				&& profil.accept(insigne)
				&& insigne.emplacement == emplacement
				&& nbEffet < NB_MAX_EFFET
				&& nbOr <= NB_MAX_OR_PAR_PERSO;
	}
	
	
	public String toString() {
		String sb = "";
		
		String bonus = "";
		
		for (int i=1; i<=6; i++) {
			Insigne ins = insignes.get(i);
			if (ins != null) {
				bonus += ins.set;
				sb += String.format("[(%s) %s-%s> %s %s %s*", ins.csvLine, ins.emplacement, ins.set, ins.puissance, ins.effet, ins.etoiles);
				if (ins.extra != null) {
					sb += String.format(" (%s %s)", ins.extraPuissance, ins.extra);
				}
				sb += "]";
			}
		}
		
		// Verifie s'il y a un bonus
		if (bonus.chars().filter(ch -> ch == 'a').count() >= SET_BONUS_NUMBER
			|| bonus.chars().filter(ch -> ch == 'b').count() >= SET_BONUS_NUMBER
			|| bonus.chars().filter(ch -> ch == 'c').count() >= SET_BONUS_NUMBER
			|| bonus.chars().filter(ch -> ch == 'd').count() >= SET_BONUS_NUMBER
			|| bonus.chars().filter(ch -> ch == 'e').count() >= SET_BONUS_NUMBER
			) {
			sb = "BONUS - " + sb;
		}
		
		return sb;
	}
	
	
}
