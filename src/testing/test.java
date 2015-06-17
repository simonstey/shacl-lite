package testing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.topbraid.shacl.lite.SHACLLiteConstraintValidator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class test {

	public static void main(String[] args) {
		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM_TRANS_INF);
		
		String sh = "http://www.w3.org/ns/shacl#";
		String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
		
		m.setNsPrefix("sh", sh);
		m.setNsPrefix("rdfs", rdfs);
		
		try {
			m.read(new FileInputStream("data/test.ttl"),null,"TTL");
			
			SHACLLiteConstraintValidator validator = new SHACLLiteConstraintValidator(m);
			validator.validateGraph();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
