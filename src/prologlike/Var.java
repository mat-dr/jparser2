package prologlike;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Var<T> {
    Iterator<T> i;
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
        i = coll.iterator();
//        next();
    }
    public Var(T[] array){
        coll = Arrays.asList(array);
        i = coll.iterator();
//        next();
    }
    public boolean next(){
        if(i.hasNext()){
            currval = (T) i.next();            
        }
        return false;
    }
    public boolean hasNext(){
        return i.hasNext();
    }
    public T getCurrval(){
        return currval;
    }
    public void reset(){        
        i = coll.iterator();
        currval = (T) i.next();
    }
}
