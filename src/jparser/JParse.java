package jparser;

import jparser.numbered.NumberedString;
import jparser.util.XMLPrinter;



public class JParse {
	
	public static ASTNode parse(String s) {
		
//		return new ASTNode(new NumberedString(s), null, NodeType.Complex,new Config());
		return ASTNode.parse(new NumberedString(s),new Config());
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
	    
		System.out.print(root.toString());
		System.out.println("||");
		
		XMLPrinter pr = new XMLPrinter();
		StringBuilder sb = new StringBuilder();
		pr.print(root, sb);
		System.out.println(sb);
	}

	

}
