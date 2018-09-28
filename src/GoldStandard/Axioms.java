package GoldStandard;

public class Axioms {
protected String part1;
protected String part2;
public Axioms(String Axiom)
{
	this.part1= splitAxiom(Axiom)[1];
	this.part2=splitAxiom(Axiom)[2];
}

    public static String [] splitAxiom (String Axiom)
{
	String tokens[];
	String regex = "[ ]"; 
	tokens=Axiom.split(regex);
	for (int i =1; i< tokens.length; i++)
	{
	 System.out.println(tokens[i]);
		
	}
	return tokens;
}
}
