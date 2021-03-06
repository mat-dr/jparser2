package example;

import jparser.ASTNode;
import jparser.Config;
import jparser.numbered.NumberedString;
import jparser.util.RemoveWhiteSpace;
import jparser.util.XMLPrinter;



public class JParse {
	
    /** Helper method for parsing. For custom block-delimiter definitions see Config. */ 
	public static ASTNode parse(String s) {
		
//		return new ASTNode(new NumberedString(s), null, NodeType.Complex,new Config());
		return ASTNode.parse(new NumberedString(s),new ConfigDom1());
	}
	/** Helper method to parse patterns. The wildcard identifier is defined in Config. */	
   public static ASTNode parsePattern(String s) {
       return ASTNode.parsePattern(new NumberedString(s),new ConfigDom1());
   }
	
	public static void main(String[] args) {
//		ASTNode root = parse("('xy',\"b\",c,(asas,asd,\"w\\\"we\"))");
//		ASTNode root = parse("[1,2] ('xy',\"b\",c,(asas,asd,\"w\\\"we\"))");
//		ASTNode root = parse("begin c end; ('xy',\"b\",(asas,asd,\"w\\\"we\"))");
	    
		// faulty nesting
//	    ASTNode root = parse("('xy',\n\n" +
//	    		"\"b\",c,(asas,asd],\"w\\\"we\")(");
	    
//	    ASTNode root = parse("('xy',\n" +
//        "\"b\",c,(asas,asd,\"w\\\"we\")))\nO");
		
//		ASTNode root = parse("beginxend;[a]");
		
		ASTNode root = parse("begin x end;[[b+c]a]");
	    
//	    ASTNode root = parse("begin *wild* end;[[b+c]a]");
//	    ASTNode root = parsePattern("[[*wild*]*wild*]");
//	    ASTNode root = parsePattern("begin *wild* end;[[b+c]a]");

		System.out.println(root.toString());
		RemoveWhiteSpace removeWhiteSpace = new RemoveWhiteSpace();
		removeWhiteSpace.iterate(root);
		System.out.println(root.toString());
		
		
//		XMLPrinter pr = new XMLPrinter();
//		StringBuilder sb = new StringBuilder();
//		pr.print(root, sb);
//		System.out.println(sb);
	}

	

}
