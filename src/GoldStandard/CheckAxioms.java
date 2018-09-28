package GoldStandard;
public class CheckAxioms{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(CheckDisjointClass());

	}
	
	public static String CheckDisjointClass(string part1,)
	{
		String part1 = "[<http://dbpedia.org/ontology/Award>]" ;
		String part2 = "[<http://dbpedia.org/ontology/SoccerManager>]";
		String result="";
		if (!part1.contains("ObjectUnionOf") && !part1.contains("INTERSECTION(") && !part2.contains("ObjectUnionOf") && !part2.contains("INTERSECTION("))
		result= ;
		//checkAtomicAxiom(part1,part2);
	/*	else
		{ 
			if (part1.contains("ObjectUnionOf"))
			{
				result= "part1 contains ObjectUnionOf";
			}
			if (part1.contains("INTERSECTION"))
					{
				result="part2 contains INTERSECTION";
				
					}		 
		}
		return result; */
	}
    
}
