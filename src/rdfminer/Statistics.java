package rdfminer;

import javax.xml.bind.annotation.XmlRootElement;

import Individuals.Individual;
import Individuals.Populations.SimplePopulation;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * @author NGUYEN Thu Huong
 * Dec 21, 2017
 */
@XmlRootElement
public class Statistics 
{
    public int curGeneration;
    public int Count_Threshold;  //count the finess > threshold
    public double Average_Fitness; 
    public  ArrayList<Double> ListAverageFitness; 
    public ArrayList<Integer> ListCount_threshold;
    public ArrayList<Double> ListDiversity;

	public Statistics() {
		// TODO Auto-generated constructor stub
		this.curGeneration=0;
		this.Count_Threshold=0;
		this.Average_Fitness=0.0;
		this.ListAverageFitness=new ArrayList<Double>();
		this.ListCount_threshold=new ArrayList<Integer>();
		this.ListDiversity=new ArrayList<Double>();
		
	}
	
	void SetAverageFitness (int averageFitness)
	{
		this.Average_Fitness=averageFitness;
	}
	void AddListAverageFitness (double averageFitness, int curGeneration)
	{
		this.ListAverageFitness.add(curGeneration,averageFitness);
	}
	void SetCountThreshold (int Count_Threshold)
	{
		this.Count_Threshold=Count_Threshold;
	}
	void AddListCountThreshold (int Count_Threshold, int curGeneration)
	{
		this.ListCount_threshold.add(curGeneration, Count_Threshold);
	}
	void AddListDiversity (double kof_diversity, int curGeneration)
	{
		this.ListDiversity.add(curGeneration,kof_diversity);
	}
	ArrayList<Integer> getListCountThreshold()
	{
		return this.ListCount_threshold;
	}
	ArrayList<Double> getListAverageFitness()
	{
		return this.ListAverageFitness;
	}
	ArrayList<Double> getListDiversity()
	{
		return this.ListDiversity;
	}
	double Compute_AverageFitness (int curGeneration, SimplePopulation CanPop)
	{
		double averageFitness=0.0;
		double sumFitness=0.0;
		for (int i=0; i<CanPop.size(); i++)
		{
			sumFitness += CanPop.get(i).getFitness().getDouble(); 
			
		}
			averageFitness= sumFitness/CanPop.size();
			this.AddListAverageFitness(averageFitness,curGeneration);
			
		return averageFitness;
	}
	
	int Compute_Fitness_greater_threshold(double threshold_fitness, SimplePopulation CanPop, int curGeneration)
	{
		int count=0;
		for (int i=0; i<CanPop.size(); i++)
		{
			if( CanPop.get(i).getFitness().getDouble()> threshold_fitness) 
			{
				count++;
			}
		}
		this.AddListCountThreshold(count, curGeneration);				
		return count;
	}
	
	double Compute_Coefficient_Distinct(SimplePopulation CanPop, int curGeneration)
	    {
		
		 int i,j,n,k,m;
		 double n1= (double) CanPop.size();
		 String ai,aj;
	
		 ArrayList<Individual> arrListIndividual = new ArrayList<>();
	   k=0;
	   m=0;
	 
	   for (i = 0; i < CanPop.size(); i++) 	
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
		
		double kof = (double)(arrListIndividual.size()/n1) ;
		this.AddListDiversity(kof, curGeneration);
		return kof;
		
	    }
}
