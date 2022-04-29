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
    public CheckersMove prevMove;
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
        this.player = player;
    }


    public boolean isLeaf()
    {
        return children == null;
    }

    public void addChild(CSNode c)
    {
        children.add(c);
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

    public void setWins(int w) {
        wins = w;
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

    public CSNode getParent()
    {
        return parent;
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

    public void expandNode() {
        CheckersMove[] moves = data.getLegalMoves(player);
        ArrayList<CSNode> nodes = new ArrayList<CSNode>();
        for (int i = 0; i < moves.length; i++) {
            CheckersData temp_board = new CheckersData(data.getBoard());
            temp_board.makeMove(moves[i]);
            CSNode node = new CSNode(temp_board, this, null, swapPlayer(player));
            node.prevMove = moves[i];
            nodes.add(node);
        }
        setChildren(nodes);
    }

    public CSNode getRandomMove() {
        Random r = new Random();
        int index = r.nextInt(children.size());
        return children.get(index);
    }

    public CSNode expandRandomMove() {
        CheckersMove[] moves = data.getLegalMoves(player);
        ArrayList<CSNode> nodes = new ArrayList<CSNode>();
        for (int i = 0; i < moves.length; i++) {
            CheckersData temp_board = new CheckersData(data.getBoard());
            temp_board.makeMove(moves[i]);
            CSNode node = new CSNode(temp_board, this, null, swapPlayer(player));
            node.prevMove = moves[i];
            nodes.add(node);
        }
        setChildren(nodes);
        Random r = new Random();
        int index = r.nextInt(moves.length);
        return nodes.get(index);
    }

    public CSNode simulateRandomMove() {
        CheckersMove[] moves = data.getLegalMoves(player);
        Random r = new Random();
        int index = r.nextInt(moves.length);
        CheckersData temp_board = new CheckersData(data.getBoard());
        temp_board.makeMove(moves[index]);
        return new CSNode(temp_board, null, null, swapPlayer(player));
    }

    public int swapPlayer(int player) {
        return (player == CheckersData.RED ? CheckersData.BLACK : CheckersData.RED);
    }

    public double getUCT() {
        if (playouts == 0) {
            return 99999;
        }
        return ((double) wins / (double) playouts) + 1.41 * Math.sqrt(Math.log((double) parent.getPlayouts()) / (double) playouts);
    }

    public CSNode getBestUCT() {
        double max = children.get(0).getUCT();
        int index = 0;
        for (int i = 1; i < children.size(); i++) {
            if (max < children.get(i).getUCT()) {
                max = children.get(i).getUCT();
                index = i;
            }
        }
        return children.get(index);
    }

    public boolean isGameOver() {
        // since draws are not possible in this game of checkers
        // do not need to factor them in
        return getGameScore() == -1 || getGameScore() == 1;
    }

    public int getGameScore() {
        if (data.getLegalMoves(player) == null) {
            return player == CheckersData.RED ? 1 : -1;
        }
        return (int) data.getEvaluation();
    }

}

