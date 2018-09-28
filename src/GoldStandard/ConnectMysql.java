package GoldStandard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import rdfminer.FitnessEvaluation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
 
public class ConnectMysql {
	  private static Logger logger = Logger.getLogger(ConnectMysql.class.getName());   
	  private static String DB_URL = "jdbc:mysql://localhost:3306/GoldStandard?useCursorFetch=true";
	    private static String USER_NAME = "root";
	    private static String PASSWORD = "PASSWORD";
	    private static final String FILE_NAME = "/media/huong/DATA/These/Codes/rdfminer/src/GoldStandard/Assesement_Expert.xlsx";
	    protected static   int part1=0;
	    protected static   int part2=0;
	    protected static String query1, query2;
	    public ConnectMysql (String str1, String str2)
	    {
	    	query1=str1;
	        query2=str2;
	    }
	public void CheckSQL() throws IOException, SQLException {
		// TODO Auto-generated method stub
	// String query = "select id from Classes2 where uri='<http://dbpedia.org/ontology/Plants>'";
	// String query2 = "select id from Classes2 where uri='<http://dbpedia.org/ontology/Person>'";
     int part1=  Reference_MySQL(query1);
     int part2= Reference_MySQL(query2);
     CheckResult(part1,part2);
	}

	 public static Connection getConnection(String dbURL, String userName, 
	            String password) {
	        Connection conn = null;
	        try {
	        	DriverManager.registerDriver( new com.mysql.jdbc.Driver() );
	            conn = DriverManager.getConnection(dbURL, userName, password);
	            System.out.println("connect successfully!");
	        } catch (Exception ex) {
	            System.out.println("connect failure!");
	            ex.printStackTrace();
	        }
	        return conn;
	    }
	 
	 
	 public static void CheckResult(int part1, int part2) throws IOException  {
	        int n =6;
			String arr[][] = new String [n][n];
	        int i=1;
	       
			String excelFilePath = FILE_NAME;
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Sheet firstSheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = firstSheet.iterator();
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        Cell cell = cellIterator.next();
	        while (iterator.hasNext()) {
	            nextRow = iterator.next();
	            
	            cellIterator = nextRow.cellIterator();
	            cell = cellIterator.next();
	          
	            int j=1;
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
	        logger.info("Phan tu hang: " + part1 + " --- cot: " + part2 + " la:");
	       logger.info (arr[part1][part2]);
	    }
	 static int Reference_MySQL(String query) throws SQLException
	 { 
	//	 try {
	 
         // connnect to database 'testdb'
         Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
         // crate statement
         Statement stmt = conn.createStatement();
         // get data from table 'student'
         ResultSet rs_part1 = stmt.executeQuery(query);   
        // show data
       // setPart1(rs_part1.getInt(1));
            //  CheckResult(rs_part1.getInt(1), rs_part2.getInt(1));
          while (rs_part1.next()) {
      //     rs_part1.getInt(1);
        	 setPart1(rs_part1.getInt(1));
        System.out.println(getPart1());
        }
         // close connection
      /*   while (rs_part1.next()) {
         SetPart1(rs_part1.getInt(1));
         }*/
        conn.close();
        return getPart1();
      
 //    } catch (Exception ex) {
  //       ex.printStackTrace();

  //   }
		 
    }
	

	static int getPart1()
	 {
		 return part1;
	 }
	 static int getPart2()
	 {
		 return part2;
	 }

	  void setPart2(int Part2)
	 {
		 part2= Part2;
	 }
	  static void setPart1(int Part1)
	 {
	     part1= Part1;
	 }
	}
	

