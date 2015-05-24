package jparser.util;

import jparser.ASTNode;

public abstract class TreeIterator {
    abstract protected void processNode(ASTNode parent, ASTNode child, int childIndex);
    public void iterate(ASTNode node) {
        for(int childIndex = 0; childIndex < node.children.size(); childIndex++) {
            ASTNode child = node.children.get(childIndex);
            processNode(node,child,childIndex);
            iterate(child);
        }
    }
}
