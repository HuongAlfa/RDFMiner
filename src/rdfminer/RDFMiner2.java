package rdfminer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


import Individuals.GEChromosome;


import Individuals.Populations.SimplePopulation;
import Individuals.GEIndividual;
//import Operator.Operations.EliteOperationSelection;
import Individuals.Individual;
import Util.Random.MersenneTwisterFast;
import Util.Random.RandomNumberGenerator;

/**
 * @author NGUYEN THU HUONG
 * developed on the basic codes of Prof. Andrea Tettamanzi
 *
 */
public class RDFMiner2
{
	private static Logger logger = Logger.getLogger(RDFMiner2.class.getName());
	
	public static CmdLineParameters parameters = new CmdLineParameters();

	final private static String dbpedia = "http://dbpedia.org/sparql";
			// "http://localhost:8890/sparql";
			//"http://dbpedia.org/sparql";
	final private static String PREFIXES =
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
			"PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +     
			"PREFIX dbr: <http://dbpedia.org/resource/>\n" +
			"PREFIX dbp: <http://dbpedia.org/property/>\n" +
	     	"PREFIX dbo: <http://dbpedia.org/ontology>\n" ;
		/*	"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
			"PREFIX grddl: <http://www.w3.org/2003/g/data-view#>\n" +
			"PREFIX xml: <http://www.w3.org/XML/1998/namespace>\n";  */
	
	/**
	 * A SPARQL endpoint which can be used to query the RDF repository.
	 */
	public static SparqlEndpoint endpoint;
	
	/**a
	 * An executor to be used to submit synchronous tasks which might be subjected to a time-out. 
	 */
	public static ExecutorService executor;
	
	/**
	 * A service native method to query for CPU usage.
	 * <p>The name and implementation of this method are adapted from
	 * <a href="http://www.javaworld.com/article/2077361/learn-java/profiling-cpu-usage-from-within-a-java-application.html">this
	 * 2002 blog post</a>.</p>
	 * <p>The implementation in C language of this native method is contained in the two source files
	 * <code>rdfminer_RDFMiner.h</code> and <code>rdfminer_RDFMiner.c</code>.</p>
	 * 
	 * @return the number of milliseconds of CPU time used by the current process so far
	 */
	public static native long getProcessCPUTime();
	
	public AxiomTest report1;
	public Parameters_GE report2; 
	public Statistics report3;
	/**
	 * The entry point of the RDF Miner application.
	 * @throws JAXBException 
	 * @throws IOException 
	 */
	
	static void DeleteTwins(GEChromosome [] a, int n)   // Xoa di cac phan tu giong nhau

	{

	      for(int i=0;i<n-1;i++)

	    for( int k=i+1;k<n;k++)

	        if (a[k]==a[i])

	        {

	            for(int j=k;j<n-1;j++)

	            a[j]=a[j+1];

	                n--;

	            k--;

	        }
	}
	
	 static void dem (List<Individual> a, int n)  // dem so lan xuat hien cua tung chromosome
    {

        int[] fr1 = new int[n];
        int i, j, bien_dem;
        String ai, aj;
        for (i=0; i<n ; i++ )
        {
        	fr1[i]=-1;
        }
        logger.info("Đếm số lần xuất hiện của từng chromosome");
        logger.info("================================================================");
      
        for (i = 0; i < n; i++)
        {
            bien_dem = 1;
            for (j = i + 1; j < n; j++)
            {
                ai= a.get(i).getGenotype().toString();
                aj=a.get(j).getGenotype().toString();
            	if (ai.equals(aj))
                {
                    bien_dem++;
                    fr1[j] = 0;
                }
            }

            if (fr1[i] != 0)
            {
                fr1[i] = bien_dem;
            }
        }
        /* Tan suat xuat hien cua tung chromosome trong mang la */
        for (i = 0; i < n; i++)
        {
            if (fr1[i] != 0)
            {
                logger.info("Chromosome" + a.get(i).getGenotype().toString() + "   xuat hien  " + fr1[i] + " lan" );
            }
        }        

        } 
	 
	 static ArrayList<Individual>  distinctPopulation (SimplePopulation CanPop)
	    {
		
		 int i,j,n,k,m;
		 n= CanPop.size();
		 String ai,aj;
	
		 ArrayList<Individual> arrListIndividual = new ArrayList<>();
	   k=0;
	   m=0;
	   Individual ak,ak1;
	   for (i = 0; i < n; i++) 	
		{
		  arrListIndividual.add(CanPop.get(i));
		}
		for (i = 1; i < arrListIndividual.size()-m; i++)
		{
			for (j = 0; j < i; j++)
			{ 
				 ai= arrListIndividual.get(i).getPhenotype().toString();
	             aj= arrListIndividual.get(j).getPhenotype().toString();
	             
				if (ai.equals(aj))
				{
					m++;
					for (k = i; k < arrListIndividual.size()-m-1; k++) 
					{
						//ak= array.get(k);
						arrListIndividual.set(k,arrListIndividual.get(k+1));
						
					}
					arrListIndividual.remove(k);
					arrListIndividual.trimToSize();
		
					//n--;
							   }
			}
		}
				
	    	return  arrListIndividual;
	    }
	 
	 static void statistical (ArrayList<Individual> distinctIndividual)
	 
	 {
		 
	 }
	 
	 public static  ArrayList<Individual>   Mutation (ArrayList<Individual> listindividual, double proMutation)
	 {
		
		 RandomNumberGenerator rand =new MersenneTwisterFast(); ; 
	 	IntFlipMutation Mut = new IntFlipMutation(proMutation, rand);
	 		Mut.doOperation(listindividual);
		 return listindividual;
	 }

	 public static ArrayList <Individual>  Crossover(SimplePopulation CanPop, double proCrossover, int curGeneration, RandomAxiomGenerator2 rd, int diversity)
	    {
		
	    	RandomNumberGenerator rand =new MersenneTwisterFast(); ; 
	 		ArrayList<Individual> ListIndividual = new ArrayList<Individual>();
	 		SinglePointCrossoverAxiom Cross = new SinglePointCrossoverAxiom(proCrossover, rand);
	 		Cross.setFixedCrossoverPoint(true);
	 		int m=0;
	 	//	int l=0;
	 	//Crowding [] ListCrowd = new Crowding[CanPop.size()/2];
	 		while (m< CanPop.size()-1)
	 		{	
	 			
	 			GEIndividual indivi1= (GEIndividual) CanPop.get(m);
	 			GEIndividual indivi2 = (GEIndividual) CanPop.get(m+1);
	 			GEChromosome chromosome1 = new GEChromosome((GEChromosome)indivi1.getGenotype().get(0));
	 			GEChromosome chromosome2= new GEChromosome((GEChromosome)indivi2.getGenotype().get(0));
	 			
	 			GEIndividual parents1= new GEIndividual();
	 			GEIndividual parents2= new GEIndividual();
	 			parents1 = rd.AxiomIndividual(chromosome1, curGeneration);
	 			parents2= rd.AxiomIndividual(chromosome2, curGeneration);
	 			Cross.Crossover(indivi1, indivi2);	
	 			indivi1 = rd.AxiomIndividual((GEChromosome) indivi1.getGenotype().get(0), curGeneration);
	 			indivi2= rd.AxiomIndividual((GEChromosome) indivi2.getGenotype().get(0), curGeneration);
	 			
	 		//	ListCrowd [l] = crowd;
	 		  if (diversity==1)  // if using crowding method in survival selection
	 		  {	
	 			Crowding crowd = new Crowding(4, parents1, parents2, indivi1, indivi2);
	 			ListIndividual.add(crowd.SurvivalSelection()[0]);
	 		    ListIndividual.add(crowd.SurvivalSelection()[1]);
	 		  }	
	 		  else // if choosing children for the new population
	 		 //   l++;
	 		  {
	 			  ListIndividual.add(indivi1);
	 			  ListIndividual.add(indivi2);
	 		  }
	 			  
	 			  
	 			 m=m+2;
	 		    		 
	 		}	
	 	/*	int i;
	 		for (i=0; i<ListCrowd.length; i++)
        	{
        		
        			ListIndividual.add(ListCrowd[i].SurvivalSelection()[0]);
        			ListIndividual.add(ListCrowd[i].SurvivalSelection()[1]);
        		//	j=j+2;
        	}*/
	 		return ListIndividual;
	}
	 
	 
	 
	
	     
	static void  FitnessEvaluation(SimplePopulation CandidatePopulation, int curGeneration,AxiomTest report2, Statistics report3, Marshaller marshaller3, Marshaller marshaller1, FileOutputStream xmlStream1 ,FileOutputStream xmlStream2,GoldStandardComparison goldstandard ) throws JAXBException, IOException, SQLException
	{
		 logger.info("==================================================================");
	       logger.info("BEGIN EVALUATING INDIVIDUALS....");
	       logger.info("CALLING FitnessEvaluation -------------------->");
		
	        FitnessEvaluation fit = new FitnessEvaluation(CandidatePopulation);
		    fit.update(report2, marshaller1,xmlStream1,goldstandard);
	/*		logger.info("==================================================================");
			logger.info("CANDIDATE POPULATION WITH FITNESS VALUES");
		for (int k1=1; k1<= CandidatePopulation.size(); k1++)
			{ 
			logger.info("------------------------------------------------------------------");
			logger.info("Individual " + k1 );
			logger.info(" Axiom: " + CandidatePopulation.get(k1-1));
			logger.info("Chromosome: " + CandidatePopulation.get(k1-1).getGenotype());		
			logger.info("Fitness: " + CandidatePopulation.get(k1-1).getFitness().getDouble());
			}
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		logger.info("The number fitness > 0 of the generation n. " + curGeneration + " is :" + fit.getcount());
		logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); */
	report3.curGeneration = curGeneration;
//	report3.Count=fit.getcount();
	marshaller3.marshal(report3, xmlStream2);
	xmlStream2.flush();
	
	}
/*	 
	static void CrossoverOperation (SimplePopulation SelectedPopulation, GEChromosome[] ListChromosome)
	{
		logger.info("==================================================================");
		logger.info("DOING CROSSOVER WITH BEST INDIVIDUALS - SINGLE POINT TYPE");
 	
		RandomNumberGenerator rand =new MersenneTwisterFast(); ; 
 		
 		SinglePointCrossoverAxiom Cross = new SinglePointCrossoverAxiom(parameters.proCrossover, rand);
 		Cross.setFixedCrossoverPoint(true);
 		int m=0;
 		while (m< SelectedPopulation.size())
 		{	
 			GEIndividual indivi1= (GEIndividual) SelectedPopulation.get(m);
 			GEIndividual indivi2= (GEIndividual) SelectedPopulation.get(m+1);
 			Cross.Crossover(indivi1, indivi2);
 			logger.info("Chromosome : " + indivi1.getGenotype().get(0) );
 			logger.info("Chromosome : " + indivi2.getGenotype().get(0) );
 			ListChromosome[l]=(GEChromosome) indivi1.getGenotype().get(0);
 			ListChromosome[l+1]= (GEChromosome) indivi2.getGenotype().get(0);
 			l=l+2;	
 			m=m+2;
 		}		
	}
	*/
	 static SimplePopulation ParentsSelection_Elitilism (double elitedper, SimplePopulation CandidatePopulation)
	 {
		    	
			        int selectedsize;
		     
			       logger.info("==================================================================");
  		           logger.info(" SELECTING 20% ELITE INDIVIDUALS FOR THE NEW POPULATION ");

		     		selectedsize= (int)(elitedper* CandidatePopulation.size());
		     		logger.info("The size of elite population: " + selectedsize);
		     		logger.info("..................................................");
		     		EliteOperationSelection elite = new EliteOperationSelection(selectedsize);
		
		     		elite.doOperation(CandidatePopulation.getAll());
	
		     		for (int k=1; k<= elite.getSelectedPopulation().size(); k++)
		     		{
		     		logger.info("individual " + k );
					logger.info(elite.getSelectedPopulation().get(k-1).getGenotype());
					logger.info(elite.getSelectedPopulation().get(k-1).getFitness().getDouble());
					logger.info(elite.getSelectedPopulation().get(k-1));
					logger.info("-----------------------------------------------------------------------------------------");
		     		}
	     return (SimplePopulation) elite.getSelectedPopulation();
         
	 }
	 
	 static SimplePopulation RoulletWheel (SimplePopulation SelectedPopulation)
	 {
		 RandomNumberGenerator rng;
		 int size =  (int) (SelectedPopulation.size());
		 RandomNumberGenerator random;
		 random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
		 ProportionalRouletteWheel rl= new ProportionalRouletteWheel(size, random);
		 rl.doOperation(SelectedPopulation.getAll());
		
		 return (SimplePopulation) rl.getSelectedPopulation();
		 
	 }
	
	 public static SimplePopulation Resize(SimplePopulation Etilism_Population, SimplePopulation CandidatePopulation)
	    
	  { 
		  SimplePopulation SelectedPopulation = new SimplePopulation (CandidatePopulation.size()- Etilism_Population.size());
		  int tmp=0;
			
		  String elite;
		  String can;
		      for (int i=0; i<CandidatePopulation.size(); i++) 
		    { 
			      tmp=0;
		    	  for (int k=0; k<Etilism_Population.size(); k++)
		    	  	{
		    		  elite = Etilism_Population.get(k).getGenotype().get(0).toString();
		    		  can =  CandidatePopulation.get(i).getGenotype().get(0).toString();
		    		  	if (elite.equals(can)) 
		    		  		tmp++;
		    	  	}
		    	 if (tmp==0)
		    	 {
		    		 SelectedPopulation.add((GEIndividual) CandidatePopulation.get(i));
		    		
		    	 }
			 }
		  
				  return SelectedPopulation;
	  }
	 
	 
	 public static SimplePopulation shuffe (SimplePopulation Population)
	 {
		 java.util.Collections.shuffle(Arrays.asList(Population.getAll()));
		 return Population;
	 }
	/* thêm vào*/
	
	
	public static void main(String[] args) throws JAXBException, IOException, SQLException
	{
		// Configure the log4j loggers:
		PropertyConfigurator.configure("log4j.properties");
		
		// Parse the command-line parameters and options:
        CmdLineParser parser = new CmdLineParser(parameters);
        
        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(100);
        Parameters_GE report1= new Parameters_GE();
        AxiomTest report2=new AxiomTest();
        Statistics report3=new Statistics();
        try
        {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");

            // after parsing arguments, you should check
            // if enough arguments are given.
            // if( parameters.arguments.isEmpty() )
            //     throw new CmdLineException(parser,"No argument is given");

        }
        catch(CmdLineException e)
        {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java -jar miner.jar [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            return;
        }

		logger.info("This is RDF Miner, version 2.0");
		System.load ("/media/huong/DATA/These/Codes/rdfminer/librdfminer_RDFMiner2.so");
		//System.loadLibrary("librdfminer_RDFMiner");
		Marshaller marshaller1 = null;
		Marshaller marshaller2 = null;
		Marshaller marshaller3 =null;
		FileOutputStream xmlStream1 = null;
		FileOutputStream xmlStream2 = null;
	    FileOutputStream xmlStream3=null;
		endpoint = new SparqlEndpoint(dbpedia, PREFIXES);
		
		try {
		   // xmlStream1 = new FileOutputStream("results.xml", true); // here, 'true' means 'append'...
		    xmlStream1 = new FileOutputStream("result.xml",true); // contains result related to GE
		    xmlStream2 = new FileOutputStream("result_statistics.xml", true); // here, 'true' means 'append'...
		    JAXBContext context = JAXBContext.newInstance(AxiomTest.class);
		    marshaller1 = context.createMarshaller();
		    marshaller1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    JAXBContext context2 = JAXBContext.newInstance(Parameters_GE.class);   		   
		    marshaller2 = context2.createMarshaller();
		    marshaller2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    JAXBContext context3 = JAXBContext.newInstance(Statistics.class);   		   
		    marshaller3 = context3.createMarshaller();
		    marshaller3.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		catch(JAXBException e)
		{
			logger.error(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		catch(FileNotFoundException e)
		{
			logger.error("Could not find the XML result file: " + e.getMessage());
		}
		
		RandomAxiomGenerator2 generator = null;
		BufferedReader axiomFile = null;
		//AxiomPolulation population = null;
		if(parameters.axiomFile==null) 
		{
			if(parameters.useRandomAxiomGenerator) // nếu đã tồn tại Axiom được tạo ra ngẫu nhiên lúc trước thì tiếp tục tạo ra một Axioms mới dựa trên BNF 
			{
				logger.info("Initializing the random axiom generator with grammar " + parameters.grammarFile + "...");
				PrintWriter writer2 = new PrintWriter("classes.txt", "UTF-8");
				generator = new RandomAxiomGenerator2(parameters.grammarFile, writer2);
				writer2.close();
			}
			/*else if(parameters.subclassList!=null) //nếu có tồn tại một list subClasssOf axioms thì tiếp tục tạo ra một axiom mới dựa trên danh sách subclasses cho trước 
			{
				logger.info("Initializing the increasing TP axiom generator...");
				generator = new IncreasingTimePredictorAxiomGenerator(parameters.subclassList);
			} 
			else // các trường hợp còn lại tạo ra một axiom mới dựa trên ngữ pháp đã cho
			{
				logger.info("Initializing the candidate axiom generator...");
				generator = new CandidateAxiomGenerator(parameters.grammarFile);
		
			} */
		}
		else // nếu tồn tại Axiom trong file
		{
			logger.info("Reading axioms from file " + parameters.axiomFile + "...");
			try
			{
				// Try to read the status file
				axiomFile = new BufferedReader(new FileReader(parameters.axiomFile));
			}
			catch(IOException e)
			{
				logger.error("Could not open file " + parameters.axiomFile);
				return;
			}
		}
		executor = Executors.newSingleThreadExecutor();
		PrintWriter writer = new PrintWriter("result.txt", "UTF-8");
		/* GRAMMATICAL EVOLUTIONARY  */
		/* Parameters as the inputs of GE */
		logger.info("========================================================");
		logger.info("PARAMETERS AS THE INPUTS OF GE");
		writer.println("PARAMETERS AS THE INPUTS OF GE");
		//PopulationSize - population size
		report1.PopulationSize=parameters.populationsize;
		logger.info("========================================================");
		logger.info("POPULATION SIZE : " + parameters.populationsize);
		logger.info("========================================================");
		writer.println("POPULATION SIZE:" + parameters.populationsize );
		writer.close();
		int curGeneration=1;
		// GenerationSize - the number of generation
		report1.GenerationSize= parameters.numGeneration;
		logger.info("GENERATION NUMBER: " + parameters.numGeneration);
		logger.info("========================================================");
		// MaximumWraping -  the maximum wrapping number
		report1.MaximumWraping=generator.grammar.getMaxWraps();
		logger.info("MAXIMUM WRAPPING: " + parameters.maxWrapp );
		// proCrossover - Crossover Probability
		report1.proCrossover= parameters.proCrossover;
	    logger.info("CROSSOVER PROBABILITY: " + parameters.proCrossover );
	    report1.proMutation= parameters.proMutation;
	    logger.info("CROSSOVER MUTATION: " + parameters.proMutation );
	    marshaller2.marshal(report1, xmlStream1);
		xmlStream1.flush();
		
		GEChromosome[] ListChromosome = new GEChromosome [parameters.populationsize];
	//	Crowding [] ListCrowding= new Crowding[parameters.populationsize/2];
		ArrayList<Individual> ListIndividual = new ArrayList<Individual>();
		SimplePopulation CandidatePopulation;
		SimplePopulation Etilism_Population = null;
		SimplePopulation Roulette_Population;
		SimplePopulation SelectedPopulation;
		Statistics stat = new Statistics();
		GoldStandardComparison goldstandard = new GoldStandardComparison (parameters.GoldStandard);
		String[][] GoldStandardArr = goldstandard.ConvertExcelResultToArray();
        goldstandard.setGoldStandard(GoldStandardArr);
	//	int i;
	   	/* STEP 1 - CREATION candidate population */
	  	  while (curGeneration<=parameters.numGeneration)
	  { //while	  
        	    logger.info("==================================================================");         
				logger.info("CREATING CANDIDATE POPULATION IN GENERATION: "+ curGeneration);
				long t0= System.currentTimeMillis();
			//	report2.curGeneration=curGeneration;
			
				CandidatePopulation CanPop = new CandidatePopulation(parameters.populationsize,curGeneration,generator,parameters.typeInitialization, ListChromosome,parameters.initlenChromosome, parameters.maxlenChromosome,parameters.maxvalCodon,parameters.maxWrapp);
		        
				
				
				if (curGeneration > 1 )     // for other generation, using Crowding method for create new population  - Survival Selection
					//	ListCrowding = DivideCrowding (CandidatePopulation, parameters.proCrossover);
					CanPop.Renew(ListIndividual, curGeneration,CanPop, Etilism_Population);
			    // for the first generation
				    CandidatePopulation= CanPop.create();
		            CandidatePopulation.getAll().toString();
				long t= System.currentTimeMillis();
				ListChromosome = CanPop.GetListChromosome();
				//report.Population=(SimplePopulation) CandidatePopulation.getAll();
	            logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	            logger.info("TOTAL TIME TO CREATE CANDIDATE POPULATION: " + (t-t0) + "ms");
	            
	  //          dem(CandidatePopulation.getAll(),CandidatePopulation.size());
	            
	     /* STEP 2 - EVALUATION individuals in the population */         
	           FitnessEvaluation(CandidatePopulation,curGeneration,report2,report3,marshaller3,marshaller1,xmlStream1 ,xmlStream2,goldstandard);
		       // stat.SetCurGeneration(curGeneration);
		        stat.Compute_AverageFitness(curGeneration-1,CandidatePopulation);
		        stat.Compute_Fitness_greater_threshold(parameters.threshold,CandidatePopulation,curGeneration-1);
				stat.Compute_Coefficient_Distinct(CandidatePopulation, curGeneration-1);
		        ArrayList<Individual> distinct =distinctPopulation(CandidatePopulation);
		/*		logger.info("mang sau bi xoa phan tu trung");
				for (i = 0; i < distinct.size(); i++) 
				{
					logger.info(distinct.get(i).toString());
				}
		*/	   	    	
	   if (curGeneration <= parameters.numGeneration-1)
	    /* STEP 3 - SELECTION OPERATION - Reproduce Selection - Parent Selection*/ 
		 { // if
		        	if (parameters.typeselect==1) /* Etilism method, which copies the best chromosome( or a few best chromosome) to new population. The rest done classical way. 
					                          it prevents losing the best found solution */
				                             
		        	{
		        		Etilism_Population = ParentsSelection_Elitilism (parameters.eliteper,CandidatePopulation);
		        //		CanPop.AddBestListChromosome();
		        		SelectedPopulation= Resize(Etilism_Population,CandidatePopulation);
		        	}
		        	else  // Normal crossover way
		        		SelectedPopulation = CandidatePopulation;
			
	    /* STEP 4 - CROSSOVER OPERATION */ 
	   //Crossover single point between 2 individuals of the selected population	
		        	Roulette_Population = RoulletWheel(SelectedPopulation);
		           	ListIndividual = Crossover (Roulette_Population, parameters.proCrossover,curGeneration,generator,parameters.diversity);
		           
		        	// CrossoverOperation (SelectedPopulation,ListChromosome);
	   /* STEP 4 - MUTATION OPERATION */ 
		        	
				   ListIndividual= Mutation(ListIndividual,parameters.proMutation);
		 		// delete duplicated elements in ListChromosome
		 		
		 	/*	if (parameters.twin == 0)
		 		{
		 			int templength=ListChromosome.length;
		 			DeleteTwins(ListChromosome,ListChromosome.length);
		 			if (ListChromosome.length< templength)
		 			{
		 				int z=0;
		 				RandomNumberGenerator random;
		 				random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
		 				while (z < templength - ListChromosome.length)		
		 				{
		 					GEChromosome chromosome =new GEChromosome(parameters.initlenChromosome);
		 					chromosome.setMaxCodonValue(parameters.maxvalCodon);
		 					chromosome.setMaxChromosomeLength(parameters.maxlenChromosome);
		 					for(int i = 0; i<parameters.initlenChromosome; i++)   //1000
							{	
								chromosome.add( Math.abs(random.nextInt()));//  typeInitialization=1
							}	
		 					ListChromosome[ListChromosome.length+1]= chromosome;
		 					z++;
		 				}
		 			}
		 		}
		 		*/
		 		// shuffle list of chromosomes after do selection of chromosomes for the next generation
		 /*		if( parameters.shuffle==1)
		 		{
		 			java.util.Collections.shuffle(Arrays.asList(ListChromosome));
		 		
		 			logger.info("List chromosome after shuffling" );
		 			logger.info("..................................");
		 			int v=0;
		 			while (v < ListChromosome.length)
		 			{
		 				logger.info("Chromosome:" + ListChromosome[v].toString());	
		 				v++;
		 			}
		 		}
		 		*/
		 		//turn to the next generation
		 		curGeneration++;
			}
			else
				{
				logger.info("===============================STATISTICS========================================================================");
				logger.info("The average fitness values over " + parameters.numGeneration + "  generations");
				logger.info(" Generation    Average fitness values");
				for (int dem1=1; dem1<=parameters.numGeneration; dem1++ )
				{
					logger.info("   " + dem1 + "              " + stat.getListAverageFitness().get(dem1-1).doubleValue());
				}
				logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				logger.info("The number of axioms having fitness greater than threshold= " + parameters.threshold + " over " + parameters.numGeneration + "  generations");
				logger.info(" Generation    The number of axioms");
				for (int dem1=1; dem1<=parameters.numGeneration; dem1++ )
				{
					
					logger.info( "   "+ dem1 + "                        " + stat.getListCountThreshold().get(dem1-1).intValue());
				}	
				
				logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				logger.info("Diversity in the population of axioms over " + parameters.numGeneration + "  generations");
				logger.info(" Generation    Coefficient diversity");
				for (int dem1=1; dem1<=parameters.numGeneration; dem1++ )
				{
					
					logger.info( "   "+ dem1 + "                        " + stat.getListDiversity().get(dem1-1).doubleValue());
				}	
			    logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				logger.info("DONE!");
				break;
				}
				
	  } //while	
	//	logger.info(" New Population: " + elite.getSelectedPopulation().getAll());
	    
	     // turn to the next generation
		
      // marshaller2.marshal(report1, xmlStream2);
      // marshaller1.marshal(report2, xmlStream2);
      // xmlStream2.flush();
	System.exit(0);
	}
	
	
}

