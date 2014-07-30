package prologlike;

import sun.org.mozilla.javascript.internal.ast.AstNode;
import jparser.ASTNode;
import jparser.NodeType;
// TODO implement deep cloning and unification
public class Unifier {
    public static void unifyLefty(ASTNode node1,ASTNode node2) {
        if (node1.type==NodeType.Wildcard) {
            node1.type = node2.type;
        }
    }
    public static ASTNode deppCopy(ASTNode node) {
        ASTNode result = null;
        return result;
    }
}
