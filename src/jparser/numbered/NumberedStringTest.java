package jparser.numbered;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class NumberedStringTest {

	@Test
	public void testTrim() {
		NumberedString ns = new NumberedString("abc123");
		ns.trim();
		assert(ns.toString().equals("abc123"));
		
		NumberedString ns2 = new NumberedString(" abc123  ");
		ns2.trim();
		assert(ns2.toString().equals("abc123"));
	}
	
	@Test
	public void testBiteoff(){
		NumberedString ns = new NumberedString("abc123 ");
		ns = ns.biteOffFromStart("ab", 0);
		assert(ns.toString().equals("c123"));		
		
		NumberedString ns2 = new NumberedString("abc123 ");
		ns2 = ns2.biteOffFromStart("ab", 1);
		assert(ns2 == null);		
	}
	
	@Test
	public void testSplit1(){
		NumberedString ns = new NumberedString("abc123 ");
		Pattern p = Pattern.compile("1");
		Object[] os = ns.split(p);		
		assert(os[0].equals("abc"));
		assert(os[1].equals("23"));
	}
	@Test
	public void testSplit2(){
		NumberedString ns = new NumberedString("abc123 ");
		Pattern p = Pattern.compile("c");
		Object[] os = ns.split(p);		
		assert(os[0].equals("ab"));
		assert(os[1].equals("123"));
	}
	
	@Test
	public void testSplit3(){
		NumberedString ns = new NumberedString("  abc 1   23  ");
		Pattern p = Pattern.compile("\\s+");
		Object[] os = ns.split(p);
		assert(os[0].equals("abc"));
		assert(os[1].equals("1"));
		assert(os[1].equals("23"));
	}



}
