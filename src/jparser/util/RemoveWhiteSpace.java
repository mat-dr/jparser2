package jparser.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import jparser.ASTNode;
import jparser.NodeType;
import jparser.numbered.NumberedString;

public class RemoveWhiteSpace extends TreeIterator{

    static private Pattern pattern = Pattern.compile("\\s");
    
    @Override
    protected void processNode(ASTNode parent, ASTNode child, int childIndex) {
    	// what's Atom for the stage1 parser is unparsed for stage2
    	
        if (child.type == NodeType.Atom) {
        	//split = child.nstring.toString()
        	NumberedString[] splits = child.nstring.split(pattern);
        	List<ASTNode> repNodes = new LinkedList<ASTNode>();
        	for (NumberedString ns:splits){
        		repNodes.add(new ASTNode(ns, NodeType.Atom, child.config));
        	}
        	parent.children.remove(childIndex);
        	parent.children.addAll(childIndex, repNodes);
        }
    }
}
