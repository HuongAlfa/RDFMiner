package rdfminer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GoldStandardComparison {
	private static Logger logger = Logger.getLogger(GoldStandardComparison.class.getName());  
	protected String GoldStandardFile;
      protected String Class1;
      protected String Class2;
      protected String[][] GoldStandardArr;
      
      GoldStandardComparison (String GoldStandardFile)
      {
    	  this.GoldStandardFile=GoldStandardFile;
      }
	static int Search(String Class, String[][] arr)
    {
    	  for (int i = 0; i < arr.length; i++){
              if (arr[0][i].equals(Class))
              {
                    return i; //i là vị trí của s
              }
       }
       return -1; // không có trả về -1 vì 0 là vị trí đầu tiên trong mảng
    }
	
	  String CheckDisjointness(String class1, String class2, String[][]GoldStandardArr)
	    {
	    	int pos1= Search(class1,GoldStandardArr);
	    	int pos2=Search(class2,GoldStandardArr);
	    	logger.info ("Position in GoldStandard matrix:  row: " + pos1 + " column: " + pos2);
	    	return GoldStandardArr[pos1][pos2];
	    } 
	    public String[][] ConvertExcelResultToArray () throws IOException  {
	        int n =63;
			String [][] arr = new String [n][n];
	        int i=0;
	        String excelFilePath = GoldStandardFile;
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
	        return arr;
	    }
		void setGoldStandard(String [][] GoldStandardArr)
		{
			this.GoldStandardArr=GoldStandardArr;
		}
		String [][] getGoldStandard()
		{
			return GoldStandardArr;
		}
		
}
