package jparser.numbered;

public class NumberedChar {
	public int charnumber;
	public int linenumber;
	public char character;
	public int column;
	public NumberedChar(char c,int cn, int ln, int col) {
		charnumber = cn;
		character = c;
		linenumber = ln;
		column = col;
	}
	@Override
	public String toString() {
	    // TODO Auto-generated method stub
	    return ""+character;
	}
}
