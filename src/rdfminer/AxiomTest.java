/**
 * 
 */
package rdfminer;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A wrapper for an axiom test report, to be used, for instance, for XML serialization.
 * @author Andrea G. B. Tettamanzi
 *
 */
@XmlRootElement
public class AxiomTest
{
	public String axiom, chromosome, mapped;
	public int referenceCardinality, numConfirmations, numExceptions;
	public List<String> exceptions, confirmations;
	public double possibility, necessity, fitness;
	public long elapsedTime; // the time it took to test the axiom, in ms.
	public int generation;
	AxiomTest()
	{
		axiom = "";
		chromosome="";
		mapped="";
		referenceCardinality = numConfirmations = numExceptions = 0;
		exceptions = confirmations = null;
		possibility = necessity = fitness= 0.0;
		elapsedTime = 0L;
		generation=0;
	}
}
