package testing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.topbraid.shacl.lite.SHACLLiteConstraintValidator;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class test {

	public static void main(String[] args) {
		OntModel m = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM_TRANS_INF);
		
		String sh = "http://www.w3.org/ns/shacl#";
		String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
		
		m.setNsPrefix("sh", sh);
		m.setNsPrefix("rdfs", rdfs);
		
		try {
			m.read(new FileInputStream("data/test2.ttl"),null,"TTL");
			
			String queryString =  "PREFIX sh: <http://www.w3.org/ns/shacl#> "+
            		 "PREFIX ex: <http://www.example.com/ex#> "+
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
             		 " SELECT ?focusValue "+
									"WHERE {"+

									"ex:v1 ex:path ?path . {"
									+ "?path ex:path1 ?f ." +
									"?f a ?focusValue ." +
									" FILTER EXISTS { ?path a ex:SeqPath . }}" +
									"UNION" +
									//"{ex:v1 ex:path ?path ." +
									 "{?path ex:path2 ?f . " +
									"?f a ?focusValue ." +
									" FILTER EXISTS { ?path a ex:AltPath . }}" +
									" } " ;
             
             System.out.println(queryString);
             Query query = QueryFactory.create(queryString) ;
      
            	 QueryExecution qexec = QueryExecutionFactory.create(query, m);
               ResultSet results = qexec.execSelect() ;
               for ( ; results.hasNext() ; )
               {
                 QuerySolution soln = results.nextSolution() ;
          
                 Resource l = soln.getResource("focusValue") ;   // Get a result variable - must be a literal
                 System.out.println( "focusValue has:\n\tvalue1: "+l.getLocalName());
               }
             
			
			
//			SHACLLiteConstraintValidator validator = new SHACLLiteConstraintValidator(m);
//			validator.validateGraph();
//			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
