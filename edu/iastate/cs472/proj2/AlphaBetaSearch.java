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
        // The checker board state can be obtained from this.board,
        // which is a int 2D array. The numbers in the `board` are
        // defined as
        // 0 - empty square,
        // 1 - red man
        // 2 - red king
        // 3 - black man
        // 4 - black king
        System.out.println(board);
        System.out.println();
        return AlphaBetaSearch(legalMoves);
    }

    int swapPlayer(int player) {
        return player == CheckersData.RED ? CheckersData.BLACK : CheckersData.RED;
    }

    CheckersMove AlphaBetaSearch(CheckersMove[] legalMoves) {
        int depth = 3;
        double min = MinValue(board, CheckersData.BLACK, depth);
        System.out.println("min: " + min);
        for (int i = 0; i < legalMoves.length; i++) {
            if (eval(legalMoves[i], board) == min) {
                return legalMoves[i];
            }
        }
        return legalMoves[0];
    }

    double MaxValue(CheckersData state, int player, int depth) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves.length == 0) {
            return player == CheckersData.RED ? -1 : 1;
        }
        double[] evals = new double[moves.length];
        double max = -9999;
        for (int i = 0; i < moves.length; i++) {
            double value;
            if (depth == 0) {
                value = eval(moves[0], state);
            } else {
                CheckersData temp_state = new CheckersData(state.getBoard());
                value = MinValue(temp_state, swapPlayer(player), depth - 1);
            }
            if (max < value) {
                max = value;
            }
        }
        return max;
    }

    double MinValue(CheckersData state, int player, int depth) {
        CheckersMove[] moves = state.getLegalMoves(player);
        if (moves.length == 0) {
            return player == CheckersData.RED ? -1 : 1;
        }
        double[] evals = new double[moves.length];
        double min = 9999;
        for (int i = 0; i < moves.length; i++) {
            double value;
            if (depth == 0) {
                value = eval(moves[0], state);
            } else {
                CheckersData temp_state = new CheckersData(state.getBoard());
                value = MaxValue(temp_state, swapPlayer(player), depth - 1);
            }
            if (min > value) {
                min = value;
            }
        }
        return min;
    }

    double eval(CheckersMove move, CheckersData state) {
        CheckersData temp_state = new CheckersData(state.getBoard());
        System.out.println("\n BEFORE: \n" + temp_state);
        temp_state.makeMove(move);
        System.out.println("\n AFTER: \n" + temp_state);
        System.out.println(temp_state.getEvaluation());
        return temp_state.getEvaluation();
    }

    // TODO
    // Implement your helper methods here.
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
