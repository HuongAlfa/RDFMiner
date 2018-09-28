package GoldStandard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Read_Result_Excel {
	private static final String FILE_NAME = "/media/huong/DATA/These/Codes/rdfminer/src/GoldStandard/Assesement_Expert.xlsx";
	public static void main(String[] arg) throws IOException
	{
	 
		CheckResult(4,3);
		
	}
	public static void CheckResult(int pos1, int pos2) throws IOException  {
        int n =12;
		String arr[][] = new String [n][n];
        int i=0;
       
		String excelFilePath = FILE_NAME;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        Row nextRow;// = iterator.next();
        Iterator<Cell> cellIterator;// = nextRow.cellIterator();
        Cell cell; //= cellIterator.next();
        while (iterator.hasNext()) {
           nextRow = iterator.next();
          cellIterator = nextRow.cellIterator();
        // cell = cellIterator.next();
          
            int j=0;
            while (cellIterator.hasNext()) {
                 cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        arr[i][j]=cell.getStringCellValue(); 
                       System.out.print(arr[i][j]);
                   	break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print("  ");
             j++;            
            }
            
            System.out.println();
             i++;       
        }
        workbook.close();
        inputStream.close();
        System.out.print("Phan tu hang: " + pos1 + " --- cot: " + pos2 + " la:");
        System.out.print(arr[pos1][pos2]);
    }
	
	
 
}
