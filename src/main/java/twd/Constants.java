package twd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {

	
	public static final String TWD_CSV_FILE_PATH = getEnvOrElse("WS_FOLDER", "/src/main/resources/twd.csv", "C:\\Users\\Didier\\eclipse-workspace\\ws_2020-03\\demo\\src\\main\\resources\\twd.csv");

	public static List<Perso> persos = Arrays.asList(
			new Perso("Glenn cogneur", 5257, 8626, Profil.DEFENSEUR, "Michonne", "Daryl", "Daryl eclaireur", "Guerrier", "Chasseur", "eclaireur", "brutalite", "riposte", "peau dure"),
			new Perso("Daryl eclaireur", 6739, 7343, Profil.ATTAQUANT, "Michonne", "Daryl", "Guerrier", "Chasseur", "peau dure", "impitoyable", "riposte", "chanceux"),
			new Perso("Daryl", 5985, 6472, Profil.ATTAQUANT, "Michonne", "Abraham", "Daryl eclaireur", "Soldat", "Guerrier", "Chasseur", "Tireur", "adresse au tir", "riposte", "impitoyable", "position defensive"),
			new Perso("Michonne", 6571, 7371, Profil.EQUILIBRE_ATTAQUE, "Abraham", "Daryl", "Daryl eclaireur", "Soldat", "Chasseur", "Eclaireur", "esquive", "riposte", "brutalite", "enchainement"),
			new Perso("Negan", 5257, 8626, Profil.DEFENSEUR, "Michonne", "Daryl", "Daryl eclaireur", "Glenn cogneur", "Cogneur", "Guerrier", "Chasseur", "eclaireur", "brutalite", "riposte", "peau dure", "frappe puissante"),
			new Perso("Rick", 8029, 5510, Profil.EQUILIBRE, "Abraham", "Daryl", "Tara", "Soldat", "Chasseur", "Tireur", "vengeance", "adresse au tir", "chanceux", "riposte"),
			new Perso("Abraham", 4546, 7122, Profil.EQUILIBRE, "Michonne", "Daryl", "Rick", "Tara", "Guerrier", "Chasseur", "Tireur", "Adresse au tir", "Vengeance", "Position Defensive", "Vigilant"),
			new Perso("Tara", 6188, 6006, Profil.EQUILIBRE, "Rick", "Abraham", "Guerrier", "Tireur", "adresse au tir", "vigilant", "position defensive", "chanceux"),
			new Perso("Carl", 5650, 4259, Profil.EQUILIBRE, "Rick", "Abraham", "Guerrier", "Tireur", "adresse au tir", "chanceux", "tir infaillible", "vengeance")
		);

	public static Set<String> attributs = new HashSet<String>(
			Arrays.asList(
			"peau dure",
			"adresse au tir",
			"vengeance",
			"position defensive",
			"tir infaillible",
			"riposte",
			"entre les balles",
			"esquive",
			"brutalite",
			"enchainement",
			"frappe puissante",
			"chanceux",
			"vigilant",
			"impitoyable"
			)
		);
	
	public static Set<String> specialites = new HashSet<String>(
			Arrays.asList(
			"soldat",
			"eclaireur",
			"guerrier",
			"cogneur",
			"chasseur",
			"tireur"
			)
		);
	
	public static Set<String> heros = new HashSet<String>(
			Arrays.asList(
			"daryl",
			"abraham",
			"michonne",
			"rosita",
			"jesus",
			"morgan",
			"negan",
			"glenn",
			"glenn cogneur",
			"ezekiel",
			"gouverneur",
			"maggie",
			"sasha",
			"tara",
			"rick",
			"carol",
			"carl",
			"daryl eclaireur",
			"rick eclaireur",
			"maggie tireur",
			"alpha",
			"glen cogneur",
			"dwight",
			"aaron",
			"rufus",
			"eugene",
			"morgan tireur",
			"beta",
			"merle",
			"jerry",
			"gabriel"
			)
		);
	
	public static Set<String> insignesEffet = new HashSet<String>(
			Arrays.asList(
			"degats",
			"sante",
			"degats critiques", 
			"coups critiques", 
			"reduction degats"
			)
		);

	
	public static Set<String> insignesSet = new HashSet<String>(
			Arrays.asList(
			"a",
			"b",
			"c",
			"d",
			"e",
			"f"
			)
		);
	
	public static List<Insigne> loadInsignes() throws FileNotFoundException, IOException {
		List<Insigne> insignes = new ArrayList<>();
		try (BufferedReader stream = new BufferedReader(new FileReader(TWD_CSV_FILE_PATH))) {
			
			String str = stream.readLine();
			while (str != null && !str.trim().isEmpty()) {
				String[] parts = str.split(";");
				insignes.add(new Insigne(Integer.parseInt(parts[0]),
						parts[1].toLowerCase(),
						Double.valueOf(parts[2].replace(',', '.')),
						Integer.parseInt(parts[3]),
						Integer.parseInt(parts[4]),
						parts[5].toLowerCase(),
						parts.length > 6 ? parts[6].toLowerCase() : null,
						parts.length > 7 ? Double.valueOf(parts[7].replace(',', '.')) : 0,
						false
						));
				str = stream.readLine();
			}
		}
		catch (Exception exc) {
			System.err.println("Erreur en parsant les insignes, ligne " + (insignes.size() + 1));
			exc.printStackTrace();
		}
		return insignes;
	}
	
	public static String getEnvOrElse(String envPropName, String defaultValue) {
		return getEnvOrElse(envPropName, "", defaultValue);
	}

	public static String getEnvOrElse(String envPropName, String envSuffix, String defaultValue) {
		String envValue = System.getenv(envPropName);
		return envValue != null && !envValue.isEmpty() ? envValue + envSuffix : defaultValue;
	}

}
