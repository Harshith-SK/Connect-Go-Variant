public class StateServerInternal extends State {
    public StateServerInternal(int num_rows, int num_cols, int num_needed_to_win) {
        super(num_rows, num_cols, num_needed_to_win);
    }//end Constructor

    public StateServerInternal setWinner(int winner) {
        this.winner = winner;
        return this;
    }//end setWinner

    public StateServerInternal setWinnerMethod(String winningMethod) {
        this.winningMethod = winningMethod;
        return this;
    }//end setWinnerMethod

    //Make a movement based on a column and a player
    public StateServerInternal makeMove(int col, int player) throws Exception {
        if (col < 0 || col >= this.numCols) {
            throw new Exception("Column out of range");
        }
        if (this.checkFullColumn(col)) {
            throw new Exception("Column full");
        }
        gameBoard[getRowPosition(col)][col] = player;
        lastPlayerPlayed = player;
        return this;
    }//end makeMove

    public boolean checkWinState() {
        if (this.winner != 0) {
            return true;
        }
        boolean all_same;
        //Case if we have row
        for (int i=0; i < this.numRows; i++) {
            for (int j=0; j + this.numNeededToWin <= this.numCols; j++) {
                if (gameBoard[i][j] == EMPTY) {
                    continue;
                }
                all_same = true;
                for (int k=j+1; k < j + this.numNeededToWin; k++) {
                    if (gameBoard[i][k] != gameBoard[i][j]) {
                        all_same = false;
                        break;
                    }
                }
                if (all_same) {
                    setWinner(gameBoard[i][j]);
                    setWinnerMethod("Winner by row: starting horizontally at " + (i + 1) + ", " + (j + 1));
                    return true;
                }
            }
        }

        //Case we have a column
        for (int i=0; i + this.numNeededToWin <= this.numRows; i++) {
            for (int j=0; j<this.numCols; j++) {
                if (gameBoard[i][j] == EMPTY) {
                    continue;
                }
                all_same = true;
                for (int k=i+1; k < i + this.numNeededToWin; k++) {
                    if (gameBoard[k][j] != gameBoard[i][j]) {
                        all_same = false;
                        break;
                    }
                }
                if (all_same) {
                    setWinner(gameBoard[i][j]);
                    setWinnerMethod("Winner by column: starting vertically at " + (i + 1) + ", " + (j + 1));
                    return true;
                }
            }
        }

        //Case we have an ascendent diagonal
        for (int i=0; i + this.numNeededToWin <= this.numRows; i++) {
            for (int j=0; j + this.numNeededToWin <= this.numCols; j++) {
                if (gameBoard[i][j] == EMPTY) {
                    continue;
                }
                all_same = true;
                for (int k = 1; k < this.numNeededToWin; k++) {
                    if (gameBoard[i + k][j + k] != gameBoard[i][j]) {
                        all_same = false;
                        break;
                    }
                }
                if (all_same) {
                    setWinner(gameBoard[i][j]);
                    setWinnerMethod("Winner by diagonal.");
                    return true;
                }
            }
        }

        //Case we have an descendent 4-diagonal
        for (int i=0; i<this.numRows; i++) {
            for (int j=0; j<this.numCols; j++) {
                if (canMove(i - this.numNeededToWin + 1, j + this.numNeededToWin - 1)) {
                    if (gameBoard[i][j] == EMPTY) {
                        continue;
                    }
                    all_same = true;
                    for (int k = 1; k < this.numNeededToWin; k++) {
                        if (gameBoard[i - k][j + k] != gameBoard[i][j]) {
                            all_same = false;
                            break;
                        }
                    }
                    if (all_same) {
                        setWinner(gameBoard[i][j]);
                        setWinnerMethod("Winner by diagonal.");
                        return true;
                    }
                }
            }
        }
        //There is no winner yet :(
        setWinner(0);
        return false;
    }//end checkWinState

    public boolean checkGameOver() {
        //If there is a winner, we need to know it
        if (checkWinState()) {
            return true;
        }
        //Are there blank spaces in the board?
        for(int row=0; row<6; row++) {
            for(int col=0; col<7; col++) {
                if(gameBoard[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }//end checkGameOver

    public State getPlayerPublicState() {
        State state = new State(this.numRows, this.numCols, this.numNeededToWin);
        state.lastPlayerPlayed = this.lastPlayerPlayed;
        state.winner = this.winner;
        state.winningMethod = this.winningMethod;
        state.gameBoard = new int[this.numRows][this.numCols];
        for(int row=0; row<this.numRows; row++) {
            for(int col=0; col<this.numCols; col++) {
                state.gameBoard[row][col] = this.gameBoard[row][col];
            }
        }
        return state;
    }

    public int getLastPlayerPlayed() {
        return this.lastPlayerPlayed;
    }

    public int getWinner() {
        return this.winner;
    }

    public String getWinningMethod() {
        return this.winningMethod;
    }
}
