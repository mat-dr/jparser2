package listmatch;

import java.util.LinkedList;
import java.util.List;


public abstract class ListPattern {

    private List<IMatchable> pattern;

    public ListPattern(List<IMatchable> pattern) {
        this.pattern = pattern;
    }

    protected int getFirstMatchIndex(List<IMatchable> inList) {
        // FIXME slow in case of linkedlists
        int matchIndex = -1;
        outer: for (int i = 0; i < inList.size(); i++) {
            if (i + pattern.size() > inList.size()) {
                break outer;
            }
            boolean match = true;
            inner: for (int j = 0; j < pattern.size(); j++) {
                if (!inList.get(i + j).isMatch(pattern.get(j))) {
                    match = false;
                    break inner;
                }
            }
            if (match) {
                matchIndex = i;
                break outer;
            }
        }

        return matchIndex;

    }
    
    abstract IMatchable replace(List<IMatchable> match);

    abstract INonTerminal ntFactory(List<IMatchable> children);

    protected List<IMatchable> substitute(List<IMatchable> inList, int matchIndex) {
        List<IMatchable> result = new LinkedList<IMatchable>(inList.subList(0, matchIndex));
        List<IMatchable> middleOrig = inList.subList(matchIndex, matchIndex + pattern.size());
        INonTerminal nt = ntFactory(middleOrig);
        result.add(nt);
        result.addAll(inList.subList(matchIndex + pattern.size(), inList.size()));

        return result;
        
    }
    
    

}
