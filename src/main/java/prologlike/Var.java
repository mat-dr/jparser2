package prologlike;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Var<T> {
    long index=0;
    Iterator<T> it;
    Collection<T> coll;
//    private LinkedList<Pred> preds;
    List<IndexInPred> preds = new ArrayList<IndexInPred>();
    T currval;
    
    public static class IndexInPred {
        long index;
        Pred pred;        
    }

    public void addPred(Pred pred, long i){
        IndexInPred in = new IndexInPred();
        in.index = i;
        in.pred = pred;
        preds.add(in);
    }
    public Var(Collection<T> collection) {    
        coll = collection;
        it = coll.iterator();
        next();
    }
    public Var(T[] array){
        coll = Arrays.asList(array);
        it = coll.iterator();
        next();
    }
    public boolean next(){
        if(it.hasNext()){
            index++;
            currval = (T) it.next();            
            return true;
        }
        return false;
//        throw new Pred.SearchExhaustedException();
    }
    public boolean hasNext(){
        return it.hasNext();
    }
    public T getCurrval(){
        return currval;
    }
    public long getCurrIndex(){
        return index;
    }
    public void reset(){        
        it = coll.iterator();
        currval = (T) it.next();
        index=0;
    }
}
