package edu.iastate.cs472.proj2;

/**
 * 
 * @author 
 *
 */

/**
 * This class implements the Monte Carlo tree search method to find the best
 * move at the current state.
 */
public class MonteCarloTreeSearch extends AdversarialSearch {

	/**
     * The input parameter legalMoves contains all the possible moves.
     * It contains four integers:  fromRow, fromCol, toRow, toCol
     * which represents a move from (fromRow, fromCol) to (toRow, toCol).
     * It also provides a utility method `isJump` to see whether this
     * move is a jump or a simple move.
     *
     * Update 03/18: each legalMove in the input now contains a single move
     * or a sequence of jumps: (rows[0], cols[0]) -> (rows[1], cols[1]) ->
     * (rows[2], cols[2]).
     *
     * @param legalMoves All the legal moves for the agent at current step.
     */
    public CheckersMove makeMove(CheckersMove[] legalMoves) {
        System.out.println(board);
        System.out.println();

        //  function MONTE-CARLO-TREE-SEARCH(state) returns an action
        //    tree <- NODE(state)
        //    while TIME-REMAINING() do
        //    	leaf <- SELECT(tree)
        //    	child <- EXPAND(leaf)
        //    	result <- SIMULATE(child)
        //    	BACKPROPAGATE(result, child)
        //    return the move in ACTIONS(state) whose node has highest number of playouts

        CSNode<CheckersData> root = new CSNode<CheckersData>(board);
        CSTree<CheckersData> gameTree = new CSTree<CheckersData>(root);
        gameTree.getRoot().printData();
        for (int playouts = 0; playouts < 200; playouts++) {

        }
        
        return legalMoves[0];
    }
    
    // selection
    // start at root, use UCB
    
    // expansion
    // choose random move from all moves

    // simulation
    //

    // back propogation
    // if draw, give back 0.5

}
