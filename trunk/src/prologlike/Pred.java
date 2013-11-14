package prologlike;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public abstract class Pred {
    public static class SearchExhaustedException extends RuntimeException {        
    }
    
//    enum Boolop {
//        AND,OR
//    };
//    Boolop op = Boolop.AND;
//    Pred nextPred;
    List<Var> vars = new ArrayList<Var>();
    
    abstract protected boolean test();
        
    public void addVar(Var v){
        vars.add(v);
        v.addPred(this, vars.size()-1);
    }
    int vari=0;
    int varj=0;
    private void resetLower(int vari){
        for (int j=vari; j>-1; j--){
            vars.get(j).reset();                    
        }        
    }
    public  boolean stepVars(){
        if (last) return false;
        vari = 0;
//        System.out.println("vari "+vari);        
        while(vari<vars.size()){
            Var v = vars.get(vari);
            if (v.hasNext()){
                v.next();
                return true;
            } else {
                if (vari == vars.size()-1){
                    last = true;
                    return false;
                } else {
                    resetLower(vari);
                    vari++; 
                }
            }         
        }                
        return false;        
    }
    boolean last=false;
    boolean first=true;
    
    public boolean backtrack(){
        while(!test()){
            if (!stepVars()) return false;
        }
        return true;
    }
    
//    public boolean next()
    
    public boolean findNextSolution(){        
//        vars.get(vars.size()-1).next();
//        if (last) return false;
        if (first){
            first = false;
        } else {
            stepVars();
        }
        return backtrack();        
    }
    public void findNextSolutionEx(){
        if (first){
            first = false;
        } else {
            stepVars();
        }
        if (!backtrack()) throw new SearchExhaustedException();
    }
    
    public void resetAll(){
        for (Var v:vars){
            v.reset();
        }
    }
    

}
