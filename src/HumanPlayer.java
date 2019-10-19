import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {

    public String name;

    @Override
    public List<Move> getNextMove() {
        Scanner input=new Scanner(System.in);

        System.out.println(name + ", it's your turn...");
        System.out.println("All available moves: (Please enter a move ID to choose a move or enter -1 to quit game)");
        List<List<Move>> availableMoves=board.printAllAvailableMove(this);

        if(availableMoves.size()==0)
            return null;

        int moveId;
        while(true) {
            moveId = input.nextInt();
            if(moveId>availableMoves.size() || moveId==0 || moveId<-1)
                System.out.println("Please choose a valid move ID");
            else
                break;
        }

        if(moveId==-1)
            return null;

        return availableMoves.get(moveId-1);
    }

    @Override
    public boolean doMove() {
        List<Move> moves=getNextMove();
        if(moves==null) return false;
        board.makeMove(moves, board.boardState[moves.get(0).row_index][moves.get(0).column_index]);
        return true;
    }
}
