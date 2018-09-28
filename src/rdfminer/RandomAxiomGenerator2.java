/**
 * 
 */
package rdfminer;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import Individuals.GEChromosome;
import Individuals.GEIndividual;
import Mapper.GEGrammar;
import Individuals.Phenotype;
import Individuals.FitnessPackage.Fitness;
import Individuals.Genotype;
import Util.Random.MersenneTwisterFast;
import Util.Random.RandomNumberGenerator;
//import org.apache.log4j.Logger;

/**
 * A generator of random axioms for a given logical language.
 * <p>The syntax of the logical language from which the axioms are to be
 * randomly extracted is given by a functional-style grammar expressed in the
 * <a href="http://www.w3.org/TR/2012/REC-owl2-syntax-20121211/#BNF_Notation">extended
 * BNF notation</a> used by the <a href="http://www.w3.org/">W3C</a>.</p>
 *  
 * @author Andrea G. B. Tettamanzi
 * @ modified in 7/12/2017 by Nguyen Thu Huong
 *
 */
public class RandomAxiomGenerator2 extends AxiomGenerator2
{
	private static Logger logger = Logger.getLogger(RandomAxiomGenerator2.class.getName());

	/**
	 * The random number generator used to generate axioms.
	 */
	protected RandomNumberGenerator random;
	
	/**
	 * Constructs a new axiom generator for the language described by the given grammar.
	 * 
	 * @param fileName the name of the file containing the grammar.
	 * @throws IOException 
	 * @throws  
	 * @throws FileNotFoundException 
	 */
	public RandomAxiomGenerator2(String fileName, PrintWriter writer) throws IOException 
	{
		super(fileName, writer);
			
		// Set up a pseudo-random number generator
		random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
	}
	
	
	// 
	/**
	 * Generate the next random axiom.
	 * 
	 * @return a random axiom
	 */

	 public GEIndividual AxiomIndividual(GEChromosome chromosome, int generation)  // added by Nguyen Thu Huong 
	 {
		 //GEChromosome chromosome;
		 GEIndividual individual;
		 boolean valid;
		 int i=1;
	do
	{  
	//	 chromosome =new GEChromosome(3);
	//	 chromosome.setMaxCodonValue(200);//(Integer.MAX_VALUE);
	//	 chromosome.setMaxChromosomeLength(3);  //1000
	//		for(int i = 0; i<3; i++)   //1000
	//		{	
	//			chromosome.add(Math.abs(random.nextInt(200)));// thay doi cho nay
				                 
	//		}	
			 // viet dong lenh cho nay de dung wrapp.
		 grammar.setGenotype(chromosome);
		 grammar.setPhenotype(new Phenotype());
		 valid = grammar.genotype2Phenotype(true);
		 i++;
	}
	while ((!valid) && (i < grammar.getMaxWraps()));
		 Genotype gp = new Genotype (1,chromosome);
		 Phenotype ph = new Phenotype();
		 Fitness f = null  ;
		 GEGrammar gr = (GEGrammar) grammar;
		// logger.info(gr);
		 individual =new GEIndividual();
	     individual.setMapper(gr);
		 individual.setGenotype(gp);
         individual.setPhenotype(grammar.getPhenotype());
        // individual.map(1);  		
		
		// individual.setPhenotype(grammar.getPhenotype()); 
		 individual.setValid(true);
         individual.setUsedCodons(chromosome.getUsedGenes());
    	 individual.setUsedWraps(grammar.getUsedWraps()-1);
    	 individual.setAge(generation);
         if (valid ==true) 
         {
        	 individual.setMapped(true);
         }
         
		 //logger.info("Mapper: " + individual.getMapper());
		 //logger.info("Generation: " + individual.getAge());
		 //logger.info("Phenotype: " + individual.getPhenotype());
		 //logger.info("Genotype: " + individual.getGenotype());
		// logger.info("UsedCodons: " + individual.getUsedCodons());
		//logger.info("UsedWraps: " + individual.getUsedWraps());
		return individual;
		
	 }
	
}
