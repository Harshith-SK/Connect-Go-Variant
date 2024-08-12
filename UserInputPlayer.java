import java.util.Scanner;

public class UserInputPlayer implements PlayerInterface {
    private int playerLetter;
    public UserInputPlayer(int player_letter) {
        playerLetter = player_letter;
    }

    public int getMoveColumn(State board) {
        int columnPosition;
        while (true) {
            try {
                do {
                    System.out.print("Select a column to drop your piece (1-" + board.numCols + "): ");
                    Scanner in = new Scanner(System.in);
                    columnPosition = in.nextInt();
                    if (board.checkFullColumn(columnPosition-1)) {
                        System.out.println("Column " + columnPosition + " is full. Pick a different one.");
                    }
                } while (board.checkFullColumn(columnPosition-1));
            } catch (Exception e){
                System.out.println("\nValid numbers are 1,2,3,4,5,6 or 7. Try again\n");
                continue;
            }
            return columnPosition-1;
        }
    }

    public int getPlayerID() {
        return playerLetter;
    }
}
