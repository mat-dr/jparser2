package jparser;
import java.io.Serializable;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import jparser.Config.BlockDelimiterInstance;
import jparser.Config.BlockDelimiterType;
import jparser.Config.ParseException;
import jparser.numbered.NumberedChar;
import jparser.numbered.NumberedString;


enum ParserState {
	Apostrophe,Quote,TopLevel
}


/*
 * children == null xor String == null
 */
/** Abstract syntax tree node. */
public class ASTNode implements Cloneable, Serializable {
	public List<ASTNode> children = new ArrayList<ASTNode>(5);
//	public ASTNode parent;
	public NumberedString nstring;
	public NodeType type;
	// not null if type==Custom
	public Enum customType = null;
	public BlockDelimiterType delimiter = null;
    public Config config;
	

	public ASTNode(NumberedString ns, NodeType t,Config config) {
		nstring = ns;
	//	parent = p;
		type = t;
		this.config = config;
		
		
		
//		if (parent == null){
//			getQuotes(ns,p,t,config);
//		}
	}
	
//	private static void getParensHelper(ASTNode inNode, ASTNode outNode,  Config config,Stack<BlockDelimiterInstance> stack) {
//           
//	}
	
	private static void parentheseError(int i,NumberedString ns,String errmsg){
	    System.out.println("ns:"+ns);
	    System.out.println("full cseq:"+ns.cseq);
        NumberedChar nc = ns.getNumChar(i);
        StringBuilder sb = new StringBuilder("block delimiter nesting error:\n"+errmsg+"\n at character: "+i
        +"\nline: "+nc.linenumber+", column: "+nc.column+"\n");
        CharSequence line = ns.getLine(nc.linenumber);
        sb.append(line);
        sb.append("\n");
        for (int j=1; j<line.length()+1; j++){
            if (j != nc.column) 
                sb.append(" ");
            else 
                sb.append("^");
        }
        throw new ParseException(sb.toString(),nc,ns);	   
	}
	/** Get parentheses, brackets and other block-type nodes. */ 
    private static ASTNode getParens(ASTNode root, ASTNode outNode,  Config config) {
        Stack<BlockDelimiterInstance> stack = new Stack<BlockDelimiterInstance>();
        ASTNode parent = new ASTNode(root.nstring,  NodeType.Root, config);
        ASTNode result = parent;        
//        ASTNode terminals = new ASTNode(root.nstring, parent, NodeType.Atom, config);
        ASTNode terminals = null;
        int i=0;
        // we know that the tree is only one level deep, as the result of processing 
        // quotes and apostrophes
        for (ASTNode inNode:root.children){            
            if (inNode.type == NodeType.Unparsed ){
                NumberedString ns = inNode.nstring;                            
                iloop:for (i=ns.start; i<ns.end; ){
//                    if (it == 5){ // for debugging only
//                        System.out.println("breakpoint");
//                    }
                    if (terminals != null){
                        terminals.nstring.end = i;                     
                    }
                    for (BlockDelimiterType delimiter:config.parensAndBraces.elements){
                        if (ns.startsWith(delimiter.startLiteral, i)){
                            terminals = null;                            
                            
                            BlockDelimiterInstance bdi = new BlockDelimiterInstance();
                            bdi.start = i+delimiter.startLiteral.length();
                            bdi.delimiterType = delimiter;
                            bdi.node = parent;
                            stack.push(bdi);
                            NumberedString ns2 = new NumberedString(ns, i, ns.end);
                            ASTNode newBlock = new ASTNode(ns2, NodeType.Block, config);
                            newBlock.delimiter = delimiter;
                            parent.children.add(newBlock);
                            parent = newBlock;
                            i = i + delimiter.startLiteral.length();
                            continue iloop;
                        } else if (ns.startsWith(delimiter.endLiteral, i)){
                            BlockDelimiterInstance bdi = null;                         
                            try {
                                bdi = stack.pop();
                            } catch (EmptyStackException e) {
                                parentheseError(i, ns,"more block end delimiter than opening one");
                            }
                            if (bdi.delimiterType != delimiter){
                                parentheseError(i, ns,"wrong end delimiter, overlapping blocks");
                            } else {
                                i = i + delimiter.endLiteral.length();
                                terminals = null;
                                
                                
                                // not really neccessary
                                bdi.end = i-1;                                 
                                parent.nstring.end = i;
                                //***
                                NumberedString ns2 = new NumberedString(ns, bdi.start, bdi.end);
                                ASTNode childNode = new ASTNode(ns2,  NodeType.Root, config);
                                parent = bdi.node;
//                                parent.children.add(childNode);
                                continue iloop;
                                
                            }
                        }
                    } // for BlockDelimiter
                    if (terminals == null){  // not delimiter char add to terminals
                        NumberedString ns2 = new NumberedString(ns, i, ns.end);
                        terminals = new ASTNode(ns2,  NodeType.Atom, config);
                        parent.children.add(terminals);
                    }
                    i++;
                } // for it         
            } // type == unparsed   
            else if (inNode.type == NodeType.Apostrophe || inNode.type == NodeType.Quote){
                if (terminals != null){
                    terminals.nstring.end = i;
                    terminals = null;
                }                
                parent.children.add(inNode);
            }
        } // for root.children
        if (stack.size() != 0)
            parentheseError(i-1, root.nstring,"unclosed blocks");
        return result;
    }   

	private void getQuotes(NumberedString ns,Config config) {	
	    
		boolean prevBackslash = false; 
		ParserState state = ParserState.TopLevel;
		ASTNode child;
		int start=0,end=0;
		int i;
		for (i=0; i<ns.end; i++){

//			for (CharPair qp:config.quotes.charPairs){
//				if (ns.startsWith(qp.start, it)) {
//					start = it;
//					nodeType = qp.nodeType;
//					childString = new NumberedString(ns.charlist, start,end);
//					child = new ASTNode(ns, p, nodeType, config)
//				}
//			}
			if (state != ParserState.TopLevel){
				
				if (state == ParserState.Quote && ns.startsWith("\"", i)){
					if (!prevBackslash){
						end=i;
						child = new ASTNode(new NumberedString(nstring, start+1, end),NodeType.Quote,config);						
						children.add(child);
						
						state = ParserState.TopLevel;
						start = i+1;
						continue;
					}					
				}
				if (state == ParserState.Apostrophe && ns.startsWith("'", i)){
					if (!prevBackslash){
						end=i;
						child = new ASTNode(new NumberedString(nstring, start+1, end),NodeType.Apostrophe,config);						
						children.add(child);
						
						state = ParserState.TopLevel;
						start = i+1;
						continue;
					}
				}
				
				if (ns.startsWith("\\", i)) {
					prevBackslash = true;				
				} else 
					prevBackslash = false;
			
			}
			if (state == ParserState.TopLevel){
				if (ns.startsWith("\"", i) && !prevBackslash) {
					end = i;
					child = new ASTNode(new NumberedString(nstring, start, end),NodeType.Unparsed,config); 
					children.add(child);
					
					start = i;
					state = ParserState.Quote;
				}	
				if (ns.startsWith("'", i) && !prevBackslash){
					end = i;
					child = new ASTNode(new NumberedString(nstring, start, end),NodeType.Unparsed,config);
					children.add(child);
					
					start = i;
					state = ParserState.Apostrophe;
				}
			}			
		} // end parse cycle "for it"
		if (state == ParserState.TopLevel){
			end = i;
			child = new ASTNode(new NumberedString(nstring, start, end),NodeType.Unparsed,config);
			children.add(child);
		} else if (state == ParserState.Quote) {
			System.out.println("unclosed quote");
		} else if (state == ParserState.Apostrophe){
			System.out.println("unclosed apostrophe");
		}
		
	}
/** Parse wildcards for patternmatching. Wildcards can't be escaped (now). */	
	private static void getWildcards(ASTNode root, Config config) {
	    if (config.wildcard == null) return;
	    List<ASTNode> newNodes = new ArrayList<ASTNode>();
	    
	    NumberedString ns = null;
	    
        for (int childIndex = 0; childIndex < root.children.size(); childIndex++) {
            ASTNode child = root.children.get(childIndex);
	        if (child.type == NodeType.Atom) {
	            ns = child.nstring;
	            int start = ns.start, end = -1000;
	            int i;
	            for (i=ns.start; i<ns.end; i++){
	                if (ns.startsWith(config.wildcard, i)) {
	                    // add atom coming before wildcard
	                    end = i;
	                    if (start != end) 
	                        newNodes.add(new ASTNode(new NumberedString(ns, start, end), NodeType.Atom, config));
	                    
	                    // add wildcard
	                    start = i;
	                    end = i + config.wildcard.length();
	                    newNodes.add(new ASTNode(new NumberedString(ns, start, end), NodeType.Wildcard, config));

	                    // after wildcard
	                    start = end;
	                    i = end;
	                }
	            }
	            end = ns.end;
	            if (start != ns.start && start != end) { 
                    newNodes.add(new ASTNode(new NumberedString(ns, start, end), NodeType.Atom, config));
	            }
	            if (newNodes.size()>0) {
                    root.children.remove(childIndex);
                    root.children.addAll(childIndex, newNodes);
                    newNodes.clear();
	            }         	            
	        } else if (child.type == NodeType.Block) {
	            getWildcards(child, config);
	        }
	    }        
	    
	}
	
	public static void indent(StringBuilder sb,int indent){
	    for (int i=0; i<indent; i++){
            sb.append(' ');
        }   	    
	}
	
	public StringBuilder prettyString(StringBuilder sb,int indent) {
	    final int indinc = 3;
        indent(sb, indent);
        if (type == NodeType.Unparsed) {
            sb.append("#\"");
            sb.append(nstring.toString());
            sb.append("\"");
        } else if (type == NodeType.Atom) {
            sb.append("@\"");
            sb.append(nstring.toString());
            sb.append("\"");
        } else if (type == NodeType.Quote) {
            sb.append('"');
            sb.append(nstring.toString());
            sb.append('"');
        } else if (type == NodeType.Apostrophe) {
            sb.append('\'');
            sb.append(nstring.toString());
            sb.append('\'');
        } else if (type == NodeType.Root) {
            for (ASTNode node : children) {
                node.prettyString(sb, indent + indinc);
            }
            sb.append("end-Root");
        } else if (type == NodeType.Block) {
            sb.append(delimiter.startLiteral + "\n");
            for (ASTNode node : children) {
                node.prettyString(sb, indent + indinc);
            }
            // indent(sb,indent);
            // sb.append('\n');
            indent(sb, indent);
            sb.append(delimiter.endLiteral);
            // sb.append('\n');
        } else if (type == NodeType.Wildcard) {
            // sb.append('\n');
            // indent(sb,indent);
            sb.append(config.wildcard);
            // sb.append('\n');
        } else {
            throw new RuntimeException(
                    "pretty-print not defined for this nodetype:" + type);
        }
		sb.append('\n');
		return sb;
	}
	
	@Override
	public String toString() {
		return type.toString()+"\n"+prettyString(new StringBuilder(),0).toString();		
	}


	public static ASTNode parse(NumberedString numberedString, Config config) {
		ASTNode root = new ASTNode(numberedString,  NodeType.Root, config);
		root.getQuotes(numberedString,config);
		ASTNode afterParen = new ASTNode(numberedString,  NodeType.Root, config);
		afterParen = getParens(root,afterParen, config);
		return afterParen;
	}
	
    public static ASTNode parsePattern(NumberedString numberedString, Config config) {
        ASTNode root = new ASTNode(numberedString,  NodeType.Root, config);
        root.getQuotes(numberedString,config);
        ASTNode afterParen = new ASTNode(numberedString,  NodeType.Root, config);
        afterParen = getParens(root,afterParen, config);
        getWildcards(afterParen, config);
        return afterParen;
    }	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	    
	}
	
}
