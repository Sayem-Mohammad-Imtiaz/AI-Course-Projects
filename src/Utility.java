public class Utility {
    public boolean isDarkCell(int rowIndex, int columnIndex, int parity)
    {
        int cellSerialNo=(rowIndex*GameAttributes.BOARD_NUM_COLUMN)+columnIndex+1;

        return ((cellSerialNo%2)==parity);
    }

    public void printDash()
    {
        for(int i=0;i<GameAttributes.BOARD_PRINT_SIZE;i++)
            System.out.print('-');
        System.out.print('\n');
    }
    public boolean isLegalMove(int initial_row,int initial_column,int goal_row,int goal_column, Board board, int currentPlayerType)
    {
        if(goal_row<0 || goal_column < 0)
            return false;
        if(goal_row>=GameAttributes.BOARD_NUM_ROW || goal_column>=GameAttributes.BOARD_NUM_COLUMN)
            return false;
        if(board.boardState[goal_row][goal_column]!=GameAttributes.EMPTY_CELL)
            return false;
        if(Math.abs(initial_row-goal_row)==2) //jump detected
        {
            int ri,ci;
            if(initial_row<goal_row)
                ri=1;
            else
                ri=-1;

            if(initial_column<goal_column)
                ci=1;
            else
                ci=-1;

            if(board.boardState[initial_row+ri][initial_column+ci]==GameAttributes.EMPTY_CELL)
                return false;

            if(isSameTypeGamePiece(board.boardState[initial_row+ri][initial_column+ci],currentPlayerType)==true)
                return false;

        }
        return true;
    }
    public boolean isJump(int initial_row,int goal_row)
    {
        return (Math.abs(initial_row-goal_row)==2);
    }
    public Move getIntermediateCell(Move first, Move second)
    {
        int ri,ci;
        if(first.row_index<second.row_index)
            ri=1;
        else
            ri=-1;

        if(first.column_index<second.column_index)
            ci=1;
        else
            ci=-1;

        return new Move(first.row_index+ri, first.column_index+ci);
    }
    public boolean isSameTypeGamePiece(int firstType, int secondType)
    {
        if(firstType==secondType)
            return true;
        if(firstType==GameAttributes.WHITE && secondType==GameAttributes.WHITE_KING)
            return true;
        if(firstType==GameAttributes.BLACK && secondType==GameAttributes.BLACK_KING)
            return true;
        if(firstType==GameAttributes.WHITE_KING && secondType==GameAttributes.WHITE)
            return true;
        if(firstType==GameAttributes.BLACK_KING && secondType==GameAttributes.BLACK)
            return true;
        return false;
    }
    public void copyBoard(int [][]source, int [][]target)
    {
        for(int i=0;i<GameAttributes.BOARD_NUM_ROW;i++)
            for(int j=0;j<GameAttributes.BOARD_NUM_COLUMN;j++)
            {
                target[i][j]=source[i][j];
            }
    }
}
