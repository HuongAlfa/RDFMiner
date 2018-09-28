package rdfminer;
import java.util.Scanner;

import rdfminer.RDFMiner2;
import rdfminer.SparqlEndpoint;
public class Matrix{

	
	private int i, j, m, n, k;
    private static int [][] M= new int [9][9];
     static String dbpedia = "http://localhost:8890/sparql";
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
    public static SparqlEndpoint endpoint;
    
    public static void main(String[] args) {
    	endpoint = new rdfminer.SparqlEndpoint(dbpedia, PREFIXES);
    	Matrix A = new Matrix();
        A.Nhap();
        A.Hienthi();

    }

    public void Hienthi() {
        for(i=0; i<n; i++){
            for(j=0; j<m; j++){
                System.out.print(M[i][j] + " ");
            }
            System.out.println();
        }

    }

    public void Nhap() {
        Scanner sc = new Scanner(System.in);
      String sparql = "DISTINCT ?class WHERE { ?_ a ?class . FILTER regex(?class,<http://dbpedia.org/ontology*>) FILTER( strStarts(MD5(str(?class))  , \" + h + \") ) }";
      RDFMiner2.endpoint.select(sparql);
       System.out.println(RDFMiner2.endpoint.resultSet.toString());
        System.out.println("Nhap so hang:");
        n = sc.nextInt();
        System.out.println("Nhap so cot:");
        m = sc.nextInt();
        for(i=0; i<n; i++){
            for(j=0; j<m; j++){
                System.out.println("nhap ma tran M [" + i + "][" + j + "]=");
                M[i][j] = sc.nextInt();
            }
     
        }
        sc.close();
    }

}
