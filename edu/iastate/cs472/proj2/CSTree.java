package edu.iastate.cs472.proj2; 

public class CSTree<E> 
{
	CSNode<E> root;
	int size = 0;

    public CSTree(CSNode<E> r) {
        root = r;
    }

    public CSNode<E> getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public void incSize() {
        size++;
    }

    public void setRoot(CSNode<E> e) {
        root = e;
    }

    public CSNode<E> getPromisingNode() {
        // find a promising node connected to root, then return it
        return root;
    }
}
