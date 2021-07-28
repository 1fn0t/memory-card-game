import java.awt.*;

public class MemoryGame {
    private static MemoryBoard gameBoard;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                gameBoard = new MemoryBoard();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
