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
}
