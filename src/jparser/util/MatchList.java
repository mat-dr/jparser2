package jparser.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// TODO testing, performance optimization (subList is probably not very efficient for this use)
public class MatchList {

    public static List<Integer> matchLists(List pattern, List full){
        LinkedList<Integer> result = new LinkedList<Integer>();
        int indexFull = 0;
        while(indexFull + pattern.size() <= full.size()){
            List slice = full.subList(indexFull, indexFull+pattern.size());
            if (matchesSliced(pattern, slice)){
                result.add(indexFull);
            }
            indexFull++;
        }
        return result;
    }
    
    /** It can be made later to unify too. */
    private static boolean matchesSliced(List pattern, List full){
        return pattern.equals(full);
    }

    public static void main(String[] args) {
        
        Integer[] full = new Integer[] {1,2,3,1,2};
        Integer[] patt = new Integer[] {1,2};
        
        List<Integer> tmp = Arrays.asList(full);
        
        List<Integer> res = matchLists(Arrays.asList(patt), Arrays.asList(full));
        System.out.println(res);
        
        
    }

}
