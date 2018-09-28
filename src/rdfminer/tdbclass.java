package rdfminer;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

/**
 * @author NGUYEN Thu Huong
 *  2017
 */
public class tdbclass {

	public static void main(String[] args) 
	{
		 Dataset dataset = TDBFactory.createDataset("TDBTest10");
		 Model tdb = dataset.getDefaultModel();
		 FileManager.get().readModel(tdb, "/media/huong/DATA/These/Codes/rdfminer/src/rdfminer/instance_types_en.nt");
	     tdb.close();
		 dataset.close();
		
	}

}




