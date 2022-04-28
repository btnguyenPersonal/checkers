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
    public int player = 3; // BLACK
    int playouts = 0;
    int wins = 0;

    public CSNode(){}

    public CSNode(CheckersData data)
    {
        this(data, null, null);
    }

    public CSNode(CheckersData data, CSNode parent, ArrayList<CSNode> children)
    {
        this.parent = parent;
        this.children = children;
        this.data = data;
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
            CSNode node = new CSNode(temp_board, this, null);
        }
        setChildren(nodes);
        Random r = new Random();
        int index = r.nextInt(nodes.size());
        return nodes.get(index);
    }
}

