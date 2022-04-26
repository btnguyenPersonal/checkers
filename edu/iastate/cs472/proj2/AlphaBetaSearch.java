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
            value = MaxValue(temp_state, CheckersData.RED, depth - 1, -9999, 9999)[0];
            System.out.println(i + ": " + value);
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
            double[] value = new double[3];
            value[0] = player == CheckersData.RED ? -1 : 1;
            value[1] = player == CheckersData.RED ? -1 : 1;
            value[2] = beta;
            return value;
        }
        double[] evals = new double[moves.length];
        double[] max = {-9999, alpha, beta};
        for (int i = 0; i < moves.length; i++) {
            double[] value = new double[3];
            if (depth == 0) {
                value[0] = eval(moves[i], state);
                value[1] = alpha;
                value[2] = beta;
            } else {
                CheckersData temp_state = new CheckersData(state.getBoard());
                temp_state.makeMove(moves[i]);
                value = MinValue(temp_state, swapPlayer(player), depth - 1, alpha, beta);
            }
            if (max[0] < value[0]) {
                max[0] = value[0];
            }
            if (max[0] >= alpha) {
                max[2] = max[0];
                return max;
            }
        }
        max[1] = max[0];
        return max;
    }

    double[] MinValue(CheckersData state, int player, int depth, double alpha, double beta) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves == null) {
            double[] value = new double[3];
            value[0] = player == CheckersData.RED ? -1 : 1;
            value[1] = alpha;
            value[2] = player == CheckersData.RED ? -1 : 1;
            return value;
        }
        double[] evals = new double[moves.length];
        double[] min = {9999, alpha, beta};
        for (int i = 0; i < moves.length; i++) {
            double[] value = new double[3];
            if (depth == 0) {
                value[0] = eval(moves[i], state);
            } else {
                CheckersData temp_state = new CheckersData(state.getBoard());
                temp_state.makeMove(moves[i]);
                value = MaxValue(temp_state, swapPlayer(player), depth - 1, alpha, beta);
            }
            if (min[0] > value[0]) {
                min[0] = value[0];
            }
            if (min[0] <= beta) {
                min[1] = min[0];
                return min;
            }
        }
        min[2] = min[0];
        return min;
    }

    double eval(CheckersMove move, CheckersData state) {
        CheckersData temp_state = new CheckersData(state.getBoard());
        temp_state.makeMove(move);
        return temp_state.getEvaluation();
    }

    /*
        function ALPHA-BETA-SEARCH(state) returns an action
            MAX-VALUE state,
            return the action in ACTIONS(state) with value

        function Max-Value(state) returns a utility value
            if Terminal-Test(state) returns a utility value
        for each inActions(state) do
            Max(Min-Value(result()))
            if then return
            Max
        return

        function Min-Value(state) returns a utility value
            if Terminal-Test(state) returns a utility value
        for each inActions(state) do
            Min(Max-Value(result()))
            if then return // prune
            Min
        return
    */

}
