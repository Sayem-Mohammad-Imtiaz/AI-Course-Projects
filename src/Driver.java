import java.util.List;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Board board=new Board();

        Scanner input=new Scanner(System.in);
        System.out.println("Provide your in-game name: ");
        String userName=input.nextLine();
        System.out.println("Who will make first move? [0=Computer, 1=You]");
        int turn=input.nextInt();
        System.out.println("Enter search depth: ");
        int searchDepth=input.nextInt();

        HumanPlayer humanPlayer=new HumanPlayer();
        humanPlayer.name=userName;
        humanPlayer.setBoard(board);
        ComputerPlayer computerPlayer=new ComputerPlayer();
        computerPlayer.setBoard(board);
        computerPlayer.setSearchDepth(searchDepth);

        //WHITE makes first move.
        if(turn==1) {
            humanPlayer.setPlayerType(GameAttributes.WHITE);
            computerPlayer.setPlayerType(GameAttributes.BLACK);
        }
        else {
            humanPlayer.setPlayerType(GameAttributes.BLACK);
            computerPlayer.setPlayerType(GameAttributes.WHITE);
        }

        boolean hasHumanWon=false;
        int moveId=0;
        int totalTurn=0;
        while(moveId!=-1)
        {
            System.out.println("\nTurn No# "+totalTurn);
            System.out.println("\n---Current Board State---");
            board.printBoard();
            if(turn==1) {
                if(humanPlayer.doMove()==false)
                    moveId=-1;
            }
            else {
                System.out.println("Computer's turn...");
                if(computerPlayer.doMove()==false) {
                    moveId = -1;
                    hasHumanWon=true;
                }
            }

            turn=(turn==1)?0: 1;

            totalTurn++;
        }
        System.out.println('\n');
        System.out.println("Total turn needed to end the game: "+totalTurn);
        System.out.println("Computer took : "+computerPlayer.getTotalTimeTaken()/(double)computerPlayer.getTotalTurn()+"ms on average");
        if(hasHumanWon==true)
            System.out.println("Congratulations "+userName+", you have won");
        else
            System.out.println("Sorry "+userName+", you have lost");

    }
}
