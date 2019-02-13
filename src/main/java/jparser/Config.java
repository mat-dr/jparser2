package jparser;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import jparser.numbered.NumberedChar;
import jparser.numbered.NumberedString;

public class Config {
    /**Wildcards can't be escaped (now). */
    public String wildcard = "*wild*";
	/**
	 * 
	 * shouldnt have CharPairs with same starting member
	 *
	 */
	public static class PriorityClass {
		public List<BlockDelimiterType> elements = new LinkedList<Config.BlockDelimiterType>();
	}
	public static class BlockDelimiterType {
		public String startLiteral;
		public String endLiteral;
		public String name;
		public Object nodeType;
		@Override
		public String toString() {		 
		    return super.toString();
		}
		/** To avoid Nullpointer exceptions. */
		public static boolean equals(BlockDelimiterType d1, BlockDelimiterType d2) {
		    if (d1 == null && d2 == null) return true;
		    return d1.equals(d2);
		}
		@Override
		public boolean equals(Object ob) {
		    try {
		        BlockDelimiterType o = (BlockDelimiterType) ob;
		        if (name == null && o.name == null) return true;
		        if (name.equals(o.name)) return true;
		    } catch(ClassCastException e) {
		        return false;
		    }
		    return false;
		}
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
	protected Hashtable<Integer, PriorityClass> priorityTable = new Hashtable<Integer, Config.PriorityClass>();
	protected PriorityClass parensAndBraces = new PriorityClass();
	
//	/**
//	 * Highest priority for obvious reason.
//	 */
//	PriorityClass quotes;
	
	public Config() {
	}
	
}
