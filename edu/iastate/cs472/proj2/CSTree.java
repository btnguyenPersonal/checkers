package edu.iastate.cs472.proj2; 

public class CSTree
{
	CSNode root;
	int size = 0;

    public CSTree(CSNode r) {
        root = r;
    }

    public CSNode getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public void incSize() {
        size++;
    }

    public void setRoot(CSNode e) {
        root = e;
    }

    public CSNode getPromisingNode() {
        // find a promising node connected to root, then return it
        return root;
    }

    public CheckersMove getBestMove() {
        CSNode bestNode = root.getBestUCT();
        return bestNode.prevMove;
    }

    public CSNode getMaxNode(CSNode a, CSNode b) {
        if ((double) a.getWins() / (double) a.getPlayouts() > (double) b.getWins() / (double) b.getPlayouts()) {
            return a;
        }
        return b;
    }
}
