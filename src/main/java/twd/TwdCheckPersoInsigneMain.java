package twd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static twd.Constants.attributs;
import static twd.Constants.heros;
import static twd.Constants.specialites;
import static twd.Constants.insignesEffet;
import static twd.Constants.insignesSet;

public class TwdCheckPersoInsigneMain {

	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		checkInsignes(Constants.loadInsignes()); 
		checkPersos(Constants.persos);

	}
	
	public static void checkInsignes(List<Insigne> insignes) {
		int erreur = 0;
		Set<Integer> ids = new HashSet<>();
		for (Insigne insigne : insignes) {
			// extra
			if (insigne.extra != null
					&& !attributs.contains(insigne.extra)
					&& !heros.contains(insigne.extra)
					&& !specialites.contains(insigne.extra)
				) {
				erreur++;
				System.err.println(String.format("Erreur sur l'insigne ligne %s, extra %s non trouve (%s)", insigne.csvLine, insigne.extra, insigne));
			}			
			// set
			if (!insignesSet.contains(insigne.set)) {
				erreur++;
				System.err.println(String.format("Erreur sur l'insigne ligne %s, set %s non trouve (%s)", insigne.csvLine, insigne.set, insigne));
			}			
			// effet
			if (!insignesEffet.contains(insigne.effet)) {
				erreur++;
				System.err.println(String.format("Erreur sur l'insigne ligne %s, effet %s non trouve (%s)", insigne.csvLine, insigne.effet, insigne));
			}
			// id
			if (ids.contains(insigne.csvLine)) {
				erreur++;
				System.err.println(String.format("Erreur sur l'insigne ligne %s, id en doublon (%s)", insigne.csvLine, insigne));
			}
			ids.add(insigne.csvLine);
		}
		if (erreur == 0) {
			System.out.println("Aucune erreur trouvee durant l'analyse des insignes");
		}
	}
	
	public static void checkPersos(List<Perso> persos) {
		int erreur = 0;
		for (Perso perso : persos) {
			for (String equipier : perso.equipiers) {
				if (	!attributs.contains(equipier)
						&& !heros.contains(equipier)
						&& !specialites.contains(equipier)
					) {
					erreur++;
					System.err.println(String.format("Erreur sur le perso %s, propriete %s non trouvee", perso.nom, equipier));
				}
			}
		}
		if (erreur == 0) {
			System.out.println("Aucune erreur trouvee durant l'analyse des personnages");
		}
	}
	
}
