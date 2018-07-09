package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import objectRepository.FuelPrice;

public class ExcelUtil {

	public static void writeDataToSheet(List<FuelPrice> fuelData) throws Exception {
		try {
			int rowCount = 0;

			File file = new File("C:\\Users\\deepak.a.jayaraman\\Documents\\ExcelWriteTest\\FuelData.xlsx");

			FileOutputStream fout = new FileOutputStream(file);

			XSSFWorkbook wb = new XSSFWorkbook();

			XSSFSheet sheet = wb.createSheet();

			for (FuelPrice fp : fuelData) {
				XSSFRow row = sheet.createRow(rowCount);

				for (int col = 0; col < 4; col++) {
					XSSFCell cell = row.createCell(col);

					switch (col) {
					case 0:
						cell.setCellValue(fp.getStation());
						break;
					case 1:
						cell.setCellValue(fp.getPetrolPrice());
						break;
					case 2:
						cell.setCellValue(fp.getDieselPrice());
						break;
					case 3:
						cell.setCellValue(fp.getLpgPrice());
					}
				}

				rowCount++;
			}

			wb.write(fout);

			fout.flush();
			fout.close();

			wb.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
