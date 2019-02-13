package listmatch;


public abstract class IMatchable {

    public boolean isMatch(IMatchable m) {
        return equals(m);
    }


}
