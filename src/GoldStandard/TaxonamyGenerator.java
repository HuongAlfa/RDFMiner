package GoldStandard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TaxonamyGenerator {
	private static final String FILE_NAME = "/media/huong/DATA/These/Codes/rdfminer/src/GoldStandard/TaxonamyClasses.xlsx";
	public static void main(String[] args) throws IOException
	{
		int n =5;
	     NodeClass arr[]  = new NodeClass [n];
        int i=1;
        String sign = "";
		String excelFilePath = FILE_NAME;
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator(); //mang hang
        Row nextRow = iterator.next();
       //Row nextRow; //hang
        Iterator<Cell> cellIterator = nextRow.cellIterator();
       //Iterator<Cell> cellIterator;  //mang cot
        Cell cell = cellIterator.next();
        //Cell cell;  //cot 
//        System.out.println(cell.getNumericCellValue());
   //     System.out.println(cellIterator.next().getStringCellValue());
        NodeClass root = new NodeClass(cellIterator.next().getStringCellValue(),(int) cell.getNumericCellValue());
        System.out.print (root.getLayer());
        System.out.println(root.value);
        NodeClass point = root;
        //     System.out.println(cellIterator.next().getStringCellValue());
        while (iterator.hasNext()) {
            nextRow = iterator.next();
            
            cellIterator = nextRow.cellIterator();
           // cell = cellIterator.next();
          
            int j=1;
            while (cellIterator.hasNext()) {
                 cell = cellIterator.next();
           
              switch (cell.getCellType()) {
                 /*   case Cell.CELL_TYPE_STRING:
                                   	
                       System.out.print(cell.getStringCellValue());
                       if (sign=="child")
                       {
                    	   NodeClass root
                       }
                   	break;
                   	*/
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC: 
                    	int t= (int)cell.getNumericCellValue();
                    	System.out.print(cell.getNumericCellValue());
                    	NodeClass node = new NodeClass (cellIterator.next().getStringCellValue(),(int)cell.getNumericCellValue());
                    	if (t> point.getLayer())
                    	{
                    		 sign="child";
                    		 point.pushDecendents(node);
                    		 System.out.print(node.getValue());
                    	}
                    	else
                    	{ 
                    	   	root.pushDecendents(node);
                    	}
                    	arr[j]=point;
                    	 System.out.println("node: " + arr[j].getValue() );
                    	 System.out.println("descedents:" + arr[j].getAllDescendents());
                    	point=node;
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
        }
		
	/*	
		NodeClass Class = new NodeClass("Activity");
		Class.pushDecendents("Game");
		Class.pushDecendents("Sales");
		Class.pushDecendents("Sport");
		while 
		TaxonamyClasses Class = new TaxonamyClasses(Class.getDescendents()[1]);
		
	*/	
	

}
