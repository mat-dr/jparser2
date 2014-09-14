package patternmatch;

import java.util.ArrayList;
import java.util.List;

import jparser.ASTNode;
import jparser.Config;
import jparser.JParse;
import jparser.NodeType;
import jparser.numbered.NumberedString;

public class Replace {
    
    public ASTNode pattern;
    public ASTNode replaceWith;
    public Config config;
    
    // this should be tested, but seems complete
    // should test complexMatch first
    public void replace(ASTNode root) {
        
        int matchIndex = complexMatch(root, pattern);
        if (matchIndex != -1) {
            removeSequence(root, matchIndex, pattern.children.size());
            //root.children.add(matchIndex,replaceWith);
            root.children.addAll(matchIndex,replaceWith.children);
        } else {
            for (int childIndex = 0; childIndex < root.children.size(); childIndex++) {
                ASTNode child = root.children.get(childIndex);
                if (child.type == NodeType.Complex || child.type == NodeType.Block)
                    replace(child);
            }
        }
        
    }
    
    private static void removeSequence(ASTNode node,int index,int length) {
        for (int i=0; i<length; i++) {
            node.children.remove(index);
        }
    }    
    
    // this should be tested, but seems complete
    /** Only deals with matching Complex type nodes. I.e. matching two nodeslists (their children) against each other. */
    private static int complexMatch(ASTNode root, ASTNode pattern) {
       // if (pattern.type != pattern.type.Complex) throw new RuntimeException("pattern type must be: complex");
        if (! (root.type == pattern.type.Complex || root.type == NodeType.Block )) throw new RuntimeException("root type must be: complex or block");
        
        // pattern shouldn't be longer than the node-list to match against 
        if (root.children.size() < pattern.children.size()) return -1;

        int firstOccurence;
        
        // j: pattern-child index
        // i: root child index
        for (int i=0; i<root.children.size()-pattern.children.size()+1; i++) {            
            for (int j=0; j<pattern.children.size(); j++) {
                // todo proper equals
                //if (!root.children.get(i).equals(pattern.children.get(j))) break;
                if (!Unifier.equalsIgnoringNStr(root.children.get(j),pattern.children.get(j))) break;
                // pattern matches
                if (j == pattern.children.size()-1) return i;
            }            
        }                
        return -1;
    }

    public static void main(String[] args) {
        ASTNode root = JParse.parse("begin *wild* end;[[b+c]a]");
        ASTNode pattern = JParse.parse("[b+c]a");
        ASTNode replacewith = JParse.parse("D");
        
        Replace replace = new Replace();
        replace.pattern = pattern;
//        replace.pattern = pattern.children.get(0);
        replace.replaceWith = replacewith;
        
        
        
        replace.replace(root);
        System.out.println(root);
        
        
        
    }

}
