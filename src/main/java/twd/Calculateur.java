package twd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;


public class Calculateur {

	
	public static long cpt = 0;
	public long unPourcent = 0;
	public Instant debut = null;
	boolean estimation = false;
	

	/**
	 * Cette méthode calcule pour chaque perso l'équipement le plus adapté
	 * Les persos sont pris dans l'ordre
	 */
	public void calcule (List<Insigne> insignes, List<Perso> persos) throws FileNotFoundException, IOException {
		
		long nbCombi = nbCombinaisons(insignes, persos);
		unPourcent = (long) (nbCombi / 100l);

		System.out.println("Nombre de combinaisons à tester : " + nbCombi);
		
		debut = Instant.now();
		List<Perso> existants = TwdExistantMain.loadPersosExistants(insignes, false);
		Map<String, Perso> mpExistants = new HashMap<>();
		for (Perso p : existants) {
			mpExistants.put(p.nom, p);
		}
		
		StringBuilder resultat = new StringBuilder();
		for (Perso perso : persos) {
			Equipement equip = recursifCalcule(1, insignes, new Equipement(perso));
			
			// On indique tous les insignes equipes du perso
			for (Insigne insigne : equip.insignes.values()) {
				insignes.forEach(action -> {
					if (insigne.csvLine == action.csvLine) {
						action.equipe = true;
					}
				});
			}
			
			Perso existant = mpExistants.get(perso.nom);
			int ecartAttaque = existant != null ? (int)(equip.totalAttaque - existant.equipement.totalAttaque) : 0; 
			int ecartDefense = existant != null ? (int)(equip.totalDefense - existant.equipement.totalDefense) : 0; 
			int totalNew = (int)equip.total();
			int totalExistant = (int)existant.equipement.total();
			
			resultat.append(perso.nom)
				.append(" > ")
				.append(totalNew + " / " + totalExistant)
				.append(" ( ")
				.append((int)equip.totalAttaque + " / " + (int)existant.equipement.totalAttaque)
				.append(" / ")
				.append((int)equip.totalDefense + " / " + (int)existant.equipement.totalDefense)
				.append(") : ")
				.append(equip)
				.append("\n");

			System.out.println(perso.nom 
					+ " > "
					+ totalNew
					+ " (" + (totalNew - totalExistant) + ")"
					+ " : "
					+ ecartAttaque
					+ " / "
					+ ecartDefense);
					
		}
		Instant fin = Instant.now();
		System.out.println(resultat);
		System.out.println("Démarré à : " + debut);
		System.out.println("Terminé à : " + fin);
		System.out.println("Nombre de combinaisons testees : " + cpt);
		
	}
	
	/**
	 * Méthode récursive de calcul des insignes pour l'équipement d'un perso donné
	 */
	public Equipement recursifCalcule(int emplacement, List<Insigne> insignes, Equipement eq) {
		Equipement bestEq = eq;
		cpt++;
		if (cpt == 100000) {
			estimeDateFin();
		}
		if (cpt % unPourcent == 0) {
			System.out.println(Instant.now() + " : " + (cpt / unPourcent) + " % - " + cpt);
		}
		
		// Condition d'arret
		if (emplacement <= 6) {
			
			for (Insigne insigne : insignes) {
				if (eq.isEquipable(insigne, emplacement, eq.getPerso().getProfil())) {
					Equipement tmp = (Equipement) SerializationUtils.clone(eq);
					tmp.equipe(insigne);
					
					Equipement res = recursifCalcule(emplacement + 1, insignes, tmp);
//					System.out.println(res.total() + " : " + res);
					if (res.total() > bestEq.total()) {
						bestEq = (Equipement) SerializationUtils.clone(res);
					}
					tmp.desequipe(insigne);
				}
			}
			
			// Cas ou aucun insigne n'est equipable
			if (!bestEq.insignes.containsKey(emplacement)) {
				Equipement tmp = (Equipement) SerializationUtils.clone(eq);
				Equipement res = recursifCalcule(emplacement + 1, insignes, tmp);
				// System.out.println("[emplacement " + emplacement + " manquant]" + res.total() + " : " + res);
				if (res.total() > bestEq.total()) {
					bestEq = (Equipement) SerializationUtils.clone(res);
				}
			}
		}
		return bestEq;
	}
	
	/**
	 * Estime l'heure de fin en se basant sur le nombre de combinaisons a couvrir
	 */
	public void estimeDateFin() {
		Instant million = Instant.now();
		double nbMillions = (unPourcent * 100) / 100000;
		long secPourUnMillion = million.getEpochSecond() - debut.getEpochSecond();
		Instant finEstimee = Instant.ofEpochSecond((long)(debut.getEpochSecond() + (nbMillions * secPourUnMillion)));
		System.out.println("Date estimée de fin : " + finEstimee);
	}
	
	/**
	 * Calcule le nombre de combinaisons
	 */
	public long nbCombinaisons(List<Insigne> insignes, List<Perso> persos) {
		int i1=0, i2=0, i3=0, i4=0, i5=0, i6=0;
		for (Insigne insigne : insignes) {
			switch (insigne.emplacement) {
				case 1 : i1++; break;
				case 2 : i2++; break;
				case 3 : i3++; break;
				case 4 : i4++; break;
				case 5 : i5++; break;
				case 6 : i6++; break;
				default : break;
			}
		}
		
		long total = 0;
		for (int i=0; i<persos.size(); i++) {
			total += (i1-i) * (i2-i) * (i3-i) * (i4-i) * (i5-i) * (i6-i);
		}
		
		return total / 4; 
	}
	
}
