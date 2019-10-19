import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer extends Player {

    int searchDepth;
    double totalTimeTaken;
    int totalTurn;

    ComputerPlayer()
    {
        totalTimeTaken=0;
        totalTurn=0;
    }
    public int getTotalTurn() {
        return totalTurn;
    }

    public void setTotalTurn(int totalTurn) {
        this.totalTurn = totalTurn;
    }

    public double getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(double totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public int getSearchDepth() {
        return searchDepth;

    }

    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    @Override
    List<Move> getNextMove() {
        List<Move> moves=new ArrayList<>();
        board.checkerGame.minimax(this.getPlayerType(), 0, searchDepth, board, Integer.MIN_VALUE, Integer.MAX_VALUE
             ,moves);

        if(moves.isEmpty())
            return null;

        return moves;
    }

    @Override
    boolean doMove()
    {
        long start = System.currentTimeMillis();
        List<Move> moves=getNextMove();
        long end = System.currentTimeMillis();
        totalTimeTaken+=(end-start);
        totalTurn++;

        System.out.println("Computer took "+(end-start)+"ms to decide this move");

        if(moves==null) return false;
        board.makeMove(moves, board.boardState[moves.get(0).row_index][moves.get(0).column_index]);
        return true;
    }
}
