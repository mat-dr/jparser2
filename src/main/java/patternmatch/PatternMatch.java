package patternmatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;



import example.JParse;
import jparser.ASTNode;

public class PatternMatch {
    /** <u>result format: </u><br> 
     * key: matching node in original tree <br> 
     * value: pattern unified with matching node */ 
    public static HashMap<ASTNode, ASTNode> match(ASTNode pattern, ASTNode tree) {       
        HashMap<ASTNode, ASTNode> result = new HashMap<ASTNode, ASTNode>();
        matchHelper(pattern, tree, result);
        return result;
    }
    private static void matchHelper(ASTNode pattern, ASTNode tree,HashMap<ASTNode, ASTNode>  result) {
        ASTNode patt2 = null;
        try {
            patt2 = (ASTNode) pattern.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (Unifier.unifyLeft(patt2, tree)) {
            result.put(tree, patt2);
        }
        for (ASTNode child:tree.children) {
            matchHelper(pattern, child, result);
        }               
    }
    
    public static void main(String[] args) {
        ASTNode pattern = JParse.parsePattern("[[*wild*]*wild*]").children.get(0);
        ASTNode root = JParse.parse("begin x end;[[b+c]a]");
        
        HashMap<ASTNode, ASTNode> result = match(pattern, root);
        Set<ASTNode> ks = result.keySet();
        int i=0;
        for(ASTNode node: ks) {
            System.out.println("*** "+i+" ***");
            System.out.println(node);
            i++;
        }        
        
    }

}
