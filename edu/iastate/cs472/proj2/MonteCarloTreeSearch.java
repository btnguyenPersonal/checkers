package edu.iastate.cs472.proj2;

/**
 * 
 * @author btnguyen Ben Nguyen
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

        CheckersNode root = new CheckersNode(board, CheckersData.BLACK);
        CheckersTree gameTree = new CheckersTree(root);
        for (int playouts = 0; playouts < 1000; playouts++) {
            CheckersNode leaf = select(gameTree);
            CheckersNode child = leaf;
            if (!child.isGameOver()) {
                expand(child);
            }
            if (child.children != null) {
                child = child.getRandomMove();
            }
            double result = simulate(child);
            //gameTree.printTree();
            backpropogate(result, child);
        }
        // return best move
        return gameTree.getBestMove();
    }
    
    // start at root, use UCB to select most promising node
    public CheckersNode select(CheckersTree tree) {
        CheckersNode leaf = tree.getRoot();
        while (!leaf.isLeaf()) {
            leaf = leaf.getBestUCB();
        }
        return leaf;
    }

    // choose random move from all possible moves from leaf position
    public void expand(CheckersNode leaf) {
        leaf.expandNode();
    }

    // get evaluation afer playing random simulated game
    public double simulate(CheckersNode expandedNode) {
        CheckersNode temp = expandedNode;
        if (temp.data.isDraw()) {
            return 0.5;
        }
        if (temp.getGameScore() == 1) {
            temp.getParent().setWins(-9999);
            return 1;
        }
        while (!temp.isGameOver()) {
            temp = temp.simulateRandomMove();
        }
        return temp.getGameScore();
    }

    // back propogation to bring the result back up the tree
    public void backpropogate(double result, CheckersNode node) {
        while (node != null) {
            if (result == 0.5) {
                node.incHalfWins();
            }
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
