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
        for (int playouts = 0; playouts < 1000; playouts++) {
            CSNode leaf = select(gameTree);
            CSNode child = leaf;
            if (!child.isGameOver()) {
                expand(child);
            }
            if (child.children != null) {
                child = child.getRandomMove();
            }
            int result = simulate(child);
            //gameTree.printTree();
            backpropogate(result, child);
        }
        // return best move
        return gameTree.getBestMove();
    }
    
    // selection
    // start at root, use UCT
    public CSNode select(CSTree tree) {
        CSNode leaf = tree.getRoot();
        while (!leaf.isLeaf()) {
            leaf = leaf.getBestUCT();
        }
        return leaf;
    }

    // expansion
    // choose random move from all moves
    public void expand(CSNode leaf) {
        leaf.expandNode();
    }

    // simulation
    // get evaluation
    public int simulate(CSNode expandedNode) {
        CSNode temp = expandedNode;
        if (temp.getGameScore() == 1) {
            temp.getParent().setWins(-9999);
            return 1;
        }
        while (!temp.isGameOver()) {
            temp = temp.simulateRandomMove();
        }
        return temp.getGameScore();
    }

    // back propogation
    public void backpropogate(int result, CSNode node) {
        // go back up through each parent node, and add result to it
        // prob some kind of recursive helper method
        while (node != null) {
            if (result == -1 && node.player == CheckersData.BLACK) {
                node.incWins();
            }
            if (result == 1 && node.player == CheckersData.RED) {
                node.incWins();
            }
            node.incPlayouts();
            node = node.getParent();
        }
    }
}
