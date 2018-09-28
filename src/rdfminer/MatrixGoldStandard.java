package rdfminer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.RDFNode;

import GoldStandard.Customer;
import Mapper.Production;
import rdfminer.RDFMiner2;
import rdfminer.SparqlEndpoint;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MatrixGoldStandard{

	
	private int i;
	private int j;
    private static String [][] M= new String [63][63];
     static String dbpedia = "http://localhost:8890/sparql";
     static String dbpedia2 = "http://dbpedia.org/sparql";
     static String PREFIXES =
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +     
			"PREFIX dbr: <http://dbpedia.org/resource/>\n" +
			"PREFIX dbp: <http://dbpedia.org/property/>\n" +
	     	"PREFIX dbo: <http://dbpedia.org/ontology>\n" ;
    public static SparqlEndpoint endpoint,endpoint2;
	private static Scanner sc;
	private static final String FILE_NAME = "/media/huong/DATA/These/Codes/rdfminer/src/GoldStandard.xlsx";
    public static void main(String[] args) throws IOException {
    	sc = new Scanner(System.in);
    	endpoint = new rdfminer.SparqlEndpoint(dbpedia, PREFIXES);
    	endpoint2 = new rdfminer.SparqlEndpoint(dbpedia2, PREFIXES);
    	MatrixGoldStandard A = new MatrixGoldStandard();
        A.Nhap();
        A.Hienthi();
        for(int i=1; i<63; i++)
    	{
        SetupSubClassOf(M[0][i]);
    	}
      //  String Class = "http://www.w3.org/2002/07/owl#Thing";
        String Class = "http://dbpedia.org/ontology/Work";
        DFS(Class);
     	A.Hienthi();
     	A.checkZero(getMatrix());
        A.WriteExcelFile(getMatrix());
    }

    

	public void Hienthi() {
        for(i=0; i<63; i++){
            for(j=0; j<63; j++){
                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void Nhap() throws IOException {
     //   Scanner sc = new Scanner(System.in);
     // String sparql = "DISTINCT ?class WHERE { ?_ a ?class . FILTER regex(?class,<http://dbpedia.org/ontology*>)}";
    //	 String sparql ="DISTINCT ?class WHERE { ?class a owl:Class .FILTER regex(?class,<http://dbpedia.org/ontology*>) }";
    	String sparql ="distinct ?class where {?class rdfs:subClassOf+ <http://dbpedia.org/ontology/Work>}";
	    System.out.print("Querying SPARQL endpoint for symbol <Class> with query:\nSELECT " + SparqlEndpoint.prettyPrint(sparql));     
	   
	   // endpoint2.select("DISTINCT ?class WHERE {?class a owl:Class .FILTER regex(?class,<http://dbpedia.org/ontology*>) }");
	     //  endpoint.select("DISTINCT ?class WHERE { ?_ a ?class . FILTER regex(?class,<http://dbpedia.org/ontology*>)}");
	    endpoint2.select("distinct ?class where {?class a owl:Class. ?class rdfs:subClassOf+ <http://dbpedia.org/ontology/Work>}");
	/*   PrintStream cache = null;
	    try {
	    	cache = new PrintStream(cacheName("class", sparql));
	    }
	    catch(FileNotFoundException e)
	    {
	    	System.out.println("Could not create cache for symbol class");
	    }
	  */
	    int dem=0;
	    int j=0;
	    M[0][0]="GOLD STANDARD";
      while(endpoint2.hasNext() && dem <63)
	    {
	    	QuerySolution solution = endpoint2.next();
	    	Iterator<String> i = solution.varNames();
	    //	String separator = "";
	    	while(i.hasNext())
	    	{
	    		String varName = i.next();
	    		RDFNode node = solution.get(varName);
	//    		System.out.println(node.toString());	
	    	    dem++;
	    	    M[dem][0]= node.toString();
	    	    M[0][dem]=node.toString();
	    	}
	          
        
        } 
        System.out.println("so class la: "+ dem );
		
     //   System.out.println("Nhap so hang:");
    //    n = sc.nextInt();
     //   System.out.println("Nhap so cot:");
     //   m = sc.nextInt();
	 for(int i1=1; i1<63; i1++){
            for(int j1=1; j1<63; j1++){
         //       System.out.println("nhap ma tran M [" + i1 + "][" + j + "]=");
              if (i1==j1) 
            	  M[i1][j1]="F";
              else
            	 M[i1][j1] = "0";
            }
     
        } 
      //  sc.close();
    }
    public static String cacheName(String symbol, String sparql)
	{
		return String.format("%s%08x.cache", symbol, sparql.hashCode());
	}
	
    static boolean checkSibling(String Class, String Class2)
    {
    	
    	String sparql= "DISTINCT ?superClass WHERE { <" + Class + "> rdfs:subClassOf ?superClass. FILTER regex(?superClass,<http://dbpedia.org/ontology*>)  }";
    	String sparql2= "DISTINCT ?superClass WHERE { <" + Class2 + "> rdfs:subClassOf ?superClass. FILTER regex(?superClass,<http://dbpedia.org/ontology*>) }";
    	ArrayList<RDFNode> k1= ResultQuery(endpoint2,sparql);
    	ArrayList<RDFNode> k2= ResultQuery(endpoint2,sparql2);
    	if (k1.size()!=0 & k2.size()!=0)
    	{
    	if (k1.get(0).toString().equals(k2.get(0).toString())) 
    	{
  //  		System.out.println(Class + " is sibling with " + Class2);
  //  		System.out.println();
            return true;    	
    	}
            else
            {	
    //		System.out.println(Class + " is not sibling with " + Class2);
    //		System.out.println();
    		return false;

            }
    	}
    	else 
    		return false;
    }
    
    static boolean checkSubclass(String Class, String Class2)
    {
    	
    	String sparql= "DISTINCT ?superClass WHERE { <" + Class + "> rdfs:subClassOf ?superClass. FILTER regex(?superClass,<http://dbpedia.org/ontology*>)  }";
    	ArrayList<RDFNode> k1= ResultQuery(endpoint2,sparql);
    	if (k1.size()!=0)
    	{
    		if (Class2.equals(k1.get(0).toString())) 
    	{
    //		System.out.println(Class + " is subClassOf " + Class2);
            return true;    	
    	}
            else
            {	
    //		System.out.println(Class + " is not subClassOf " + Class2);
    		return false;

            }
    	}
    	else
    		return false;
    }
  
    static ArrayList<String> ListSubClasses (String superClass)
    {
    	String sparql= "DISTINCT ?Class WHERE { ?Class rdfs:subClassOf+ <" + superClass +"> .FILTER regex(?Class,<http://dbpedia.org/ontology*>)}";
    	ArrayList<RDFNode> k1= ResultQuery(endpoint2,sparql);
    	ArrayList<String> List = new ArrayList<String>();
    	for(int i=0; i<k1.size();i++)
    	{
    		List.add(k1.get(i).toString());
    	}
    	return List;
    }
    static ArrayList<String> ListSubClasses2 (String superClass)
    {
    	String sparql= "DISTINCT ?Class WHERE { ?Class rdfs:subClassOf <" + superClass +"> .FILTER regex(?Class,<http://dbpedia.org/ontology*>) }";
    	ArrayList<RDFNode> k1= ResultQuery(endpoint2,sparql);
    	ArrayList<String> List = new ArrayList<String>();
    	
    	//for(int i=0; i<k1.size();i++)
    	for(int i=0; i<k1.size();i++)
    	{
    		List.add(k1.get(i).toString());
    	}
    	return List;
    }

    static ArrayList<RDFNode> ResultQuery(SparqlEndpoint endpoint, String sparql)
    {
    	ArrayList<RDFNode>  arrRDFNode = new ArrayList<RDFNode>();
    //	System.out.println("Querying SPARQL endpoint for symbol <Class> with query:\nSELECT " + SparqlEndpoint.prettyPrint(sparql));  
    	endpoint.select(sparql);
    	 while(endpoint.hasNext())
 	    {
 	    	QuerySolution solution = endpoint.next();
 	    	Iterator<String> i = solution.varNames();
 	//    	String separator = "";
 	 	    	
 		    	while(i.hasNext())
 		    	{
 		    		String varName = i.next();
 		    		RDFNode node = solution.get(varName);
 		    		arrRDFNode.add(node);
 		    //		System.out.println(node.toString());	
 		    		
 		    	}
 		 }
    	 return arrRDFNode;
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
    static void  SetupSubClassOf(String Class)
    {
    	int pos,pos1;
    	    ArrayList<String> ListSubClass= ListSubClasses(Class);
    		pos1=Search(Class,M);
    		if (ListSubClass.size()!=0) 
    		{  
    			for (int j=0; j<ListSubClass.size(); j++)
    			{
    				pos= Search(ListSubClass.get(j).toString(),M);
    				if(pos>=0)
    				{
    					M[pos1][pos]="F";
    					M[pos][pos1]="F";
    				}
    			}
    		}
    }
    static void SetUpSiblingDisjoint(String Class1, String Class2)
    {
    	int pos11,pos22;
    	int pos1=Search(Class1,M);
    	int pos2=Search(Class2,M);;
    	ArrayList<String> ListSubClass1; //= ListSubClasses(Class1); 
    	ArrayList<String> ListSubClass2; //=ListSubClasses(Class2);
    	
   	
    if (ListSubClasses(Class1).size()==0)
    {
    		ListSubClass1= ListSubClasses(Class2);
    		ListSubClass2= ListSubClasses(Class1);
    		pos1=Search(Class2,M);
    		pos2=Search(Class1,M);
    	
    			
    }
    else
    
    {
    		ListSubClass1= ListSubClasses(Class1);
    		ListSubClass2= ListSubClasses(Class2);
    		pos1=Search(Class1,M);
    		pos2=Search(Class2,M);
		
    }	
    	    	
    	for (int i=0; i<ListSubClass1.size(); i++)
    	{
    		pos11=Search(ListSubClass1.get(i).toString(),M);
    		M[pos2][pos11]="T";
    		M[pos11][pos2]="T";
    		for (int j=0; j<ListSubClass2.size();j++)
    		{
    			pos22=Search(ListSubClass2.get(j).toString(),M);
    			M[pos1][pos22]="T";
    			M[pos22][pos1]="T";
    		if(pos11 >=0 && pos22 >=0)
    			{
    			M[pos11][pos22]="T";
    			M[pos22][pos11]="T";
    			}
    		}
    	}
    }
    static String getSuperClass (String Class)
    {
    	String sparql= "DISTINCT ?superClass WHERE { <" + Class + "> rdfs:subClassOf ?superClass .FILTER regex(?superClass,<http://dbpedia.org/ontology*>) }";
    	ArrayList<RDFNode> k1= ResultQuery(endpoint2,sparql);
    	if (k1.size()!=0)
    	{
    		String superClass = k1.get(0).toString();
    		return superClass;
    	}
    	else
    	{
    		return "";
    	}
    }
    
    static ArrayList<String> ListSibling (String Class)
    {
    	String superClass = getSuperClass(Class);
    	String sparql="DISTINCT ?Class WHERE { ?Class  rdfs:subClassOf <" + superClass + "> .FILTER regex(?Class,<http://dbpedia.org/ontology*>)}";
    	ArrayList<RDFNode> k1= ResultQuery(endpoint2,sparql);
    	ArrayList<String> List = new ArrayList<String>();
    	if (k1.size()!=0)
    	{
    		for(int i=0; i<k1.size();i++)
        	{
        		if(!k1.get(i).toString().equals(Class))
    			List.add(k1.get(i).toString());
        	}
    	}
        	return List;
    }
    
    static boolean Checked(String Class1, String Class2)
    {
    	int pos1= Search(Class1,M);
    	int pos2=Search(Class2,M);
    	if (!M[pos1][pos2].equals("0"))
    	return true; // da co gia tri
    	else return false; // chua co gia tri
    }
    
    static int countUnChecked(String Class1, ArrayList<String> ListClasses) 
    {
    	int dem=0;
    	for (int i=0; i<ListClasses.size(); i++)
    	{
    		if (!Checked(Class1, ListClasses.get(i).toString()))
    		dem= dem +1;
    	}	
    	return dem;
    	
    }
    static void DFS( String Class)
    {
    	String n="y"; 
        int pos1, pos2, countUnChecked;
    	String c2;
    	String m="y";
    	sc = new Scanner(System.in);
    	ArrayList<String> Siblings = ListSibling(Class);
    	ArrayList<String> ListSubclasses = ListSubClasses2(Class);
    	String check="";
    if(ListSubclasses.size()!=0)
    {
      	System.out.println();
    	System.out.println(ListSubclasses.size() + " SUBCLASSES OF THE CLASS " + Class);
    	System.out.println("*****************************************");
      	System.out.println();
    	for (int t2=0; t2<ListSubclasses.size(); t2++)
    	{
    		System.out.println(ListSubclasses.get(t2).toString());
    	}
    }
    countUnChecked=countUnChecked (Class,Siblings);

    if ((Siblings.size() >0) && (countUnChecked >0))
    
    {
    	System.out.println();
    	System.out.println("****************************************");
    	System.out.println(Siblings.size() + " LIST SIBLING OF THE CLASS " + Class);
     	System.out.println("****************************************");
      	System.out.println();
    	for (int t1=0; t1<Siblings.size(); t1++)
    	{
    		c2= Siblings.get(t1).toString();	
    		if (!c2.equals(Class))
    		if (Checked (Class,c2))
    			check="Checked";
    		else 
    			check="Unchecked";
    		
    			System.out.println(Siblings.get(t1).toString() + "  " + check);
    	}
    	System.out.println("*****************************************************************");
        System.out.println("List unchecked:" + countUnChecked);
    	System.out.println("*****************************************************************");
    	System.out.println("Setup automatically disjointness between sibling class group of the class " + Class + "? Press 'y' - Yes or Press any keys - Manually" );
    	m=sc.nextLine().toString();
    	if (m.equals("y"))
    	System.out.println("Automatically setting up......" );
    	else
    	System.out.println("Manually setting up ......");
    		for (int i=0; i< Siblings.size(); i++)
    		{
    		 //  	System.out.println("da vao day1");
    			c2= Siblings.get(i).toString();
    			if (!Checked(Class,c2))
    			{
    					pos1= Search(Class,M);
    			//		System.out.println(pos1);
    					pos2=Search(c2,M);
    			//		System.out.println(pos2);
    					if  (pos1>=0 & pos2 >=0)
    						{
    			//				System.out.println("da vao day2");
    			//				System.out.println(M[pos1][pos2]);
    							if (M[pos1][pos2].equals("0"))
    								{					  
    			//						System.out.println("da vao day3");
    									if (!m.equals("y"))
    										{
    											System.out.println( Class + " and " + c2 + " are disjointness or not? Press 'y' - yes or Press any keys - no");
    											n= sc.nextLine().toString();
    										//	System.out.println("You choose: " + n); 
    										}	
    							            else 
    										{
    											n="y";
    										//	System.out.println("You choose: " + n); 
    										}
    			//				  System.out.println(pos1);
    			//				  System.out.println(pos2);
						if(n.equals("y"))
								{
									M[pos1][pos2]="T";
									M[pos2][pos1]="T";
									SetUpSiblingDisjoint(Class,c2);
				//					System.out.println("da vao day 4");
								}
								else
								{
					  				M[pos1][pos2]="F";
					  				M[pos2][pos1]="F"; 
				//	  				System.out.println("da vao day 5");
								}
					 }	
    							
    					
    		}
    	}		
    }
    		
  }			
	    for (int t=0; t<ListSubClasses(Class).size(); t++)
    	DFS(ListSubClasses(Class).get(t).toString());
    	   	
    }
    
    void WriteExcelFile(String[][] arr)
    {
    	System.out.println("Recording Gold Standard Matrix to Excel file.....");
    	XSSFWorkbook workbook = new XSSFWorkbook();
    	XSSFSheet sheet = workbook.createSheet("GoldStandard");
        for (int rowNum=0;rowNum<arr.length; rowNum++)
        {
        	Row row = sheet.createRow(rowNum);
        	for (int cellNum=0; cellNum<arr.length; cellNum++) {
            Cell cell = row.createCell(cellNum,CellType.STRING);
            cell.setCellValue(arr[rowNum][cellNum].toString());
       //     System.out.println(rowNum);
        //    System.out.println(cellNum);
         //   System.out.println(arr[rowNum][cellNum].toString());
        /* 	 Cell cell = row.createCell(cellNum);
             cell.setCellValue("dssd");
         /  Cell cell2 = row.createCell(1);
             cell2.setCellValue("sdfsdf");
             Cell cell3 = row.createCell(2);
             cell3.setCellValue("sdfsdf"); */
          }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream("GoldStandard.xlsx");
            workbook.write(outputStream);
            workbook.close();
          } catch (FileNotFoundException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
          System.out.println("Done");
    	
    }
    static String[][] getMatrix()
    {
    	return M;
    }
    void checkZero (String[][] arr)
    {
     	System.out.println("Check the remaining unchecked pair classes....");
    	sc = new Scanner(System.in);
    	String n="";
    	for(int i=0; i<arr.length; i++ )
    		for(int j=0;j<arr.length; j++ )
    		{
    			if (arr[i][j].equals("0"))
    					{
    						System.out.println( arr[i][0] + " and " + arr[0][j] + " are disjointness or not? Press 'y' - yes or Press any keys - no");
    						n= sc.nextLine().toString();
    						
    						if(n.equals("y"))
    							{
    								arr[i][j]="T";
    								arr[j][i]="T";
    							}
    						else
    							{
    								arr[i][j]="F";
    								arr[j][i]="F"; 
    							}
    					}
    		}
    	
	}
  
	
}
