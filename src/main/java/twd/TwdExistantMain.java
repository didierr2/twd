package twd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TwdExistantMain {

	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		List<Insigne> insignes = Constants.loadInsignes(); 
		loadPersosExistants(insignes, true);
	}
	
	
	public static List<Perso> loadPersosExistants(List<Insigne> insignes, boolean log) throws FileNotFoundException, IOException {
		List<Perso> persos = Constants.persos;
		for (Perso perso : persos) {
			if ("Michonne".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 3,15,2,4,21,10);
			if ("Daryl".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 49,6,14,35,25,11);
			if ("Daryl eclaireur".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 16,55,23,33,12,22);
			if ("Abraham".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 9,80,8,48,26,5);
			if ("Rick".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 17,50,7,53,81,52);
			if ("Tara".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 20,51,13,54,1,82);
			if ("Carl".equalsIgnoreCase(perso.nom)) perso.equipe(insignes, 30,83,27,36,84,37);
			
			int total = perso.equipement.total();
			if (log) {
				System.out.println(perso.nom
				 + " > " + total
				 + " ( " + (int)perso.equipement.totalAttaque
				 + " / " + (int)perso.equipement.totalDefense
				 + ") : " + perso.equipement);
			}
		}
		return persos;
	}
}
