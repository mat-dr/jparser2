package listmatch;

import java.util.List;


public abstract class INonTerminal extends IMatchable {

    List<IMatchable> children;

    public INonTerminal(List<IMatchable> children) {
        this.children = children;
    }

    public List<IMatchable> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object obj) {
        // FIXME should cache all the results
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        INonTerminal ont = (INonTerminal) obj;
        children.equals(ont.children);
        return true;

    }

}
