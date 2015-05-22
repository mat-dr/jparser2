package example;

import jparser.Config;
import jparser.Config.BlockDelimiterType;
import jparser.Config.PriorityClass;

public class ConfigDom1 extends Config {

	public ConfigDom1() {
		/* The original plan was to have a priority class for quote-types too, but it was
		 * scrapped, because handling escaping would be more complicated in cases where the quote 
		 * literals are longer than one character or can contain backslash etc.
		 * 
		 *  So there's only one priority class at the moment called "priorityClass2".
		 *  Treat priority classes and priority tables as boiler plate code at the moment.
		 */
		
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
