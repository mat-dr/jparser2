package jparser;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import jparser.numbered.NumberedChar;
import jparser.numbered.NumberedString;

public class Config {
	/**
	 * 
	 * shouldnt have CharPairs with same starting member
	 *
	 */
	public class PriorityClass {
		public List<BlockDelimiterType> elements = new LinkedList<Config.BlockDelimiterType>();
	}
	public class BlockDelimiterType {
		public String startLiteral;
		public String endLiteral;
		public String name;
		public Object nodeType;
	}
	
	public static class BlockDelimiterInstance {
	    BlockDelimiterType delimiterType;
	    int start;
	    int end;
	    ASTNode node;
	}
	public static class ParseException extends RuntimeException {
	    public NumberedString numberedString;
	    public NumberedChar numberedChar;
	    ParseException(String s,NumberedChar nc,NumberedString ns){
	        super(s);
	        numberedChar = nc;
	        numberedString = ns;
	    }
	};
	public List<BlockDelimiterType> matching = new LinkedList<BlockDelimiterType>();
	
	/**
	 * This won't be used now. Maybe for a more complex parser version ...
	 */
	Hashtable<Integer, PriorityClass> priorityTable = new Hashtable<Integer, Config.PriorityClass>();
	PriorityClass parensAndBraces = new PriorityClass();
	
//	/**
//	 * Highest priority for obvious reason.
//	 */
//	PriorityClass quotes;
	
	public Config() {
//		CharPair aposthrophe,quote;
//		aposthrophe = new CharPair();
//		quote = new CharPair();
//		
//		aposthrophe.start = "'";
//		aposthrophe.end = "'";
//		aposthrophe.name = "apostrophe";
//		aposthrophe.nodeType = NodeType.Apostrophe;
//		
//		quote.start = "\"";
//		quote.end = "\"";
//		quote.name = "quotation mark";
//		quote.nodeType = NodeType.Quote;
//		
//		PriorityClass priorityClass1 = new PriorityClass();
//		priorityClass1.charPairs.add(aposthrophe);
//		priorityClass1.charPairs.add(quote);
////		priorityTable.put(1, priorityClass1);
//		quotes = priorityClass1;
		BlockDelimiterType brace,paren,bracket,beginEnd;
		
		
		brace = new BlockDelimiterType();
		paren = new BlockDelimiterType();
		bracket = new BlockDelimiterType();
		beginEnd = new BlockDelimiterType();
		
		paren.startLiteral = "(";
        paren.endLiteral = ")";
        paren.name = "paren";
		
		brace.startLiteral = "{";
		brace.endLiteral = "}";
		brace.name = "brace";	
				
		bracket.startLiteral = "[";
		bracket.endLiteral = "]";
		bracket.name = "bracket";
		
		beginEnd.startLiteral = "begin";
		beginEnd.endLiteral = "end;";
		beginEnd.name = "begin-end block";
		
		PriorityClass priorityClass2 = new PriorityClass();
		priorityClass2.elements.add(paren);
//		priorityClass2.elements.add(brace);		
		priorityClass2.elements.add(bracket);
		priorityClass2.elements.add(beginEnd);
		
		priorityTable.put(2, priorityClass2);
		parensAndBraces = priorityClass2;
		
	}
	
}
