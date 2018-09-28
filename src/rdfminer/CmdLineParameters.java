/**
 * 
 */
package rdfminer;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * A container of command line parameters and options.
 * 
 * @author Andrea G. B. Tettamanzi
 *
 */
public class CmdLineParameters
{
	@Option(name = "-a", aliases = { "--axioms" },
			usage = "test axioms contained in this file",
			metaVar = "AXIOMFILE")
	public String axiomFile = null;

	/**
	 *  The angular coefficient to be used for dynamic time capping of axiom test.
	 *  <p>If this parameter is zero, time capping is performed using the value of the {@link #timeOut} parameter.</p>
	 *  <p>If this parameter is different from zero, its value is taken to mean the angular coefficient <var>b</var>
	 *  of the linear equation <var>T</var> = <var>a</var> + <var>b</var>TP,
	 *  where <var>a</var> is the value of the {@link #timeOut} parameter
	 *  and TP is the <em>time predictor</em>, computed, for subsumption axioms,
	 *  as the product of the reference cardinality of the subclass and of the number
	 *  of classes sharing at least one instance with it.</p>
	 */
	@Option(name = "-d", aliases = { "--dynamic-timeout" },
			usage = "use a dynamic time-out for axiom testing",
			metaVar = "ANGULAR_COEFF")
	public double dynTimeOut = 0.0;
	
	@Option(name = "-g", aliases = { "--grammar" },
			usage = "use this file as the axiom grammar",
			metaVar = "GRAMMAR")
	public String grammarFile = "OWL2Axiom-test3.bnf";

	@Option(name = "-o", aliases = { "--output" },
			usage = "output results to this XML file",
			metaVar = "RESULTFILE")
	public String resultFile = "results.xml";
	
	@Option(name = "-r", aliases = { "--random"},
			usage = "test randomly generated axioms")
	public boolean useRandomAxiomGenerator = true;

	@Option(name = "-s", aliases = { "--subclasslist"},
			usage = "test subClassOf axioms generated from the list of subclasses in this given file",
			metaVar = "FILE")
	public String subclassList = null;

	@Option(name = "-t", aliases = { "--timeout" },
			usage = "use this time-out (in minutes) for axiom testing",
			metaVar = "MINUTES")
	public long timeOut = 0;
	
    // receives other command line parameters than options
    @Argument
    public List<String> arguments = new ArrayList<String>();
    
    // Parameters for GE
    
    
    /** List of parameters as the input for GEn operation
     * 
     */
    
    public int populationsize = 6;
    
    public int numGeneration = 3;
    
    public int initlenChromosome = 10;
    
    public int maxlenChromosome = 51;
   
    public int maxWrapp = 2;
    
    public int maxvalCodon=  500; //Integer.MAX_VALUE;
    
    public int typeInitialization = 1; // 1- random initialization ; 2 - ....... - need TO DO
    
    public double proCrossover =  0.8;
  
    public double proMutation =0.05;
    
    public int twin = 1; // 0 - the twin individuals are not accepted; 1 -  accepted
    
    public int shuffle = 0; // 0- needn't to shuffle list of chromosomes; 1- shuffle list of chromosomes
    
    public int typeselect =1; // 1- etilisim 2-normal way
    
    public double eliteper = 0.2;
     
    public int diversity=0; //0- not use; 1- crowding method 
    
    public double threshold = 5000;
    
    public String GoldStandard = "/media/huong/DATA/These/Codes/rdfminer/GoldStandard.xlsx";
    
   
}
