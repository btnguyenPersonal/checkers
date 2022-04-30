package edu.iastate.cs472.proj2; 

/**
 *
 * @author btnguyen Ben Nguyen
 *
 */
public class CheckersTree
{
	CheckersNode root;
	int size = 0;

    public CheckersTree(CheckersNode r) {
        root = r;
        r.expandRandomMove();
    }

    public CheckersNode getRoot() {
        return root;
    }

    public int getSize() {
        return size;
    }

    public void incSize() {
        size++;
    }

    public void setRoot(CheckersNode e) {
        root = e;
    }

    public CheckersMove getBestMove() {
        int highestPlayouts = root.children.get(0).getPlayouts();
        CheckersNode bestNode = root.children.get(0);
        for (int i = 1; i < root.children.size(); i++) {
            if (root.children.get(i).getPlayouts() >= highestPlayouts) {
                highestPlayouts = root.children.get(i).getPlayouts();
                bestNode = root.children.get(i);
            }
        }
        return bestNode.prevMove;
    }

    public CheckersNode getMaxNode(CheckersNode a, CheckersNode b) {
        if ((double) a.getWins() / (double) a.getPlayouts() > (double) b.getWins() / (double) b.getPlayouts()) {
            return a;
        }
        return b;
    }

    public void printTree() {
        System.out.println("=================================root===========================");
        printNode(root);
        for (CheckersNode node : root.children) {
            printNode(node);
        }
    }

    public void printNode(CheckersNode n) {
        System.out.println();
        System.out.println(n.data);
        System.out.println(n.getWins() + "/" + n.getPlayouts());
        System.out.println();
        //if (n.children != null) {
        //    for (CheckersNode node : n.children) {
        //        printNode(node);
        //    }
        //}
    }
}
