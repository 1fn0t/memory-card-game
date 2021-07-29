import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel scorePanel;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel extraPanel;
    private JLabel attempts;
    private JPanel lastPanel;

    public MainFrame() {
        setTitle("Memory Game");
        setContentPane(mainPanel);
        mainPanel.setBackground(Color.white);
        gamePanel.setBackground(Color.white);
        extraPanel.setBackground(Color.white);
        scorePanel.setBackground(Color.white);
        lastPanel.setBackground(Color.white);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        attempts.setAlignmentX(LEFT_ALIGNMENT);
        gamePanel.setSize(500, 500);
        gamePanel.setLayout(new BorderLayout(0,0));
        //For this Card game a gridlayout is used to organize the cards on the board
        gamePanel.setLayout(new GridLayout(4,4, 5, 5));
        extraPanel.setLayout(new GridLayout(3,1, 10, 10));
        scorePanel.setLayout(new GridLayout(4,1, 10, 10));
        lastPanel.setLayout(new GridLayout(2,1, 10, 10));
    }
    //These are the methods used to add gui components from the MemoryBoard class
    public void setupGame(JButton button) {
        gamePanel.add(button);
    }
    public void extraSetup(JButton button) {
        extraPanel.add(button);
    }
    public void addText(JLabel label) {
        scorePanel.add(label);
    }
    public void addText2(JLabel label) {
        lastPanel.add(label);
    }

}