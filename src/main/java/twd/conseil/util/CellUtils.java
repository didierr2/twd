package twd.conseil.util;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CellUtils {

	public static boolean isCellPresent(Sheet sheet, int rowIndex, int colIndex) {
		boolean present = true;
		try {
			Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
			present = (cell != null);
		} catch (Exception e) {
			present = false;
		}
		return present;
	}

	public static Date getCellAsDateValue(Sheet sheet, int rowIndex, int colIndex) {
		Date val = null;
		try {
			Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
			if (cell != null) {
				val = cell.getDateCellValue();
			}
		} catch (Exception e) {
			System.err.println("Error recuperation cellule date " + getCellLabel(rowIndex, colIndex) + " : " + e.getMessage());
		}
		return val;
	}

	public static int getCellAsIntValue(Sheet sheet, int rowIndex, int colIndex) {
		int val = -1;
		String cellStr = getCellAsTextValue(sheet, rowIndex, colIndex);
		try {
			double dbl = Double.valueOf(cellStr);
			val = (int)dbl;
		}
		catch (NumberFormatException nfe) {
			val = -1;
			System.out.println("Erreur lors de la lecture de la cellule " + ((char)('A' + colIndex)) + rowIndex + " : la valeur n'est pas un nombre");
			System.out.println(nfe.getMessage());
		}
		return val;
	}


	public static String getCellAsTextValue(Sheet sheet, int rowIndex, int colIndex) {
		String val = "";
		try {
			Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
			if (cell != null) {
				switch (cell.getCellType()) {
				case BOOLEAN:
					val = String.valueOf(cell.getBooleanCellValue());
					break;
				case STRING:
					val = cell.getStringCellValue();
					break;
				case BLANK:
					val = "";
					break;
				case NUMERIC:
					val = String.valueOf(cell.getNumericCellValue());
					break;
				default:
					System.err.println("Read error " + getCellLabel(rowIndex, colIndex) + ", type = " + cell.getCellType().name());
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Error recuperation cellule " + getCellLabel(rowIndex, colIndex) + " : " + e.getMessage());
		}
		return val;
	}

	public static String getCellLabel(int rowIndex, int colIndex) {
		return (char) ('A' + colIndex) + "" + (rowIndex + 1);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}
	

	public static void writePercentCell (Workbook workbook, Sheet sheet, int rowIndex, int colIndex, String val) {
		try {
			Cell cell = writeNumericCell(sheet, rowIndex, colIndex, val);
			CellStyle style = workbook.createCellStyle();
			style.setDataFormat(workbook.createDataFormat().getFormat("0%")); //"0.00%"
			cell.setCellStyle(style);
		}
		catch (NumberFormatException nfe) {
			System.err.println(String.format("Erreur durant l'ecriture de '%s' en tant que nombre dans la cellule (lecriture sera realisee en string): %s", val, nfe.getMessage()));
			writeCell(sheet, rowIndex, colIndex, val);
		}
	}

	public static Cell writeNumericCell (Sheet sheet, int rowIndex, int colIndex, String val) {
		Cell cell = null;
		try {
			Double num = Double.valueOf(val);
			cell = createCellIfNotExists(sheet, rowIndex, colIndex, CellType.NUMERIC);
			cell.setCellValue(num);
		}
		catch (NumberFormatException nfe) {
			System.err.println(String.format("Erreur durant l'ecriture de '%s' en tant que nombre dans la cellule (lecriture sera realisee en string): %s", val, nfe.getMessage()));
			cell = writeCell(sheet, rowIndex, colIndex, val);
		}
		return cell;
	}
	
	public static Cell writeCell (Sheet sheet, int rowIndex, int colIndex, String val) {
		Cell cell = createCellIfNotExists(sheet, rowIndex, colIndex, CellType.STRING);
		cell.setCellValue(val);
		return cell;
	}
	
	private static Cell createCellIfNotExists(Sheet sheet, int rowIndex, int colIndex, CellType ctype) {
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		Cell cell = row.getCell(colIndex);
		if (cell == null) {
			cell = row.createCell(colIndex, ctype);
		}
		return cell;
	}
	
	
}
