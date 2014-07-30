package jparser.util;

import org.apache.commons.lang3.StringEscapeUtils;

import jparser.ASTNode;
import jparser.NodeType;
import sun.org.mozilla.javascript.internal.ast.AstNode;

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
        result.append(" b=\""+escape(node.delimiter.startLiteral)+"\"");
        result.append(" e=\""+escape(node.delimiter.endLiteral)+"\"");
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
    
    public void print(ASTNode node, StringBuilder result) {
        if (node.type == NodeType.Complex) {
            printComplex(node, result);
        }
        if (node.type == NodeType.Block) {
            printBlock(node, result);
        }
        if (node.type == NodeType.Atom) {
            printAtom(node, result);
        }
        if (node.type == NodeType.Quote) {
            printQuote(node, result);
        }
        if (node.type == NodeType.Apostrophe) {
            printApostrophe(node, result);
        }        
        
    }

    public static String escape(String s) {
        return StringEscapeUtils.escapeXml11(s);
    }
}
