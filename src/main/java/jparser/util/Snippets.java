package jparser.util;


import java.io.*;
/** Deep cloning utility.
 * Borrowed from the DataPal project. (Wasn't feeling like extracting one file to a utility project/repo.) 
 * */
public class Snippets {
	@SuppressWarnings("unchecked")
	public static <T> T deepCopy(T o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static int signumInt(Double dev1, Double dev2) {
		return (int) Math.round(Math.signum(dev2-dev1));
	}
	
	/** Cuts off the trailing zeroes. */
	public static String formatFloat(String fs,Object o){
		String result = String.format(fs, o);
		
		while (result.endsWith("0") ||						
				result.endsWith(".")){
					if (result.indexOf(".") > 0)
						result = result.substring(0, result.length()-1);
					else break;
				
		}
		return result;
	}
}
