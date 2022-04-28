package edu.iastate.cs472.proj2;
import java.util.Random;
import java.util.ArrayList;

/**
 * Child-sibling node type for an n-ary tree.
 */
public class CSNode
{
    public CSNode parent;
    public ArrayList<CSNode> children;
    public CheckersData data;
    public int player;
    int playouts = 0;
    int wins = 0;

    public CSNode(){}

    public CSNode(CheckersData data, int player)
    {
        this(data, null, null, player);
    }

    public CSNode(CheckersData data, CSNode parent, ArrayList<CSNode> children, int player)
    {
        this.parent = parent;
        this.children = children;
        this.data = data;
        this.player = (player == CheckersData.RED ? CheckersData.BLACK : CheckersData.RED);
    }

    public boolean isLeaf()
    {
        return children == null;
    }

    public ArrayList<CSNode> getChildren()
    {
        return children;
    }

    public void incWins() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public void incPlayouts() {
        playouts++;
    }

    public int getPlayouts() {
        return playouts;
    }

    public void setChildren(ArrayList<CSNode> children)
    {
        this.children = children;
    }

    public CheckersData getData()
    {
        return data;
    }

    public void setData(CheckersData data)
    {
        this.data = data;
    }

    public void printData() {
        System.out.println(data);
    }

    // need to test
    public CSNode expandRandomMove() {
        CheckersMove[] moves = data.getLegalMoves(player);
        ArrayList<CSNode> nodes = new ArrayList<CSNode>();
        for (int i = 0; i < moves.length; i++) {
            CheckersData temp_board = new CheckersData(data.getBoard());
            temp_board.makeMove(moves[i]);
            CSNode node = new CSNode(temp_board, this, null, player);
        }
        setChildren(nodes);
        Random r = new Random();
        int index = r.nextInt(nodes.size());
        return nodes.get(index);
    }

    public double getUCT() {
        return ((double) wins / (double) playouts) + 1.41 * Math.sqrt(Math.log(parent.getPlayouts()) / (double) playouts);
    }

    public CSNode getBestUCT() {
        double max = -9999;
        for (CSNode node : children) {
            if (max < node.getUCT()) {
                max = node.getUCT();
            }
        }
        for (CSNode node : children) {
            if (max == node.getUCT()) {
                return node;
            }
        }
        System.out.println("Error lowets UCB");
        return children.get(0);
    }

    public boolean isGameOver() {
        // since draws are not possible in this game of checkers
        // do not need to factor them in
        return data.getEvaluation() == -1 || data.getEvaluation() == 1;
    }

    public int getGameScore() {
        return (int) data.getEvaluation();
    }

}

