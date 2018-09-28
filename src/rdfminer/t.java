package rdfminer;

import Individuals.GEChromosome;
import Individuals.GEIndividual;
import Individuals.Genotype;
import Individuals.Phenotype;
import Individuals.FitnessPackage.Fitness;
import Mapper.GEGrammar;
import Util.Random.MersenneTwisterFast;
import Util.Random.RandomNumberGenerator;

public class t {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         GEGrammar grammar = new GEGrammar();
         RandomNumberGenerator random = new MersenneTwisterFast(System.currentTimeMillis() & 0xFFFFFFFF);
		 GEChromosome chromosome;
		 GEIndividual individual; 
		chromosome =new GEChromosome(3);
		 chromosome.setMaxCodonValue(200);//(Integer.MAX_VALUE);
		 chromosome.setMaxChromosomeLength(3);  //1000
			for(int i = 0; i<3; i++)   //1000
			{	
				chromosome.add(Math.abs(random.nextInt(200)));// thay doi cho nay
				                 
			}	
		Genotype gp = new Genotype (1,chromosome);
		 Phenotype ph = new Phenotype();
		 Fitness f = null;
		 GEGrammar gr = (GEGrammar) grammar;
		// logger.info(gr);
		 individual =new GEIndividual (gr,ph,gp,f);
		 System.out.print(individual);
	}

}
