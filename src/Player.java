import java.util.List;

public abstract class Player {
    int playerType;
    Board board;
    abstract List<Move> getNextMove();
    abstract boolean doMove();
    void setPlayerType(int type)
    {
        this.playerType=type;
    }
    int getPlayerType()
    {
        return  this.playerType;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
