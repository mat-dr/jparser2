package jparser.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// TODO testing, performance optimization (subList is probably not very efficient for this use)
public class MatchList {

    public static List<Integer> matchLists(List pattern, List full){
        LinkedList<Integer> result = new LinkedList<Integer>();
        int indexFull = 0;
        while(indexFull + pattern.size() < full.size()){
            List slice = full.subList(indexFull, indexFull+pattern.size());
            if (matchesSliced(pattern, slice)){
                result.add(indexFull);
            }
            indexFull++;
        }
        return null;
    }
    
    /** It can be made later to unify too. */
    private static boolean matchesSliced(List pattern, List full){
        return pattern.equals(full);
    }

    public static void main(String[] args) {

    }

}
