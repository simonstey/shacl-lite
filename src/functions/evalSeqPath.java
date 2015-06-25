package functions;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.expr.ExprEvalException;
import com.hp.hpl.jena.sparql.expr.NodeValue;
import com.hp.hpl.jena.sparql.function.FunctionBase1;
import com.hp.hpl.jena.sparql.util.FmtUtils;

public class evalSeqPath extends FunctionBase1 {

	public evalSeqPath() { super() ; }
	
	@Override
	public NodeValue exec(NodeValue v) {
		Node n = v.asNode() ;
        if ( ! n.isURI() )
            throw new ExprEvalException("Not a URI: "+FmtUtils.stringForNode(n)) ;
        
 
        String str = n.getNameSpace() ;
        return NodeValue.makeString(str) ;
	}

}
