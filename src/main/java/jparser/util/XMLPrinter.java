package jparser.util;

import org.apache.commons.lang3.StringEscapeUtils;

import jparser.ASTNode;
import jparser.NodeType;


public class XMLPrinter {
    public static final String atomTag="atom";  
    public static final String quoteTag="quote";
    public static final String apostropheTag="apostrophe";
    
    protected void printComplex(ASTNode node, StringBuilder result) {
        result.append("<");
        result.append("complex");
        result.append(">");
        for(ASTNode child : node.children) {
            print(child, result);
        }
        result.append("</");
        result.append("complex");
        result.append(">");
        
    }
    protected void printBlock(ASTNode node, StringBuilder result) {
        result.append("<");
        result.append("block");
//        result.append(" b=\""+escape(node.delimiter.startLiteral)+"\"");
//        result.append(" e=\""+escape(node.delimiter.endLiteral)+"\"");
        result.append(" name=\""+escape(node.delimiter.name)+"\"");
        result.append(">");
        for(ASTNode child : node.children) {
            print(child, result);
        }
        result.append("</");
        result.append("block");
        result.append(">");       
    }        
    
    protected void printQuote(ASTNode node, StringBuilder result) {
        result.append("<quote>");        
        result.append(escape(node.nstring.cseq.toString()));               
        result.append("</quote>");
        
    }
    protected void printApostrophe(ASTNode node, StringBuilder result) {
        result.append("<apostrophe>");        
        result.append(escape(node.nstring.cseq.toString()));               
        result.append("</apostrophe>");        
        
    }
    protected void printAtom(ASTNode node, StringBuilder result) {
        result.append("<");
        result.append(escape(atomTag));
        result.append(" name=\""+escape(node.nstring.toString())+"\"");        
        result.append("/>");
    }
    private void printWildcard(ASTNode node, StringBuilder result) {
        result.append("<");
        result.append("wildcard");        
        result.append("/>");
        
    }

    
    public void print(ASTNode node, StringBuilder result) {
        if (node.type == NodeType.Root) {
            printComplex(node, result);
        } else if (node.type == NodeType.Block) {
            printBlock(node, result);
        } else if (node.type == NodeType.Atom) {
            printAtom(node, result);
        } else if (node.type == NodeType.Quote) {
            printQuote(node, result);
        } else if (node.type == NodeType.Apostrophe) {
            printApostrophe(node, result);
        } else if (node.type == NodeType.Wildcard) {
            printWildcard(node, result);
        } else {
            throw new RuntimeException("unknown node type in xml printer:"
                    + node.type);
        }
        
    }

    public static String escape(String s) {
        return StringEscapeUtils.escapeXml11(s);
    }
}
