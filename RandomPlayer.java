import java.util.Random;

public class RandomPlayer implements PlayerInterface {
    private int playerLetter;
    public RandomPlayer(int player_letter) {
        playerLetter = player_letter;
    }

    public int getMoveColumn(State board) {
        Random r = new Random();
        int move;
        do {
            move = r.nextInt(board.numCols);
        } while(board.checkFullColumn(move));
        return move;
    }

    public int getPlayerID() {
        return playerLetter;
    }
}
