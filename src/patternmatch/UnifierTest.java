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
        
        pattern.children.add(0,new ASTNode(null, NodeType.Wildcard, new Config()));
        Unifier.unifyLeft(pattern, root);
        ASTNode wc = pattern.children.get(0);
        assert(wc.toString().equals(" x "));
        assert(wc.type==NodeType.Atom);
	}

}
