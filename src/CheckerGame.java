import java.util.ArrayList;
import java.util.List;

public class CheckerGame {
    Utility util=new Utility();

    int [] whitePlayerMoveRow={+1,+1,+2,+2};
    int [] whitePlayerMoveColumn={+1,-1,+2,-2};

    int [] blackPlayerMoveRow={-1,-1,-2,-2};
    int [] blackPlayerMoveColumn={+1,-1,+2,-2};

    public void getAllMove(int moveIndex, int current_row,int current_column,int playerType, Board board, List<Move> currentMove, List<List<Move>> allMoves)
    {

        if((playerType==GameAttributes.WHITE && current_row==GameAttributes.BOARD_NUM_ROW-1) ||
                (playerType==GameAttributes.BLACK && current_row==0))
        {
            //no more jump
            if(currentMove.size()>1)
            {
                allMoves.add(new ArrayList(currentMove));
            }
            return;
        }

        int i,j;
        int next_row,next_column;
        boolean jumpMade=false;
        if(playerType==GameAttributes.WHITE || playerType==GameAttributes.BLACK_KING || playerType==GameAttributes.WHITE_KING)
        {
            for(i=moveIndex;i<whitePlayerMoveRow.length;i++)
            {
                next_row=current_row+whitePlayerMoveRow[i];
                next_column=current_column+whitePlayerMoveColumn[i];
                if(util.isLegalMove(current_row,current_column,next_row,next_column,board,playerType)==true)
                {
                    currentMove.add(new Move(next_row, next_column));

                    //if jump then try all possible jump
                    if(i>1) {
                        jumpMade=true;

                        //save state before making move
                        int [][] tempBoard=new int[GameAttributes.BOARD_NUM_ROW][GameAttributes.BOARD_NUM_COLUMN];
                        util.copyBoard(board.boardState, tempBoard);
                        int numWhite=board.numWhite;
                        int numBlack=board.numBlack;
                        int numWhiteKing=board.numKingWhite;
                        int numBlackKing=board.numKingBlack;

                        board.makeMove(currentMove, playerType);

                        //only allow jump next
                        getAllMove(2, next_row, next_column, playerType, board, currentMove, allMoves);

                        //undo move
                        util.copyBoard(tempBoard, board.boardState);
                        board.numWhite=numWhite;
                        board.numBlack=numBlack;
                        board.numKingWhite=numWhiteKing;
                        board.numKingBlack=numBlackKing;
                    }

                    //if single cell move, then no further exploration
                    if(moveIndex==0&&i<2)
                    {
                        allMoves.add(new ArrayList(currentMove));
                    }
                    currentMove.remove(currentMove.size() - 1);

                }
            }
        }


        if(playerType==GameAttributes.BLACK || playerType==GameAttributes.BLACK_KING || playerType==GameAttributes.WHITE_KING)
        {
            for(i=moveIndex;i<blackPlayerMoveColumn.length;i++)
            {
                next_row=current_row+blackPlayerMoveRow[i];
                next_column=current_column+blackPlayerMoveColumn[i];
                if(util.isLegalMove(current_row,current_column,next_row,next_column,board,playerType)==true)
                {
                    currentMove.add(new Move(next_row, next_column));
                    //if jump then try all possible jump
                    if(i>1) {
                        jumpMade=true;

                        //save state before making move
                        int [][] tempBoard=new int[GameAttributes.BOARD_NUM_ROW][GameAttributes.BOARD_NUM_COLUMN];
                        util.copyBoard(board.boardState, tempBoard);
                        int numWhite=board.numWhite;
                        int numBlack=board.numBlack;
                        int numWhiteKing=board.numKingWhite;
                        int numBlackKing=board.numKingBlack;

                        board.makeMove(currentMove, playerType);

                        //only allow jump next
                        getAllMove(2, next_row, next_column, playerType, board, currentMove, allMoves);

                        //undo move
                        util.copyBoard(tempBoard, board.boardState);
                        board.numWhite=numWhite;
                        board.numBlack=numBlack;
                        board.numKingWhite=numWhiteKing;
                        board.numKingBlack=numBlackKing;
                    }

                    //if single cell move, then no further exploration
                    if(moveIndex==0&&i<2)
                    {
                        allMoves.add(new ArrayList(currentMove));
                    }
                    currentMove.remove(currentMove.size() - 1);
                }
            }
        }

        if(jumpMade==false && currentMove.size()>1)
        {
            allMoves.add(new ArrayList(currentMove));
        }

    }

    public List<List<Move>> getAvailableMoves(Player player, Board board)
    {
        List<List<Move>> allMoves=new ArrayList<>();
        int i,j;
        for(i=0;i<GameAttributes.BOARD_NUM_ROW;i++) {
            for (j = 0; j < GameAttributes.BOARD_NUM_COLUMN; j++)
            {
                if(util.isSameTypeGamePiece(board.boardState[i][j],player.getPlayerType())==true)
                {
                    List<Move> currentMove=new ArrayList<>();
                    currentMove.add(new Move(i,j));
                    this.getAllMove(0,i,j,board.boardState[i][j],board,currentMove,allMoves);
                }
            }
        }
        return allMoves;
    }

    public int minimax(int currentPlayer, int depth,int maxDepth, Board board,int alpha, int beta, List<Move> move)
    {
        if(depth==maxDepth)
        {
            return board.evaluationFunction();
        }

        int i,j;
        int minValue=Integer.MAX_VALUE;
        int maxValue=Integer.MIN_VALUE;

        for(i=0; beta>alpha && i<GameAttributes.BOARD_NUM_ROW;i++) {
            for (j = 0; beta>alpha && j < GameAttributes.BOARD_NUM_COLUMN; j++)
            {
                if(util.isSameTypeGamePiece(board.boardState[i][j], currentPlayer)==true)
                {
                    List<Move> currentMove=new ArrayList<>();
                    currentMove.add(new Move(i,j));
                    List<List<Move>> allMoves=new ArrayList<>();
                    //get all possible moves for current gamepiece
                    this.getAllMove(0,i,j,board.boardState[i][j],board,currentMove,allMoves);

                    //try all gamepiece
                    for(List<Move> m: allMoves)
                    {
                        //save state before making move
                        int [][] tempBoard=new int[GameAttributes.BOARD_NUM_ROW][GameAttributes.BOARD_NUM_COLUMN];
                        util.copyBoard(board.boardState, tempBoard);
                        int numWhite=board.numWhite;
                        int numBlack=board.numBlack;
                        int numWhiteKing=board.numKingWhite;
                        int numBlackKing=board.numKingBlack;

                        //make move
                        board.makeMove(m,currentPlayer);

                        //next player turns
                        int currentVal=minimax( (currentPlayer==GameAttributes.WHITE)?GameAttributes.BLACK: GameAttributes.WHITE,
                                depth+1,maxDepth,
                                board, alpha, beta, move);
                        //maximizing player
                        if(currentPlayer==GameAttributes.WHITE)
                        {
                            maxValue=Math.max(maxValue, currentVal);
                            alpha=Math.max(alpha, maxValue);

                            if(depth==0&& currentVal==maxValue) {
                                move.clear();
                                move.addAll(new ArrayList(m));
                            }
                        }
                        else
                        {
                            minValue=Math.min(minValue, currentVal);
                            beta=Math.min(beta, minValue);

                            if(depth==0&& currentVal==minValue) {
                                move.clear();
                                move.addAll(new ArrayList(m));
                            }
                        }

                        //undo move
                        util.copyBoard(tempBoard, board.boardState);
                        board.numWhite=numWhite;
                        board.numBlack=numBlack;
                        board.numKingWhite=numWhiteKing;
                        board.numKingBlack=numBlackKing;

                        if(beta<=alpha)
                            break;

                    }
                }
            }
        }

        if(currentPlayer==GameAttributes.WHITE)
            return maxValue;
        return minValue;
    }
}
