package rdfminer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.shared.JenaException;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

import Individuals.GEIndividual;
import Individuals.Individual;
import Individuals.FitnessPackage.BasicFitness;
import Individuals.Populations.SimplePopulation;
import Mapper.Grammar;
import Mapper.Symbol;
import GoldStandard.ConnectMysql;

/**
 * FitnessEvaluation - is the class to setup the fitness value for Axioms in the specified population
 * @author NGUYEN Thu Huong
 * Nov 18, 2017
 */
public class FitnessEvaluation {
	private static Logger logger = Logger.getLogger(FitnessEvaluation.class.getName());

	protected SimplePopulation population;
	protected Axiom a;
	protected long t0=0,t=0;
	protected long dt;
	int count;
	double referenceCardinality=0.0;
	double possibility=0.0;
	double necessity=0.0;
    protected int numTrueGoldStandard=0;
	
	
	FitnessEvaluation(SimplePopulation population)
	{
		this.population=population;
		this.count=0;
	}
    FitnessEvaluation()
    {
    	
    }
/* Updates the fitness values based on the possibility and necessity degrees 
 * fitness= cardinality * (possibility + necessity)/2
 * 
 * 
 *  
 */
   
	void update(AxiomTest report,Marshaller marshaller, FileOutputStream xmlStream, GoldStandardComparison  goldstandard) throws JAXBException, IOException, SQLException 
    {
    	int i=0;
       // AxiomTest report = new AxiomTest();
    	String arg1="";
    	String arg2="";
    	String Class1="";
        String Class2="";
        String checkGoldStandard="";
     
        while(i < population.size())
    {	
     	
     	GEIndividual indivi= (GEIndividual) population.get(i);
     		if(population.get(0).getPhenotype()==null)
     		    
     			break;
     	report.generation= population.get(i).getAge();
    	report.axiom =population.get(i).getPhenotype().getStringNoSpace();
    	report.chromosome= population.get(i).getGenotype().toString();
    	logger.info("...............................................................................");
    	logger.info("Axiom: " + report.axiom);	     	
     	if (indivi.isMapped())
     	{
     	
     	try
		{
     		t0= System.currentTimeMillis();
    		a = AxiomFactory.create(population.get(i).getPhenotype());
    		List<List<Symbol>> arguments = a.ArgumentClasses;
    		logger.info("Argument of axioms:"+  arguments.get(0) + " and " + arguments.get(1));
    /*		str1= "select id from Classes2 where uri='"+ arguments.get(0).get(0).getSymbolString() + "'";
    		str2= "select id from Classes2 where uri='"+ arguments.get(1).get(0).getSymbolString() + "'";
    		ConnectMysql checkaxiom = new ConnectMysql(str1,str2);
    		checkaxiom.CheckSQL();
    */		
    		arg1= arguments.get(0).get(0).getSymbolString();
    		arg2= arguments.get(1).get(0).getSymbolString();
    		
    		Class1= arg1.substring(1,arg1.length()-1);
    		Class2= arg2.substring(1,arg2.length()-1);
    		checkGoldStandard= goldstandard.CheckDisjointness(Class1, Class2, goldstandard.getGoldStandard());
    	   logger.info("GoldStandard Comparison " + checkGoldStandard);
    	   if (checkGoldStandard.equals("T"))
    	   {
    		   numTrueGoldStandard++;
    	   }
    	   
    	   t=System.currentTimeMillis();
		}
		catch (QueryExceptionHTTP httpError)
		{
			logger.error("HTTP Error " + httpError.getMessage() + " making a SPARQL query.");
			httpError.printStackTrace();
			System.exit(1);
		}
		catch (JenaException jenaException)
		{
			logger.error("Jena Exception " + jenaException.getMessage() + " making a SPARQL query.");
			jenaException.printStackTrace();
			System.exit(1);
		}   
	
    	if(a != null)
		{
			// Save an XML report of the test:
		//	AxiomTest report = new AxiomTest();
    		dt=t-t0;
    		logger.info("Time to evaluate fitness: " + dt + "ms");
    		report.mapped="YES";
    		report.elapsedTime = dt;
			report.referenceCardinality = a.referenceCardinality;
			
			logger.info("num_Confirmation: " + a.numConfirmations);
			report.numConfirmations = a.numConfirmations;
			logger.info("num_Exceptions: " + a.numExceptions);
			report.numExceptions = a.numExceptions;
			//if(a.numConfirmations<100)
			//	report.confirmations = a.confirmations;
			//if(a.numExceptions<100)
			//	report.exceptions = a.exceptions;
			logger.info("Possibility: " + a.possibility().doubleValue());
			report.possibility = a.possibility().doubleValue();
		//	System.out.println("Possibility = " + report.possibility);
			logger.info("Necessity: " + a.necessity().doubleValue());
			report.necessity = a.necessity().doubleValue();
		//	System.out.println("Necessity = " + report.necessity); 
		    
		   //fit.setDouble(a.possibility().doubleValue());  //moi them vao 
		    	    
        }
    	
     	}
    	else
     	{
    		dt=t-t0;
    		logger.info("Time to evaluate fitness: " + dt + "ms");
    		report.mapped="NO";
    		report.elapsedTime = t - t0;
    		report.referenceCardinality = 0 ;
			report.numConfirmations = 0;
			report.numExceptions = 0;
			report.confirmations= null;
			report.exceptions= null;
			report.possibility=0.0;
		//	System.out.println("Possibility = " + report.possibility);
			report.necessity=0.0;
		//	System.out.println("Necessity = " + report.necessity); 
     	}
    	
     	double f = report.referenceCardinality*(report.possibility + report.necessity)/2;
    // 	double f = (report.possibility + report.necessity)/2;
    	BasicFitness fit = new BasicFitness(f,population.get(i));
	    fit.setIndividual(population.get(i));
	    fit.getIndividual().setValid(true);
	    population.get(i).setFitness(fit);
	    logger.info("Fitness function: "+ f);
	    report.fitness= f;
	   
	    if (f>0) {count ++;}
	  
	    // logger.info("Fitness:" + population.get(i).getFitness().getDouble());
	 //  logger.info("Fitness:" + fit.getDouble());
	    marshaller.marshal(report, xmlStream);
		xmlStream.flush();
	    i++; 
     	}	
        logger.info("+++++++++++++++++++++++++++++++++++++++++++++");
        logger.info("Number axioms are true to GoldStandard: " + numTrueGoldStandard);
    	     
    }
    
    int getcount()
    {
    	return count;
    }
    
    void update2 (GEIndividual indivi)  // evaluation fitness for each individual
    {
    	int i=0;
    	String arg1="";
    	String arg2="";
    	String Class1="";
        String Class2="";
        String checkGoldStandard="";
        // AxiomTest report = new AxiomTest();
     
      	 // 		if(indivi==null)
      		    
      	//		break;
      //	logger.info("generation:" + indivi.getAge());
     	logger.info("axiom: " + indivi.getPhenotype().getStringNoSpace());
     	logger.info("chromosome:"+ indivi.getGenotype().toString());
     	logger.info("...............................................................................");
      	if (indivi.isMapped())
      	{
      	
      	try
 		{
      		t0= System.currentTimeMillis();
     		a = AxiomFactory.create(indivi.getPhenotype());
     		t=System.currentTimeMillis();
     	
 		}
 		catch (QueryExceptionHTTP httpError)
 		{
 			logger.error("HTTP Error " + httpError.getMessage() + " making a SPARQL query.");
 			httpError.printStackTrace();
 			System.exit(1);
 		}
 		catch (JenaException jenaException)
 		{
 			logger.error("Jena Exception " + jenaException.getMessage() + " making a SPARQL query.");
 			jenaException.printStackTrace();
 			System.exit(1);
 		}   
 	
     	if(a != null)
 		{
 			// Save an XML report of the test:
 		//	AxiomTest report = new AxiomTest();
     		dt=t-t0;
     //		logger.info("Time to evaluate fitness: " + dt + "ms");
     		logger.info("mapped=YES");
     ///		logger.info("elapsedTime = dt");
 	     	referenceCardinality = a.referenceCardinality;
            logger.info("referenceCardinality" + referenceCardinality);
 	        logger.info("confirmation:" + a.numConfirmations);
 	        logger.info("numExceptions:" +  a.numExceptions);
 			//if(a.numConfirmations<100)
 			//	report.confirmations = a.confirmations;
 			//if(a.numExceptions<100)
 			//	report.exceptions = a.exceptions;
 			possibility= a.possibility().doubleValue();
 			logger.info("possibility " + possibility);
 		//	System.out.println("Possibility = " + report.possibility);
 			necessity = a.necessity().doubleValue();
 			logger.info("necessity : " + necessity );
 		//	System.out.println("Necessity = " + report.necessity); 
 		    
 		   //fit.setDouble(a.possibility().doubleValue());  //moi them vao 
 		    	    
         }
      	}
     	else
      	{
     		dt=t-t0;
     //		logger.info("Time to evaluate fitness: " + dt + "ms");
     //		logger.info("mapped= NO");
     //		logger.info("elapsedTime:" + (t - t0));
     //		logger.info("referenceCardinality = 0") ;
     		referenceCardinality=0;
 	//	    logger.info("numConfirmations = 0");
 		   // a.confirmations=null;
 	//		logger.info("numExceptions = 0");
 		  // a.exceptions=null;
 	//		logger.info("confirmations= null");
 		//	logger.info("exceptions= null");
 	//		logger.info("possibility=0.0");
 			//	System.out.println.("Possibility = " + report.possibility);
 	//		logger.info("necessity=0.0");
 		//	System.out.println("Necessity = " + report.necessity); 
      	}
     	
      	double f = referenceCardinality*(possibility + necessity)/2;
     	
     	BasicFitness fit = new BasicFitness(f,indivi);
 	    fit.setIndividual(indivi);
 	    fit.getIndividual().setValid(true);
 	    indivi.setFitness(fit);
 	    logger.info("fitness:" + f);
 	   
 	    if (f>0) {count ++;}
 	  
 	    // logger.info("Fitness:" + population.get(i).getFitness().getDouble());
 	 //  logger.info("Fitness:" + fit.getDouble());
 	  	
      	
    }
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
     }

}