package patternmatch;

import static org.junit.Assert.*;
import jparser.ASTNode;
import jparser.Config;
import jparser.NodeType;

import org.junit.Test;

import example.JParse;

public class UnifierTest {

	@Test
	public void testUnifyLeft() {
        ASTNode pattern = JParse.parse("[[b+c]a]");
        ASTNode root = JParse.parse("begin x end;[[b+c]a]");
        ASTNode wc1 = new ASTNode(null, NodeType.Wildcard, new Config());
        pattern.children.add(0,wc1);
        Unifier.unifyLeft(pattern, root);
        ASTNode wc2 = pattern.children.get(0);
        assert(wc1.toString().equals(" x "));
        assert(wc1.type==NodeType.Atom);
        
        assert(wc1==wc2);
	}

}
