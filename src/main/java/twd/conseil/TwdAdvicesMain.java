package twd.conseil;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.list.TreeList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import twd.conseil.constant.SurvivorClass;
import twd.conseil.constant.Trait;
import twd.conseil.constant.Constants.ROWS_AND_CELLS;
import twd.conseil.rules.Rules;
import twd.conseil.rules.SurvivorRule;
import twd.conseil.rules.TeamRule;
import twd.conseil.util.CellUtils;
import twd.conseil.xls.AbstractWorkbookHandler;
import static twd.conseil.util.StringUtils.toStringSurvivants;

public class TwdAdvicesMain extends AbstractWorkbookHandler {

	// Liste des survivants
	static List<Survivor> survivors = null;
	final static String FILE_ADVICES = "conseils.txt";
	final static String FILE_RULES = "regles.txt";
	final static Logger log = LogManager.getLogger(TwdAdvicesMain.class);
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		try {
			startTwd(args);
		}
		catch (Throwable t) {
			log.error("Erreur remontée au main", t);
			System.out.println("ERREUR> Une erreur est survenue. Il faut envoyer le fichier " + args[0] + " et le fichier twd.log a didierr2 sur discord. Merci.");
		}

	}

	public static void startTwd(String[] args) throws FileNotFoundException, IOException, InterruptedException {

		// Check args
		if (args.length == 0 || args[0] == null) {
			System.err.println("Parametre d'execution manquant.");
			System.exit(-1);
		}
		
		// On charge les survivants depuis la feuille excel
		log.info("*** Chargement des survivants");
		new TwdAdvicesMain().loadSurvivalists(args[0]);

		// Charge toutes les regles
		log.info("*** Execution des regles");
		StringBuilder diagnostic = Rules.processRules(survivors);

		// Ecrit les règles dans un fichier
		log.info("*** Ecrit le fichier de description");
		boolean first = true;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_RULES))) {
			for (TeamRule rule : Rules.teamRules) {
				if (first) {
					first = false;
				} else {
					writer.write("\n\n");
				}
				log.debug("- [TEAM_RULE] " + rule.getClass().getSimpleName());
				writer.write("[" + rule.getClass().getSimpleName() + "]");
				writer.write("\n" + rule.description());
			}
			for (SurvivorRule rule : Rules.survivorRules) {
				log.debug("- [SURVIVOR_RULE] " + rule.getClass().getSimpleName());
				writer.write("\n\n[" + rule.getClass().getSimpleName() + "]");
				writer.write("\n" + rule.description());
			}    
			log.info("La description des règles est dans le fichier : " + FILE_RULES);
			System.out.println("La description des règles est dans le fichier : " + FILE_RULES);
		} 

		// Ecrit le diagnostic dans un fichier
		log.info("*** Ecrit le fichier de recommandations");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ADVICES))) {
			writer.write(diagnostic.toString());
			log.info("Les recommandations sont dans le fichier : " + FILE_ADVICES);
			System.out.println("Les recommandations sont dans le fichier : " + FILE_ADVICES);
		} 
	}
	

	public void loadSurvivalists(String filePath) throws FileNotFoundException, IOException {
		
		// Récupère les survivants
		readWorkbook(OPEN_MODE.READ_ONLY, filePath, ROWS_AND_CELLS.SHEET_DATA.value);

	}

	@Override
	protected void processSheet(Workbook workbook, Sheet data) {
		
		
		// On ne prend pas la premiere ligne (1) car ce sont les entetes
		// On parcourt toutes les lignes (2 à n), ce sont les survivants
		survivors = new TreeList<>();
		int iRaw = ROWS_AND_CELLS.ROW_FIRST_SURVIVANT.value;
		while (CellUtils.isCellPresent(data, iRaw, ROWS_AND_CELLS.COL_NAME.value)) {
			
			// LoadSurvivant
			survivors.add(loadSurvivor(data, iRaw));
			iRaw++;
		}
		log.info(String.format("%s survivants chargés : %s ", survivors.size(), toStringSurvivants(survivors)));
		System.out.println(String.format("%s survivants chargés : %s ", survivors.size(), toStringSurvivants(survivors)));
	}

	private Survivor loadSurvivor(Sheet data, int rawIndex) {
		String name = CellUtils.getCellAsTextValue(data, rawIndex, ROWS_AND_CELLS.COL_NAME.value);
		String className = CellUtils.getCellAsTextValue(data, rawIndex, ROWS_AND_CELLS.COL_CLASS_NAME.value);
		int level = CellUtils.getCellAsIntValue(data, rawIndex, ROWS_AND_CELLS.COL_LEVEL.value);
		int extraStars = CellUtils.getCellAsIntValue(data, rawIndex, ROWS_AND_CELLS.COL_EXTRA_STARS.value);
		int damageLevel = CellUtils.getCellAsIntValue(data, rawIndex, ROWS_AND_CELLS.COL_DAMAGE_LEVEL.value);
		int healthLevel = CellUtils.getCellAsIntValue(data, rawIndex, ROWS_AND_CELLS.COL_HEALTH_LEVEL.value);
		String trait2 = CellUtils.getCellAsTextValue(data, rawIndex, ROWS_AND_CELLS.COL_TRAIT2.value);
		String trait3 = CellUtils.getCellAsTextValue(data, rawIndex, ROWS_AND_CELLS.COL_TRAIT3.value);
		String trait4 = CellUtils.getCellAsTextValue(data, rawIndex, ROWS_AND_CELLS.COL_TRAIT4.value);
		String trait5 = CellUtils.getCellAsTextValue(data, rawIndex, ROWS_AND_CELLS.COL_TRAIT5.value);

		List<Trait> traits = List.of(
			Trait.of(trait2),
			Trait.of(trait3),
			Trait.of(trait4),
			Trait.of(trait5)
		);

		return new Survivor(name, SurvivorClass.of(className), level, extraStars, damageLevel, healthLevel, traits);
	}

}
