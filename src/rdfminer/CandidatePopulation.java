package rdfminer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Policy.Parameters;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import Individuals.Chromosome;
import Individuals.GEChromosome;
import Individuals.GEIndividual;
import Individuals.Genotype;
import Individuals.Individual;
import Individuals.Phenotype;
import Individuals.Populations.SimplePopulation;
import Util.Random.MersenneTwisterFast;
import Util.Random.RandomNumberGenerator;

/*
 * 
 *  @author: NGUYEN THU HUONG 
 *
 *
 *
*/
public class CandidatePopulation {

	private static Logger logger = Logger.getLogger(CandidatePopulation.class.getName());
	
	protected int size;
	protected int generation;
	protected RandomAxiomGenerator2 generator;
	protected GEChromosome [] ListChromosome;
    protected int maxlenChromosome;
    protected int maxWrapp;
    protected int maxvalCodon;
    protected int typeInitialization;
    protected int initlenChromosome;
    
	public CandidatePopulation(int size, int generation,  RandomAxiomGenerator2 generator, int typeInitialization,GEChromosome [] ListChromosome, int initlenChromosome, int maxlenChromosome, int maxvalCodon,int maxWrapp)
	{
		this.size=size;              // size of the population
		this.generation=generation; //current generation
		this.generator=generator;
		this.ListChromosome = ListChromosome;
		this.maxlenChromosome=maxlenChromosome;
		this.maxvalCodon=maxvalCodon;
		this.maxWrapp=maxWrapp;
		this.typeInitialization=typeInitialization;
		this.initlenChromosome= initlenChromosome;
	}

	public SimplePopulation create()	 
	{
		GEChromosome chromosome; 
	
		GEIndividual individual;
		
		/* developed only in the case of random initialization  - typeInitialization=1
		 * Later need to develop other type of initialization.
		 */
		
	  if (generation ==1) // the first time to create candidate population 
		{ 
		  
				RandomNumberGenerator random;
				random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
				
			
			int n=0;
			while(n < size)
		
		     {
			chromosome =new GEChromosome(initlenChromosome);
			chromosome.setMaxCodonValue(maxvalCodon);
			chromosome.setMaxChromosomeLength(1000);  //1000
				for(int i = 0; i<initlenChromosome; i++)   //1000
				{	
					chromosome.add( Math.abs(random.nextInt(maxvalCodon)));//  typeInitialization=1
					                 
				}	
			ListChromosome[n]= chromosome;
			n++;
		    }
		}
			 
	SimplePopulation CandidatePopulation = new SimplePopulation (size);
			
		//	Axiom a = null;
		//	String axiomName = null;
		    // long t0 = System.currentTimeMillis();
			//long t0 = getProcessCPUTime();
		int j=0;
		while (j<size)
		{
			if(generator!=null)
		
			{   
			    individual= generator.AxiomIndividual(ListChromosome[j], generation);
			   // boolean t= CandidatePopulation.contains(individual);
			   // while (t)
		/*	    {
			    	random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
			    	chromosome =new GEChromosome(1000);
					chromosome.setMaxCodonValue(Integer.MAX_VALUE);
					chromosome.setMaxChromosomeLength(1000);  //1000
						for(int i = 0; i<1000; i++)   //1000
						{	
							chromosome.add(Math.abs(random.nextInt()));// thay doi cho nay
							                 
						}	
					ListChromosome[j]= chromosome;
				    individual= generator.AxiomIndividual(ListChromosome[j]);
			}
			*/
				
			   // individual.setAge(generation);
			    CandidatePopulation.add(individual);
			    Phenotype axiom = CandidatePopulation.get(j).getPhenotype();
				Genotype gp = CandidatePopulation.get(j).getGenotype();
			    Chromosome chro= gp.get(0);
			
			if(axiom==null)
					break;
			    
			    logger.info("Generation: " + CandidatePopulation.get(j).getAge());
			    logger.info("Individual: " + CandidatePopulation.get(j) + " is added to the population");
			    logger.info("Chromosome of this individual: " + chro);
			    logger.info("Used wraps: " + individual.getUsedWraps());
			    logger.info("------------------------------------------------------------------------------");
			    j++;
			}   
		      //axiomName = axiom.getStringNoSpace();
				//logger.info("Individual: " + generator.AxiomIndividual());
	  
		}
		return CandidatePopulation;
	}
	GEChromosome [] GetListChromosome () 
	{
		
		return ListChromosome;
	}
    public  void Renew(ArrayList <Individual> ListIndividual, int curGeneration, CandidatePopulation CanPop, SimplePopulation Etilism_Population)
    {
    	
    	
    	AddBestListChromosome(Etilism_Population);
    	int i=0;
    	int j=Etilism_Population.size();
      	for (i=0; i<ListIndividual.size(); i++)
    	{
    		
    			CanPop.ListChromosome [j]= (GEChromosome) ListIndividual.get(i).getGenotype().get(0);
    			
    		//	j=j+2;
    	}
    	   	
    //	CandidatePopulation CanPop = new CandidatePopulation(j,curGeneration,generator,typeInitialization, ListChromosome,initlenChromosome, maxlenChromosome,maxvalCodon,maxWrapp);
        
       // SimplePopulation NewCandidatePopulation =CanPop.create();
    	
    }
    
   public void AddBestListChromosome(SimplePopulation Etilism_Population)
   {
	   int i = 0;
	   while (i<Etilism_Population.size())
	   {
		   ListChromosome[i]= (GEChromosome) Etilism_Population.get(i).getGenotype().get(0);
		   i++;
	   }
	}
  
    
}

