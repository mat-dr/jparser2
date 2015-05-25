package jparser.numbered;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberedString {
	/** Normally this is shared by all instances that came from the same AST. */
	protected List<NumberedChar> charlist;
	public int start;
	public int end=-1;
	/** Normally this is shared by all instances that came from the same AST. */
	public CharSequence cseq;
	/** Normally this is shared by all instances that came from the same AST. */
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
	
	public NumberedChar getNumChar(int i){
		return charlist.get(i);
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
	
	/** Same as startsWith but also removes the matching portion. 
	 * Doesn't modify current instance but returns what's left. */ 
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
	
	protected boolean isWhiteSpace(char c){
		return Character.isWhitespace(c);
	}
	
	/** Remvoes whitespaces from beginning and end. Modifies current instance. */
	public void trim(){
		int start2 = start;
		int end2 = end;
		for (int i=start; i<end; i++){
			if (!isWhiteSpace(cseq.charAt(i))){
				start2=i;
				break;				
			}
		}
		for (int i=end-1; i>start; i--){
			if (!isWhiteSpace(cseq.charAt(i))){
				end2=i+1;
				break;				
			}
		}
		start = start2;
		end = end2;
	}
	
	/** Splits at the given character index. Uses absolute indexing. */
	NumberedString[] splitAt(int splitAt){
		NumberedString ns1 = new NumberedString(this, start, splitAt);
		NumberedString ns2 = new NumberedString(this, splitAt, end);
		NumberedString[] result = {ns1,ns2};
		return result;
	}
	
	/** Splits by the given the regex pattern. Common usage is splitting at whitespace. */ 
	public NumberedString[] split(Pattern pattern){
		int offset = start;
		int cursor = 0;
		int end2 = start;
		List<NumberedString> result = new LinkedList<NumberedString>();
		Matcher matcher = pattern.matcher(toString());
		
		while (matcher.find()) {
			if (matcher.start() > cursor){
				result.add(new NumberedString(this, offset+cursor, offset+matcher.start()));
				cursor = matcher.end();
			} else if (matcher.start() == cursor){
				// string starts with delimiter pattern				
				cursor = matcher.end();
			}			
		}
		if (offset + cursor < end){
			result.add(new NumberedString(this, offset+cursor, end));
		}
		
		return result.toArray(new NumberedString[0]);
	}
	
	public int getLength(){
		return end-start;
	}
	
	public boolean isEmpty(){
		return (getLength()==0);
	}
	
	
	public static void main(String[] args) {
		NumberedString ns = new NumberedString("  abc 1   23  ");
		//ns.start = 2;
		//ns.trim();
		//ns = ns.biteOffFromStart("ab", 0);
		//ns = ns.splitAt(2)[1];
		Pattern p = Pattern.compile("\\s");
		Object[] os = ns.split(p);
		int i=0;
		for(Object o:os){
			System.out.println(i+": '"+o+"'");
			i++;
		}
		System.out.println("'"+ns+"'");
		
	}
}
