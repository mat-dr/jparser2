package prologlike;

import sun.org.mozilla.javascript.internal.ast.AstNode;
import jparser.ASTNode;
import jparser.Config;
import jparser.JParse;
import jparser.NodeType;
// TODO implement deep cloning and unification
public class Unifier {
    /** The left one contains wildcard(s), the right one is ground. */
    public static boolean unifyLefty(ASTNode node1,ASTNode node2) {
        if (node1.type==NodeType.Wildcard) {
            node1.type = node2.type;
            node1.children = node2.children;
            node1.delimiter = node2.delimiter;
            node1.nstring = node2.nstring;
        } else {
            if (! (
                    node1.type == node2.type && 
                    // complicated way of 
                    ((node1.delimiter == null && node2.delimiter == null ) || node1.delimiter.equals(node2.delimiter) ) && 
                    (
                         // atom and quote types must have same string content, other types don't   
                        (node2.type != NodeType.Atom && node2.type != NodeType.Quote && node2.type != NodeType.Apostrophe) || 
                        node1.nstring.toString().equals(node2.nstring.toString())
                    )
                  )
                )
                return false;
            for(int i=0; i<node1.children.size(); i++) {
                if (!unifyLefty(node1.children.get(i), node2.children.get(i)))
                    return false;
            }            
        }
        return true;
    }
    
    public static boolean equalsIgnoringNStr(ASTNode node1,ASTNode node2) {
        if (node1.type == NodeType.Wildcard) return false;
        if (! (
                node1.type == node2.type && 
                node1.delimiter == node2.delimiter &&
                node1.nstring.toString().equals(node2.nstring.toString())
                )
            )
                return false;
        for(int i=0; i<node1.children.size(); i++) {
            if (!equalsIgnoringNStr(node1.children.get(i), node2.children.get(i)))
                return false;
        }
        return true;
    }
    
    public static ASTNode deepCopy(ASTNode node) {
        ASTNode result = null;
        return result;
    }
    public static void main(String[] args) {
        ASTNode pattern = JParse.parse("[[b+c]a]");
        ASTNode root = JParse.parse("begin x end;[[b+c]a]");
        
        System.out.println(pattern);
        
        pattern.children.add(0,new ASTNode(null, NodeType.Wildcard, new Config()));
        unifyLefty(pattern, root);
        
        System.out.println("****");
        System.out.println(pattern);
        
    }
}
