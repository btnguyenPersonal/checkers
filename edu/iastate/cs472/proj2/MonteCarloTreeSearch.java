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

        CSNode root = new CSNode(board, CheckersData.BLACK);
        CSTree gameTree = new CSTree(root);
        gameTree.getRoot().printData();
        for (int playouts = 0; playouts < 200; playouts++) {
            CSNode leaf = select(gameTree);
            CSNode child = expand(leaf);
            int result = simulate(child);
            backpropogate(result, child);
        }
        
        // return best move
        return legalMoves[0];
    }
    
    // selection
    // start at root, use UCB
    public CSNode select(CSTree tree) {
        CSNode leaf = tree.getRoot();
        while (!leaf.isLeaf()) {
            leaf = leaf.getBestUCT();
        }
        return leaf;
    }

    // expansion
    // choose random move from all moves
    public CSNode expand(CSNode leaf) {
        return leaf.expandRandomMove();
    }

    // simulation
    // get evaluation
    public int simulate(CSNode expandedNode) {
        CSNode temp = expandedNode;
        while (!temp.isGameOver()) {
            temp = temp.expandRandomMove();
        }
        return temp.getGameScore();
    }

    // back propogation
    // if draw, give back 0.5
    public void backpropogate(int result, CSNode node) {
        // go back up through each parent node, and add result to it
        // prob some kind of recursive helper method
    }
}
