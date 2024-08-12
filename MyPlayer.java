//Group40(CSE0598)
import java.util.Random;

public class MyPlayer implements PlayerInterface {
	 private int playerLetter;

	    public MyPlayer(int playerLetter) {
	        this.playerLetter = playerLetter;
	    }

	    @Override
	    public int getMoveColumn(State board) {
	        int depth = 5; // Adjust the depth of the search tree as needed
	        return minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true)[1];
	    }

	    @Override
	    public int getPlayerID() {
	        return playerLetter;
	    }

	    private int[] minimax(State board, int depth, int alpha, int beta, boolean maximizingPlayer) {
	        int bestColumn = -1;
	        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

	        // Base case: If depth is 0 or the game is over, evaluate the board state
	        if (depth == 0 || isGameOver(board)) {
	            int score = evaluate(board);
	            return new int[]{score, -1}; // -1 indicates no move column (not relevant at this level)
	        }

	        // Generate possible moves
	        for (int col = 0; col < board.numCols; col++) {
	            if (!board.checkFullColumn(col)) {
	                // Make a move
	                try {
	                    State newBoard = makeMove(board, col, maximizingPlayer ? State.X : State.O);
	                    int[] result = minimax(newBoard, depth - 1, alpha, beta, !maximizingPlayer);

	                    // Update alpha and beta
	                    if (maximizingPlayer) {
	                        if (result[0] > bestScore) {
	                            bestScore = result[0];
	                            bestColumn = col;
	                        }
	                        alpha = Math.max(alpha, bestScore);
	                    } else {
	                        if (result[0] < bestScore) {
	                            bestScore = result[0];
	                            bestColumn = col;
	                        }
	                        beta = Math.min(beta, bestScore);
	                    }

	                    // Alpha-beta pruning
	                    if (beta <= alpha) {
	                        break;
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        return new int[]{bestScore, bestColumn};
	    }

	    // Evaluation function to assign scores to different game states
	    private int evaluate(State board) {
	        // For simplicity, let's return a random score between 0 and 100
	        Random random = new Random();
	        return random.nextInt(101); // Return a random score between 0 and 100
	    }

	    // Method to check if the game is over
	    private boolean isGameOver(State board) {
	        // Check for a winner or if the board is full
	        return checkWinState(board) || checkFullBoard(board);
	    }
	 // Method to check if there is a winner
	    private boolean checkWinState(State board) {
	        int[][] gameBoard = board.getGameBoard();
	        int numRows = board.numRows;
	        int numCols = board.numCols;
	        int numNeededToWin = board.numNeededToWin;

	        // Check rows for a win
	        for (int row = 0; row < numRows; row++) {
	            for (int col = 0; col <= numCols - numNeededToWin; col++) {
	                int player = gameBoard[row][col];
	                if (player != State.EMPTY) {
	                    boolean win = true;
	                    for (int k = 1; k < numNeededToWin; k++) {
	                        if (gameBoard[row][col + k] != player) {
	                            win = false;
	                            break;
	                        }
	                    }
	                    if (win) return true;
	                }
	            }
	        }

	        // Check columns for a win
	        for (int col = 0; col < numCols; col++) {
	            for (int row = 0; row <= numRows - numNeededToWin; row++) {
	                int player = gameBoard[row][col];
	                if (player != State.EMPTY) {
	                    boolean win = true;
	                    for (int k = 1; k < numNeededToWin; k++) {
	                        if (gameBoard[row + k][col] != player) {
	                            win = false;
	                            break;
	                        }
	                    }
	                    if (win) return true;
	                }
	            }
	        }

	        // Check diagonals (top-left to bottom-right) for a win
	        for (int row = 0; row <= numRows - numNeededToWin; row++) {
	            for (int col = 0; col <= numCols - numNeededToWin; col++) {
	                int player = gameBoard[row][col];
	                if (player != State.EMPTY) {
	                    boolean win = true;
	                    for (int k = 1; k < numNeededToWin; k++) {
	                        if (gameBoard[row + k][col + k] != player) {
	                            win = false;
	                            break;
	                        }
	                    }
	                    if (win) return true;
	                }
	            }
	        }

	        // Check diagonals (top-right to bottom-left) for a win
	        for (int row = 0; row <= numRows - numNeededToWin; row++) {
	            for (int col = numCols - 1; col >= numNeededToWin - 1; col--) {
	                int player = gameBoard[row][col];
	                if (player != State.EMPTY) {
	                    boolean win = true;
	                    for (int k = 1; k < numNeededToWin; k++) {
	                        if (gameBoard[row + k][col - k] != player) {
	                            win = false;
	                            break;
	                        }
	                    }
	                    if (win) return true;
	                }
	            }
	        }

	        return false;
	    }

	    // Method to check if the board is full
	    private boolean checkFullBoard(State board) {
	        int[][] gameBoard = board.getGameBoard();
	        for (int row = 0; row < board.numRows; row++) {
	            for (int col = 0; col < board.numCols; col++) {
	                if (gameBoard[row][col] == State.EMPTY) {
	                    return false; // If there's an empty space, the board is not full
	                }
	            }
	        }
	        return true; // If no empty space is found, the board is full
	    }

	    
	    // Method to make a move on the board
	    private State makeMove(State board, int col, int player) throws Exception {
	        if (col < 0 || col >= board.numCols) {
	            throw new Exception("Column out of range");
	        }
	        if (board.checkFullColumn(col)) {
	            throw new Exception("Column full");
	        }
	        State newBoard = new StateServerInternal(board.numRows, board.numCols, board.numNeededToWin);
	        for (int i = 0; i < board.numRows; i++) {
	            for (int j = 0; j < board.numCols; j++) {
	                newBoard.gameBoard[i][j] = board.gameBoard[i][j];
	            }
	        }
	        newBoard.gameBoard[newBoard.getRowPosition(col)][col] = player;
	        newBoard.lastPlayerPlayed = player;
	        return newBoard;
	    }
}
