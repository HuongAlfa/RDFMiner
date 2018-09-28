package GoldStandard;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteExcel {
  public static void main(String[] args) {
    System.out.println("Create file excel");
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Customer_Info");
    int rowNum = 0;
    Row firstRow = sheet.createRow(rowNum++);
    Cell firstCell = firstRow.createCell(0);
    firstCell.setCellValue("List of Customer");
    List<Customer> listOfCustomer = new ArrayList<Customer>();
 
    listOfCustomer.add(new Customer(2, "Tom Cruise", "xyz@yahoo.com"));
    listOfCustomer.add(new Customer(3, "Vin Diesel", "abc@hotmail.com"));
    for (Customer customer : listOfCustomer) {
      Row row = sheet.createRow(rowNum++);
      Cell cell1 = row.createCell(0);
      cell1.setCellValue(customer.getId());
      Cell cell2 = row.createCell(1);
      cell2.setCellValue(customer.getName());
      Cell cell3 = row.createCell(2);
      cell3.setCellValue(customer.getEmail());
    }
    try {
      FileOutputStream outputStream = new FileOutputStream("Demo-ApachePOI-Excel.xlsx");
      workbook.write(outputStream);
      workbook.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Done");
  }
}

