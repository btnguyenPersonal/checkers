package edu.iastate.cs472.proj2; 

public class CSTree
{
	CSNode root;
	int size = 0;

    public CSTree(CSNode r) {
        root = r;
        r.expandRandomMove();
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

    public CheckersMove getBestMove() {
        int highestPlayouts = root.children.get(0).getPlayouts();
        CSNode bestNode = root.children.get(0);
        for (int i = 1; i < root.children.size(); i++) {
            if (root.children.get(i).getPlayouts() >= highestPlayouts) {
                highestPlayouts = root.children.get(i).getPlayouts();
                bestNode = root.children.get(i);
            }
        }
        return bestNode.prevMove;
    }

    public CSNode getMaxNode(CSNode a, CSNode b) {
        if ((double) a.getWins() / (double) a.getPlayouts() > (double) b.getWins() / (double) b.getPlayouts()) {
            return a;
        }
        return b;
    }

    public void printTree() {
        System.out.println("=================================root===========================");
        printNode(root);
        for (CSNode node : root.children) {
            printNode(node);
        }
    }

    public void printNode(CSNode n) {
        System.out.println();
        System.out.println(n.data);
        System.out.println(n.getWins() + "/" + n.getPlayouts());
        System.out.println();
        //if (n.children != null) {
        //    for (CSNode node : n.children) {
        //        printNode(node);
        //    }
        //}
    }
}
