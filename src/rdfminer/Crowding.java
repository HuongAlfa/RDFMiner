package rdfminer;

import org.apache.log4j.Logger;

import Individuals.GEChromosome;
import Individuals.GEIndividual;

public class Crowding {
	private static Logger logger = Logger.getLogger(Crowding.class.getName());
	
	protected int size;
	protected int distance_p1_c1;
	protected int distance_p2_c2;
	protected int distance_p1_c2;
	protected int distance_p2_c1;
	protected GEIndividual parents1;
	protected GEIndividual parents2;
	protected GEIndividual child1;
	protected GEIndividual child2;
		
	public Crowding(int size, GEIndividual parents1, GEIndividual parents2,GEIndividual child1, GEIndividual child2) 
	{
		this.size= size;
		this.parents1=parents1;
		this.parents2=parents2;
		this.child1=child1;
		this.child2=child2;
		this.distance_p1_c1 = this.distance(this.parents1, this.child1); 
		this.distance_p2_c2 =  this.distance(this.parents2, this.child2);
		this.distance_p1_c2 = this.distance(this.parents1, this.child2);
		this.distance_p2_c1 = this.distance(this.parents2, this.child1);
		
	}
    GEIndividual [] SurvivalSelection()
   {
	  int d1, d2;
	  GEIndividual [] ListSurvival = new GEIndividual[2];
	  d1 = distance_p1_c1 + distance_p2_c2;
	  d2 = distance_p1_c2 + distance_p2_c1;
	  logger.info("parent1: " + parents1.getGenotype().get(0));
	  logger.info("parent2: " + parents2.getGenotype().get(0));
	  logger.info("child1: " + child1.getGenotype().get(0));
	  logger.info("child2: " + child2.getGenotype().get(0));
	  logger.info("distance (p1,c1):" + distance_p1_c1);
	  logger.info("distance (p2,c2):" + distance_p2_c2);
	  logger.info("distance (p1,c2):" + distance_p1_c2);
	  logger.info("distance (p2,c1):" + distance_p2_c1);
	  logger.info("d1: " + d1);
	  logger.info("d2: " + d2);
	  FitnessEvaluation fit = new FitnessEvaluation();
	  fit.update2(parents1);
	  fit.update2 (parents2);
	  fit.update2(child1);
	  fit.update2(child2);
	   if (d1 >= d2)
	   { 
		  ListSurvival[0]= compare(parents1,child1);
		  ListSurvival[1]=compare(parents2,child2);
	
	   }
	   else
	   {   ListSurvival[0]= compare(parents1,child2);
	   	   ListSurvival[1]=compare(parents2,child1);
	   }		   
	return ListSurvival;
   }
   
    int distance (GEIndividual a, GEIndividual b)
	 {
		 GEChromosome chromosome_a= (GEChromosome) a.getGenotype().get(0);
		 GEChromosome chromosome_b= (GEChromosome) b.getGenotype().get(0);
         int[] a1=chromosome_a.toArray();
         int[] b1=chromosome_b.toArray();
	     int n = a1.length;
         int i,j,d;
		 d=0;
		 for (i=0;i<n; i++)
		 {
			 for (j=0;j<n; j++)
			 {
			 if (b1[j]==a1[i])
			 {
				 d++;
			 }
			 	 
		 }
			 
		 } 
	 return d;
	 }
   
     GEIndividual compare (GEIndividual parent, GEIndividual child)
   {
    	 
    	 logger.info("Fitness of Parent: " + parent.getFitness().getDouble());   
    	 logger.info("Fitness of Child: " + child.getFitness().getDouble());
    	 if (parent.getFitness().getDouble()<= child.getFitness().getDouble())
	   {
		 
		   return child; 
	   }
	   else  
			   return parent;
	   
   }

}
