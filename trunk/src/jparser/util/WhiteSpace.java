package jparser.util;

import jparser.ASTNode;
import jparser.NodeType;

public class WhiteSpace extends TreeIterator{
    public static void getRid(ASTNode node) {
        
    }

    @Override
    protected void processNode(ASTNode parent, ASTNode child, int childIndex) {
        if (child.type == NodeType.Atom) {
            for (int i =0; i<child.nstring.end; i++) {
                
            }
        }
    }
}
