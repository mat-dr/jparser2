package jparser.numbered;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NumberedString {
	public List<NumberedChar> charlist;
	public int start;
	public int end=-1;
	public CharSequence cseq;
	private ArrayList<Integer> lines = new ArrayList<Integer>();
	
	/**
	 * Creating rootnode.
	 * @param cs
	 */	
	public NumberedString(CharSequence cs) {
		cseq = cs;
		start=0;
		end = cs.length();
		charlist = new ArrayList<NumberedChar>(end);
		int line = 1;
		int col = 1;
		for (int i=0; i<end; i++){
			char c = cs.charAt(i);					
			charlist.add(new NumberedChar(c,i, line,col));
			if (c == '\n') {			    
			    line++;			    
			    lines.add(i);
			    col = 1;
			} else 
			    col++;
		}
	}
	/**
	 * Creating childnode.
	 * @param nchars
	 * @param start
	 * @param end
	 */
	public NumberedString(NumberedString ns,int start, int end){
		charlist = ns.charlist;
		cseq = ns.cseq;
		this.start = start;
		this.end = end;
		this.lines = ns.lines;
	}
	
	public CharSequence getLine(int i){
	    CharSequence result;
	    System.out.println("it "+i);
	    System.out.println("lines "+lines);
	    if (i == 1) {
	        if (lines.size() == 0)
	            result = cseq;
	        else 
	            result = cseq.subSequence(0, lines.get(0));
	        return result;
	    }
	    else if (i == lines.size()+1)
	        result = cseq.subSequence(lines.get(i-2), cseq.length());
	    else if (i < lines.size()+1)
	        result = cseq.subSequence(lines.get(i-2), lines.get(i-1));
	    else throw new IndexOutOfBoundsException("too big line index");
	    return result;
	}
	
	public char getChar(int i){
		return charlist.get(i).character;
	}
	
	public boolean startsWith(CharSequence s,int startCursor){
		int i;
		try {
			for (i=0; i<s.length(); i++){
				if (s.charAt(i) != getChar(startCursor+i)){
					return false;				
				}									
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return false;
		}
	}
	
	public NumberedString biteOffFromStart(CharSequence s, int startCursor){
	    if (startsWith(s, startCursor)) {
	        NumberedString result = new NumberedString(this, startCursor+s.length(), end);
	        return result;
	    }
	    return null;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	
	@Override
	public String toString() {
		return (cseq.subSequence(start, end).toString());
	}
	
	public static void main(String[] args) {
		NumberedString ns = new NumberedString("abc123");
		ns.start = 2;
		System.out.println(ns);
	}
}
