public class Main {

    static final int ROWS = 8;
    static final int COLS = 9;
    static final int MIN_NEEDED_TO_WIN = 5;

    static int columnPosition;
    static StateServerInternal theBoard;
    static RandomPlayer playerO;
    static MyPlayer playerX;
    ////////////////////

    private static String getPlayerLabel(int playerID) {
        return (playerID == State.X ? "X" : "O");
    }

    public static void main(String[] args) throws Exception {
        playerO = new RandomPlayer(State.O);
        playerX = new MyPlayer(State.X);
        theBoard = new StateServerInternal(Main.ROWS, Main.COLS, Main.MIN_NEEDED_TO_WIN);
        System.out.println("Connect Go in Java!\n");
        theBoard.printBoard();
        //While the game has not finished
        while(!theBoard.checkGameOver()) {
            PlayerInterface current_player;
            System.out.println();
            switch (theBoard.getLastPlayerPlayed()) {
            //If O played last, then X plays now (blue color)
                case State.O:
                    //Movement of the user
                    current_player = playerX;
                    break;
            //If X played last, then O plays now (red color)
                case State.X:
                    current_player = playerO;
                    break;
                default:
                    throw new Exception("Unreachable");
            }
            int move = current_player.getMoveColumn(theBoard.getPlayerPublicState());
            try {
                theBoard.makeMove(move, current_player.getPlayerID());
                System.out.println("Player " + getPlayerLabel(current_player.getPlayerID()) + " moves on column " + (move + 1)+".");
                System.out.println();
                theBoard.printBoard();
            } catch (Exception e) {
                System.out.println("Player " + getPlayerLabel(current_player.getPlayerID()) + " can't play there. Player forfeits game.");
                theBoard.setWinner(theBoard.getLastPlayerPlayed())
                    .setWinnerMethod("Forfeit by Exception: " + e.getMessage());
            }
        }
        //The game has finished because...
        System.out.println();
        if (theBoard.getWinner() != 0) {
            System.out.println("Player " + getPlayerLabel(theBoard.getWinner()) + " wins!");
            System.out.println(theBoard.getWinningMethod());
        } else {
            System.out.println("It's a draw!");
        }
        System.out.println("Game over.");
    }// end main method
}//end Main class
