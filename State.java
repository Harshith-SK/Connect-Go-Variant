public class State {

    static final int X = 1;     // Player "X"
    static final int O = -1;    // Player "O"
    public static final int EMPTY = 0;       // Blank space

    // We need to know the player that made the last move
    // public GamePlay lastMove;
    protected int lastPlayerPlayed;
    protected int winner;
    protected int [][] gameBoard;
    protected String winningMethod;       // Winner by [row, column, diagonal]
    protected int numRows;
    protected int numCols;
    protected int numNeededToWin;
    //------------

    //Constructor of a state (board)
    public State(int num_rows, int num_cols, int num_needed_to_win) {
        numRows = num_rows;
        numCols = num_cols;
        numNeededToWin = num_needed_to_win;
        // lastMove = new GamePlay();
        lastPlayerPlayed = O; // The user starts first
        winner = 0;
        gameBoard = new int[numRows][numCols];
        for(int i=0; i<numRows; i++) {
            for(int j=0; j<numCols; j++) {
                gameBoard[i][j] = EMPTY;
            }
        }
    }//end Constructor

    //Checks whether a move is valid; whether a square is empty. Used only when we need to expand a movement
    public boolean isValidMove(int col) {
        int row = getRowPosition(col);
        if ((row < 0) || (col < 0) || (row >= this.numRows) || (col >= this.numCols)) {
            return false;
        }
        if(gameBoard[row][col] != EMPTY) {
            return false;
        }
        return true;
    }//end isValidMove

    //Is used when we need to make a movement (Is possible to move the piece?)
    public boolean canMove(int row, int col) {
        //We evaluate mainly the limits of the board
        if ((row < 0) || (col < 0) || (row >= this.numRows) || (col >= this.numCols)) {
            return false;
        }
        return true;
    }//end CanMove

    //Is a column full?
    public boolean checkFullColumn(int col) {
        if (gameBoard[0][col] == EMPTY)
            return false;
        else{
            return true;
        }
    }//end checkFullColumn

    //We search for a blank space in the board to put the piece ('X' or 'O')
    public int getRowPosition(int col) {
        int rowPosition = -1;
        for (int row=0; row<this.numRows; row++) {
            if (gameBoard[row][col] == EMPTY) {
                    rowPosition = row;
            }
        }
        return rowPosition;
    }//end getRowPosition

    public int[][] getGameBoard() {
        return this.gameBoard;
    }

//Print the board
    public void printBoard() {
        System.out.print("| ");
        for (int col = 1; col <= this.numCols; col++) {
            System.out.print(col + " | ");
        }
        System.out.println();
        System.out.println();
        for (int i=0; i<this.numRows; i++) {
            for (int j=0; j<this.numCols; j++) {
                    if (gameBoard[i][j] == State.X) {
                        System.out.print("| " + "X "); //Blue for user
                    } else if (gameBoard[i][j] == State.O) {
                        System.out.print("| " + "O "); //Red for computer
                    } else {
                        System.out.print("| " + "-" + " ");
                    }
            }
            System.out.println("|"); //End of each row
        }
    }//end printBoard
}//end State
