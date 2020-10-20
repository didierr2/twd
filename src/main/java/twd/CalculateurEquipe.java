package twd;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.SerializationUtils;


public class CalculateurEquipe {

	public static long cpt = 0;
	
	
	public void calcule (List<Insigne> insignes, List<Perso> persos) {
		StringBuilder resultat = new StringBuilder();

		Instant debut = Instant.now();
		List<Perso> persosEquipes = recursifCalcule(1, 0, insignes, persos);
		Instant fin = Instant.now();
		
		int totalEquipe = 0;
		for (Perso perso : persosEquipes) {
			int totalPerso = perso.equipement.total();
			totalEquipe += totalPerso;
			resultat.append(perso.nom)
				.append(" > ")
				.append(totalPerso)
				.append(" : ")
				.append(perso.equipement)
				.append("\n");
		}
		System.out.println(totalEquipe + " points : \n" + resultat);
		System.out.println("Combinaisons testees : " + cpt);
		System.out.println("Démarré à : " + debut);
		System.out.println("Terminé à : " + fin);
	}
	
	
	public List<Perso> recursifCalcule(int emplacement, int numPerso, List<Insigne> insignes, List<Perso> persos) {
		cpt++;
		if (cpt % 100000 == 0) {
			System.out.println(cpt);
		}
		
		List<Perso> bestEquipe = persos;
		int totalBestEquipe = 0;
		
		// Condition d'arret : on a atteint l'emplacement max
		if (emplacement <= 6) {
//			System.out.println(String.format("E%s P%s/%s", emplacement, numPerso, persos.size()));
			
			
			Perso persoAEquiper = persos.get(numPerso);
			
			for (Insigne insigne : insignes) {
				Equipement eq = persoAEquiper.equipement;
				if (eq.isEquipable(insigne, emplacement)) {
//					System.out.println(String.format("%s - E%s - I%s", persoAEquiper.nom, emplacement, insigne.csvLine));
					List<Perso> tmp = clone(persos);
					tmp.get(numPerso).equipement.equipe(insigne);
					
					List<Perso> res = null;
					// S'il rest des persos, on rappelle pour le personnage suivant
					if (numPerso < persos.size() - 1) {
						res = recursifCalcule(emplacement, numPerso + 1, insignes, tmp);
					}
					// Sinon on passe a l'emplacement suivant en reprenant au perso 0
					else {
						res = recursifCalcule(emplacement + 1, 0, insignes, tmp);
					}
//						Equipement res = recursifCalcule(emplacement + 1, insignes, tmp);
//					System.out.println(res.total() + " : " + res);
					
					// Calcul des totaux
					int totalEquipe = totalEquipe(res);
					if (totalBestEquipe == 0) {
						totalBestEquipe = totalEquipe(bestEquipe);
					}
					if (totalEquipe > totalBestEquipe) {
						bestEquipe = res;
						totalBestEquipe = totalEquipe;
					}
					tmp.get(numPerso).equipement.desequipe(insigne);
				}
			}
				
			// Cas ou aucun insigne n'est equipable, on fait le recursif en passant sur le perso suivant
			if (!bestEquipe.get(numPerso).equipement.insignes.containsKey(emplacement)) {
				List<Perso> tmp = clone(persos);
				
				List<Perso> res = null;
				// S'il rest des persos, on rappelle pour le personnage suivant
				if (numPerso < persos.size() - 1) {
					res = recursifCalcule(emplacement, numPerso + 1, insignes, tmp);
				}
				// Sinon on passe a l'emplacement suivant en reprenant au perso 0
				else {
					res = recursifCalcule(emplacement + 1, 0, insignes, tmp);
				}

				// Calcul des totaux
				int totalEquipe = totalEquipe(res);
				if (totalBestEquipe == 0) {
					totalBestEquipe = totalEquipe(bestEquipe);
				}
				if (totalEquipe > totalBestEquipe) {
					bestEquipe = res;
					totalBestEquipe = totalEquipe;
				}
			}
		}
		else {
//			System.out.println(String.format("ARRET - E%s P%s/%s", emplacement, numPerso, persos.size()));

			// On est dans la condition d'arret, donc on log la combinaison testee
//			log(persos);
		}

		return bestEquipe;
		
	}
	
	public void log(List<Perso> persos) {
		StringBuilder resultat = new StringBuilder();
		for (Perso perso : persos) {
			resultat.append(perso.nom).append(" - ");
			for (Entry<Integer, Insigne> entry : perso.equipement.insignes.entrySet()) {
				resultat
				.append(entry.getValue().csvLine)
				.append(".");
			}
			resultat.append(" / ");
		}
		System.out.println(resultat);
	}
	
	
	public int totalEquipe (List<Perso> lst) {
		int total = 0;
		for (Perso perso : lst) {
			total += perso.equipement.total();
		}
		return total;
	}
	
	public <T extends Serializable> List<T> clone(List<T> lst) {
		List<T> lstCloned = new ArrayList<>();
		lst.forEach(elem -> {
			lstCloned.add((T) SerializationUtils.clone(elem));
		});
		return lstCloned;
	}
	
	
}
