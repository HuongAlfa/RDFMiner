package rdfminer;

import java.util.ArrayList;

import Individuals.GEChromosome;
import Individuals.GEIndividual;
import Individuals.Genotype;
import Individuals.Individual;
import Individuals.Populations.SimplePopulation;
import Mapper.GEGrammar;
import Operator.CrossoverModule;
import Operator.Operations.SinglePointCrossover;
import Util.Random.MersenneTwisterFast;
import Util.Random.RandomNumberGenerator;

public class SinglePointCrossoverAxiom extends SinglePointCrossover{

	public SinglePointCrossoverAxiom(double prob, RandomNumberGenerator m) {
		super(prob, m);
		// TODO Auto-generated constructor stub
	}

	void Crossover(GEIndividual Individual1, GEIndividual Individual2)
	{
		    GEChromosome c1 = (GEChromosome) Individual1.getGenotype().get(0);
	        GEChromosome c2 = (GEChromosome) Individual2.getGenotype().get(0);

	        makeNewChromosome(c1, c2, c1.size(), c2.size());

	        Genotype g1 = new Genotype();
	        Genotype g2 = new Genotype();
	        g1.add(c1);
	        g2.add(c2);
	        Individual1.setGenotype(g1);
	        Individual2.setGenotype(g2);
	        

	}
	
	
}
