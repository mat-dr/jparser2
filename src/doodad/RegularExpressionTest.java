package doodad;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionTest {

	public RegularExpressionTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Pattern p = Pattern.compile("(a+)");
		 Matcher m = p.matcher("aaaaaba");
		 //System.out.println(m.matches())
		 m.find();		 
		 m.find();
		 System.out.println("grCount "+m.groupCount());	 
		 MatchResult mr = m.toMatchResult();
		 
		 System.out.println(m.end());
		 System.out.println("'"+mr.group()+"'");
		 System.out.println("start "+mr.start(1));
		 
	}

}
