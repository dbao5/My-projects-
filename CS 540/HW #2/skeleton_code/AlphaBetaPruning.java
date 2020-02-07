public class AlphaBetaPruning {

    private GameState root;
    double Value ;
    int vis ;
    int eva ;
    int maxDepth ;
    int step ;
    double avgbf ;


    public AlphaBetaPruning() {
         Value = 0.0;
        vis = 1;
        eva = 0;
       maxDepth = 0;
       step = 0;
       avgbf = 0.0;
    }

    /**
     * This function will print out the information to the terminal,
     * as specified in the homework description.
     */
    public void printStats() {
        System.out.println("Move: " + this.root.bestMove);
        System.out.println("Value: " + String.format( "%.1f", Value));
        System.out.println("Number of Nodes Visited: " + vis);
        System.out.println("Number of Nodes Evaluated: " + eva);
        System.out.println("Max Depth Reached : " + maxDepth);
        System.out.println("Avg Effective Branching Factor: " + String.format( "%.1f", avgbf));
    }

    /**
     * This function will start the alpha-beta search
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
        step = depth;
        boolean maxPlayer = false;
        this.root = state;
        int count = 0;
        double beta = Double.POSITIVE_INFINITY;
        double alpha = Double.NEGATIVE_INFINITY;
        for(int i = 1; i < state.getSize(); i++)
            if(!state.getStone(i)) count++;
        if (count % 2 == 0) maxPlayer = true;
        this.Value = alphabeta(state, depth, alpha, beta, maxPlayer);

        avgbf = (double)(this.vis - 1) / (double)(this.vis - this.eva);
        maxDepth = depth - step;
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
        double value;
        if(depth < this.step) {
            step = depth;
        }
        if(state.getSuccessors().size() == 0) {
            this.eva++;
            return state.evaluate();
        }
        if (0 == depth) {
            this.eva++;
            return state.evaluate();
        }

        if(maxPlayer) {
            double Maxvalue = Double.NEGATIVE_INFINITY;
            for(int i = 0; i< state.getSuccessors().size(); i++) {
                this.vis++;
                value = alphabeta(state.getSuccessors().get(i), depth - 1, alpha, beta, false);
                if(Maxvalue < value) {
                    state.bestMove = state.getSuccessors().get(i).getLastMove();
                }
                Maxvalue = Math.max(Maxvalue, value);
                alpha = Math.max(alpha, Maxvalue);
                if(alpha >= beta) {
                    break;
                }
            }
            return Maxvalue;
        } else {
            double MinValue = Double.POSITIVE_INFINITY;
            for(int i = 0; i< state.getSuccessors().size(); i++) {
                this.vis++;
                value = alphabeta(state.getSuccessors().get(i), depth - 1, alpha, beta, true);
                if(MinValue > value) {
                    state.bestMove = state.getSuccessors().get(i).getLastMove();
                }
                MinValue = Math.min(MinValue, value);
                beta = Math.min(beta, MinValue);
                if(alpha >= beta) {
                    break;
                }
            }
            return MinValue;
        }
    }
}





















