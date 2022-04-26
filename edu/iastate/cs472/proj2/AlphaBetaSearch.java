package edu.iastate.cs472.proj2;

/**
 *
 * @author btnguyen
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
            value = MaxValue(temp_state, CheckersData.RED, depth - 1, -9999, 9999)[1];
            System.out.println(i + ": " + value);
            if (min > value) {
                min = value;
                lowestIndex = i;
            }
        }
        System.out.println("Index: " + lowestIndex);
        return legalMoves[lowestIndex];
    }

    double[] MaxValue(CheckersData state, int player, int depth, double alpha, double beta) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves == null) {
            double[] value = new double[3];
            value[1] = player == CheckersData.RED ? -1 : 1;
            return value;
        }
        double[] v = {alpha, beta};
        for (int i = 0; i < moves.length; i++) {
            if (depth == 0) {
                v[1] = eval(moves[i], state);
            } else {
                double[] new_value = new double[2];
                CheckersData temp_state = new CheckersData(state.getBoard());
                temp_state.makeMove(moves[i]);
                new_value = MinValue(temp_state, swapPlayer(player), depth - 1, v[0], v[1]);
                if (v[0] < new_value[0]) {
                    v[0] = new_value[0];
                }
                if (v[0] >= v[1]) {
                    System.out.println("pruning max");
                    return v;
                }
            }
        }
        return v;
    }

    double[] MinValue(CheckersData state, int player, int depth, double alpha, double beta) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves == null) {
            double[] value = new double[3];
            value[1] = player == CheckersData.RED ? -1 : 1;
            return value;
        }
        double[] v = {alpha, beta};
        for (int i = 0; i < moves.length; i++) {
            if (depth == 0) {
                v[0] = eval(moves[i], state);
            } else {
                double[] new_value = new double[2];
                CheckersData temp_state = new CheckersData(state.getBoard());
                temp_state.makeMove(moves[i]);
                new_value = MaxValue(temp_state, swapPlayer(player), depth - 1, v[0], v[1]);
                if (v[1] > new_value[1]) {
                    v[1] = new_value[1];
                } 
                if (v[1] <= v[0]) {
                    System.out.println("pruning min");
                    return v;
                }
            }
        }
        return v;
    }

    double eval(CheckersMove move, CheckersData state) {
        CheckersData temp_state = new CheckersData(state.getBoard());
        temp_state.makeMove(move);
        return temp_state.getEvaluation();
    }

}
