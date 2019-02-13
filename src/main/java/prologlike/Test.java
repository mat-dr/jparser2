package prologlike;

public class Test {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Integer[] ra = {1,2,3};
        Integer[] rb = {1,2,3};
        final Var<Integer> a = new Var<Integer>(ra);
        final Var b = new Var<Integer>(rb);
        Pred pred = new Pred() {
            
            @Override
            protected boolean test() {
                // TODO Auto-generated method stub
                int ia = a.getCurrval();
                int ib = (Integer) b.getCurrval();
                if (ia+ib<4) 
                    return true;
                else 
                    return false;
            }
        };
        pred.addVar(a);
        pred.addVar(b);
        
        boolean sol;
        
        for(int i=0; i<6; i++){
            sol = pred.findNextSolution();
            if (sol){
                System.out.println("a: "+a.getCurrval()+", b: "+b.getCurrval());
                System.out.println("sol "+sol);
            } else {
                System.out.println("search exhausted");
            }            
        }


        
        
    }
}
