package edu.iastate.cs472.proj2;

/**
 *
 * @author btnguyen Ben Nguyen
 *
 */

/**
 * This class implements the Alpha-Beta pruning algorithm to find the best
 * move at current state.
*/
public class AlphaBetaSearch extends AdversarialSearch {

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
        return AlphaBetaSearch(legalMoves);
    }

    int swapPlayer(int player) {
        if (player == CheckersData.RED) {
            return CheckersData.BLACK;
        } else {
            return CheckersData.RED;
        }
    }

    CheckersMove AlphaBetaSearch(CheckersMove[] legalMoves) {
        int depth = 8;
        double min = 9999;
        double value = 0;
        int lowestIndex = 0;
        for (int i = 0; i < legalMoves.length; i++) {
            CheckersData temp_state = new CheckersData(board.getBoard());
            temp_state.makeMove(legalMoves[i]);
            value = MaxValue(temp_state, CheckersData.RED, depth - 1, -9999, 9999)[0];
            if (min > value) {
                min = value;
                lowestIndex = i;
            }
        }
        return legalMoves[lowestIndex];
    }

    double[] MaxValue(CheckersData state, int player, int depth, double alpha, double beta) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves == null) {
            double[] value = new double[2];
            value[0] = player == CheckersData.RED ? -1 : 1;
            return value;
        }
        double[] v = {alpha, beta};
        for (int i = 0; i < moves.length; i++) {
            // if reached max depth, set B to eval
            if (depth == 0) {
                v[0] = eval(state);
                break;
            } else {
                // get value of child node
                double[] new_value = new double[2];
                CheckersData temp_state = new CheckersData(state.getBoard());
                temp_state.makeMove(moves[i]);
                new_value = MinValue(temp_state, swapPlayer(player), depth - 1, v[0], v[1]);
                // if A >= B prune
                if (v[0] >= v[1]) {
                    return v;
                }
                // if A < B_child
                if (v[0] < new_value[1]) {
                    // set A to B_child
                    v[0] = new_value[1];
                }
            }
        }
        return v;
    }

    double[] MinValue(CheckersData state, int player, int depth, double alpha, double beta) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves == null) {
            double[] value = new double[2];
            value[1] = player == CheckersData.RED ? -1 : 1;
            return value;
        }
        double[] v = {alpha, beta};
        for (int i = 0; i < moves.length; i++) {
            // if reached max depth, set A to eval
            if (depth == 0) {
                v[1] = eval(state);
                break;
            } else {
                // get value of child node
                double[] new_value = new double[2];
                CheckersData temp_state = new CheckersData(state.getBoard());
                temp_state.makeMove(moves[i]);
                new_value = MaxValue(temp_state, swapPlayer(player), depth - 1, v[0], v[1]);
                // if B <= A prune
                if (v[1] <= v[0]) {
                    return v;
                }
                // if B > A_child
                if (v[1] > new_value[0]) {
                    // set B to A_child
                    v[1] = new_value[0];
                } 
            }
        }
        return v;
    }

    double eval(CheckersData state) {
        return state.getEvaluation();
    }

}
