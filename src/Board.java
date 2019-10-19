import java.util.ArrayList;
import java.util.List;

public class Board {
    int [][]boardState=new int[GameAttributes.BOARD_NUM_ROW][GameAttributes.BOARD_NUM_COLUMN];
    int numWhite;
    int numBlack;
    int numKingWhite;
    int numKingBlack;

    Utility util=new Utility();
    CheckerGame checkerGame=new CheckerGame();

    Board()
    {
        this.numWhite=GameAttributes.NUM_WHITE;
        this.numBlack=GameAttributes.NUM_BLACK;
        this.numKingBlack=0;
        this.numKingWhite=0;
        //initialize board
        initBoard();
    }
    public void initBoard()
    {
        int parity=1;

        int numWhite=GameAttributes.NUM_WHITE;
        int numBlack=GameAttributes.NUM_BLACK;
        int i,j;
        //place white piece in board
        for(i=0;i<GameAttributes.BOARD_NUM_ROW;i++)
        {
            for(j=0;j<GameAttributes.BOARD_NUM_COLUMN;j++)
            {
                if(util.isDarkCell(i,j, parity)==true)
                {
                    if(numWhite>0) {
                        boardState[i][j] = GameAttributes.WHITE;
                        numWhite--;
                    }
                    else
                        boardState[i][j]=GameAttributes.EMPTY_CELL;
                }
                else
                    boardState[i][j]=GameAttributes.ILLEGAL_CELL;
            }
            parity=(parity==1)?0:1;
        }

        parity=0;
        //place black piece in board
        for(i=GameAttributes.BOARD_NUM_ROW-1;i>=0;i--)
        {
            for(j=GameAttributes.BOARD_NUM_COLUMN-1;j>=0;j--)
            {
                if(util.isDarkCell(i,j,parity)==true)
                {
                    if(numBlack>0) {
                        boardState[i][j] = GameAttributes.BLACK;
                        numBlack--;
                    }
                }
                else
                    boardState[i][j]=GameAttributes.ILLEGAL_CELL;
            }
            parity=(parity==1)?0:1;
        }
    }

    public void printBoard()
    {
        int i,j;
        util.printDash();
        for(i=0;i<GameAttributes.BOARD_NUM_ROW;i++)
        {
            System.out.print('|');
            for (j = 0; j < GameAttributes.BOARD_NUM_COLUMN; j++)
            {
                if(this.boardState[i][j]==GameAttributes.WHITE)
                    System.out.print("w");
                else if(this.boardState[i][j]==GameAttributes.BLACK)
                    System.out.print('b');
                else if(this.boardState[i][j]==GameAttributes.WHITE_KING)
                    System.out.print("W");
                else if(this.boardState[i][j]==GameAttributes.BLACK_KING)
                    System.out.print('B');
                else if(this.boardState[i][j]==GameAttributes.EMPTY_CELL)
                    System.out.print(' ');
                else
                    System.out.print('-');
                System.out.print('|');
            }
            System.out.print('\n');
            util.printDash();
        }
    }

    public List<List<Move>> printAllAvailableMove(Player player)
    {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";

        List<List<Move>> availableMoves=checkerGame.getAvailableMoves(player, this);
        int moveId=1;
        for(List<Move> m: availableMoves)
        {
            System.out.print("Move "+moveId+": ");
            moveId++;
            int t=1;
            boolean isJump=util.isJump(m.get(0).row_index, m.get(1).row_index);
            for(Move move: m)
            {
                if(isJump)
                    System.out.print(ANSI_GREEN+"("+(move.row_index+1)+","+(move.column_index+1)+")"+ANSI_RESET);
                else
                    System.out.print("("+(move.row_index+1)+","+(move.column_index+1)+")");
                if(t!=m.size()) {
                    if(isJump)
                        System.out.print(ANSI_GREEN+" -> "+ANSI_RESET);
                    else
                        System.out.print(" -> ");
                }

                t++;
            }
            System.out.print('\n');
        }
        return availableMoves;
    }

    public void makeMove(List<Move> moves, int playerType)
    {
        for(int moveNum=1;moveNum<moves.size();moveNum++)
        {
            Move firstCell=moves.get(moveNum-1);
            Move secondCell=moves.get(moveNum);
            if(util.isJump(firstCell.row_index,secondCell.row_index)==true)
            {
                Move intermediateCell=util.getIntermediateCell(firstCell,secondCell);
                if(playerType==GameAttributes.WHITE || playerType==GameAttributes.WHITE_KING) {
                    if(this.boardState[intermediateCell.row_index][intermediateCell.column_index]==GameAttributes.BLACK)
                        this.numBlack--;
                    else
                        this.numKingBlack--;
                }
                else{
                    if(this.boardState[intermediateCell.row_index][intermediateCell.column_index]==GameAttributes.WHITE)
                        this.numWhite--;
                    else
                        this.numKingWhite--;
                }
                this.boardState[intermediateCell.row_index][intermediateCell.column_index]=GameAttributes.EMPTY_CELL;
            }

            if(playerType==GameAttributes.WHITE) {
                if(secondCell.row_index==GameAttributes.BOARD_NUM_ROW-1) {
                    this.numKingWhite++;
                    this.numWhite--;
                    this.boardState[secondCell.row_index][secondCell.column_index]=GameAttributes.WHITE_KING;
                }
                else
                    this.boardState[secondCell.row_index][secondCell.column_index]=playerType;

            }
            else if(playerType==GameAttributes.WHITE_KING || playerType==GameAttributes.BLACK_KING) {
                    this.boardState[secondCell.row_index][secondCell.column_index]=playerType;
            }
            else if(playerType==GameAttributes.BLACK) {
                if(secondCell.row_index==0) {
                    this.numKingBlack++;
                    this.numBlack--;
                    this.boardState[secondCell.row_index][secondCell.column_index]=GameAttributes.BLACK_KING;
                }
                else
                    this.boardState[secondCell.row_index][secondCell.column_index]=playerType;
            }

            this.boardState[firstCell.row_index][firstCell.column_index]=GameAttributes.EMPTY_CELL;

        }
    }

    //returns positive value if WHITE player has more pieces remaining, otherwise, returns negative value if game is in favor of
    //BLACK player at this moment
    public int evaluationFunction()
    {
        return (((this.numWhite*10)+(this.numKingWhite*20))-((this.numBlack*10)-(this.numKingBlack*20)));
    }

}

