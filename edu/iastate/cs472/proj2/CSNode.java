package edu.iastate.cs472.proj2;
import java.util.Random;

/**
 * Child-sibling node type for an n-ary tree.
 */
public class CSNode<E>
{
    protected CSNode<E> firstChild;
    protected CSNode<E> nextSibling;
    protected E data;
    protected int player = CheckersData.RED;

    public CSNode(){}

    public CSNode(E data)
    {
        this(data, null, null);
    }

    public CSNode(E data, CSNode<E> child, CSNode<E> sibling)
    {
        this.firstChild = child;
        this.nextSibling = sibling;
        this.data = data;
    }

    public boolean isLeaf()
    {
        return firstChild == null;
    }

    public CSNode<E> getChild()
    {
        return firstChild;
    }

    public void setChild(CSNode<E> child)
    {
        this.firstChild = child;
    }

    public CSNode<E> getSibling()
    {
        return nextSibling;
    }

    public void setSibling(CSNode<E> sibling)
    {
        this.nextSibling = sibling;
    }

    public E getData()
    {
        return data;
    }

    public void setData(E data)
    {
        this.data = data;
    }

    public void printData() {
        System.out.println(data);
    }

    public CSNode<CheckersData> expandRandomMove() {
        Random r = new Random();
        CheckersData currentData = (CheckersData) data;
        CheckersMove[] moves = currentData.getLegalMoves(player);
        int i = r.nextInt(moves.length);
        CheckersMove move = moves[i];
        CheckersData temp_board = new CheckersData(currentData.getBoard());
        temp_board.makeMove(move);
        CSNode<E> new_node = new CSNode<E>((E) temp_board);
        setChild(new_node);
        return new_node;
    }

}

