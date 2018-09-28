package GoldStandard;

import java.util.ArrayList;

import Individuals.Individual;

public class NodeClass {
	
	protected String value;
	ArrayList<NodeClass> descendents = new ArrayList<NodeClass>();
	protected int layer; 
	NodeClass(String value, int d)
	{
		this.value=value;
		ArrayList<NodeClass> a = new ArrayList<NodeClass>() ;
		this.descendents= a;
		this.layer = d;
		
	}
	
	void pushDecendents(NodeClass descendents)
	{
		
		this.descendents.add(descendents);
	}
    ArrayList<NodeClass> getAllDescendents()
    {
    	ArrayList<NodeClass> a = new ArrayList<NodeClass>();
    	for (int i=0; i <this.descendents.size(); i++)
    	a.add(this.descendents.get(i));
      	return a;
    	
    }
    void setLayer(int layer)
    {
    	this.layer= layer;
    }
    int getLayer()
    {
    	return layer;
    }
    String getValue()
    {
    	return value;
    }

}
      