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
             		 " SELECT ?focusValue ?t "+
									"WHERE {"+
								"ex:a2 ex:propA ?focusValue ."+
									" {BIND(bound(?focusValue) as ?t)} UNION {BIND(bound(?focusValue) as ?t)}"+
									" } " ;
             
             System.out.println(queryString);
             Query query = QueryFactory.create(queryString) ;
      
            	 QueryExecution qexec = QueryExecutionFactory.create(query, m);
               ResultSet results = qexec.execSelect() ;
               for(String s : results.getResultVars())
            	   System.out.println(s);
               for ( ; results.hasNext() ; )
               {
                 QuerySolution soln = results.nextSolution() ;
     
                 Literal l = soln.getLiteral("t") ;   // Get a result variable - must be a literal
                 System.out.println( "focusValue has:\n\tvalue1: "+l.toString());
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
