/**
 * 
 */
package rdfminer;

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
 * @ modified in 7/11/2017 by Nguyen Thu Huong
 *
 */
public class RandomAxiomGenerator extends AxiomGenerator
{
	private static Logger logger = Logger.getLogger(RandomAxiomGenerator.class.getName());

	/**
	 * The random number generator used to generate axioms.
	 */
	protected RandomNumberGenerator random;
	
	/**
	 * Constructs a new axiom generator for the language described by the given grammar.
	 * 
	 * @param fileName the name of the file containing the grammar.
	 */
	public RandomAxiomGenerator(String fileName)
	{
		super(fileName);
			
		// Set up a pseudo-random number generator
		random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
	}
	
	
	// 
	
	
	/**
	 * Generate the next random axiom.
	 * 
	 * @return a random axiom
	 */
	public Phenotype nextAxiom()
	{
		GEChromosome chromosome;
		
		boolean valid;
		do
		{  
			chromosome = new GEChromosome(3);  //1000
			chromosome.setMaxCodonValue(200);//(Integer.MAX_VALUE);
			chromosome.setMaxChromosomeLength(20);  //1000
			for(int i = 0; i<3; i++)   //1000
			{	
				chromosome.add(Math.abs(random.nextInt(200)));// thay doi cho nay
				                 
			}		
		    grammar.setGenotype(chromosome);
			grammar.setPhenotype(new Phenotype());
			
			valid = grammar.genotype2Phenotype(true);
					
/*			if(!valid) logger.warn("Invalid derivation!");
			logger.info("Codons: " + grammar.getUsedCodons() +
					"\n\tWraps: " + grammar.getUsedWraps() +
					"\n\tMax current tree depth: " + grammar.getMaxCurrentTreeDepth() +
					"\n\tName: " + grammar.getName());
*/		}
		while(!valid);
		return grammar.getPhenotype();
	}
	 public GEIndividual AxiomIndividual()  // added by Nguyen Thu Huong 
	 {
		 GEChromosome chromosome;
		 GEIndividual individual;
		 boolean valid;
	do
	{  
		  chromosome =new GEChromosome(3);
		 chromosome.setMaxCodonValue(200);//(Integer.MAX_VALUE);
		 chromosome.setMaxChromosomeLength(3);  //1000
			for(int i = 0; i<3; i++)   //1000
			{	
				chromosome.add(Math.abs(random.nextInt(200)));// thay doi cho nay
				                 
			}	
			 
		 grammar.setGenotype(chromosome);
		 grammar.setPhenotype(new Phenotype());
		 valid = grammar.genotype2Phenotype(true);
	}
	while(!valid);
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
		 individual.setUsedCodons(chromosome.getUsedGenes());
		 individual.setUsedWraps(chromosome.getUsedWraps());
	
		 //logger.info("Mapper: " + individual.getMapper());
		 //logger.info("Generation: " + individual.getAge());
		 //logger.info("Phenotype: " + individual.getPhenotype());
		 //logger.info("Genotype: " + individual.getGenotype());
		// logger.info("UsedCodons: " + individual.getUsedCodons());
		// logger.info("UsedWraps: " + individual.getUsedWraps());
		return individual;
		
	 }
	
}
