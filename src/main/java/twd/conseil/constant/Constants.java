package twd.conseil.constant;

import java.text.SimpleDateFormat;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	public static final SimpleDateFormat SDF_EXCEL = new SimpleDateFormat("dd/MM/yyyy");



	public enum ROWS_AND_CELLS {
		SHEET_DATA(0),
		// SHEET_INDICATOR(1),
		// SHEET_SYNTHESIS(2),
		ROW_FIRST_SURVIVANT (1),
		COL_NAME (0),
		COL_CLASS_NAME (1),
		COL_LEVEL (2),
		COL_EXTRA_STARS (3),
		COL_DAMAGE_LEVEL (4),
		COL_HEALTH_LEVEL (5),
		COL_TRAIT1 (6),
		COL_TRAIT2 (7),
		COL_TRAIT3 (8),
		COL_TRAIT4 (9),
		COL_TRAIT5 (10);

		// COL_FIRST_STOCK (1),
		// ROW_INDEX (0), // INDICE (DOW, CAC)
		// ROW_RECO (1), // RECOMMANDATION
		// ROW_REPORT_COMM (2),
		// ROW_BUY_DATE (3),
		// ROW_COST_PRICE (4),
		// ROW_URL (5),
		// ROW_ISIN (6),
		// ROW_SOCIETY (7),
		// ROW_REF_PRICE(8),
		// COL_DATE(0),
		
		// // Les indicateurs
		// COL_INDICATORS_HEADERS (0),
		// COL_INDICATORS_FIRST(1),
		// ROW_INDICATORS_HEADERS (0),
		// ROW_INDICATORS_FIRST(1);
		
		
		public int value = 0;
		ROWS_AND_CELLS(int n) {
			value = n;
		}
	}
			
}
