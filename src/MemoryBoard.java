import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MemoryBoard {
    private static String[] arrP = {"fly", "bird", "dolphin", "mana", "panther", "sail", "bass", "gator", "fly",
            "bird", "dolphin", "mana", "panther", "sail", "bass", "gator"};
    private static Card[] board = new Card[16];
    public static MainFrame gui;
    private static JButton giveUp = new JButton();
    private static JLabel attemptLabel = new JLabel("");
    private static JLabel finalScore = new JLabel("");
    private static JLabel finalTime = new JLabel("");
    private static GameDurationTimer stopWatch;
    private int currentI;
    private int nextI;
    private Timer gameTimer;
    private int clicks;
    private int points;
    private boolean finished = false;
    private int duplicateClicks;
    public MemoryBoard() {
        shuffle();
        createCards();
        setGui();
        EventQueue.invokeLater(() -> {
            try {
                gameTimer = new Timer(850, new TimeListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void createCards() {
        for (int i = 0; i < board.length; i++) {
            String temp = "src/Images/" + arrP[i] + ".jpg";
            board[i] = new Card(temp, arrP[i]);
        }
    }
    private void shuffle() {
        Random randomizer = new Random();
        for (int i = 0; i < arrP.length; i++) {
            int pick = i + randomizer.nextInt(arrP.length-i);
            String temp = arrP[pick];
            arrP[pick] = arrP[i];
            arrP[i] = temp;
        }
    }
    private void showCard(int n) {
        EventQueue.invokeLater(() -> {
            try {
                board[n].setIcon(board[n].getFront());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void hideCard(int n) {
        EventQueue.invokeLater(() -> {
            try {
                board[n].setIcon(board[n].getBack());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void setGui() {
        EventQueue.invokeLater(() -> {
            try {
                gui = new MainFrame();
                for (Card card : board) {
                    gui.setupGame(card);
                    card.setIcon(card.getBack());
                    card.addActionListener(new ImageListener());
                    card.setBorder(BorderFactory.createEmptyBorder());
                    card.setBackground(Color.WHITE);
                }
                gui.extraSetup(giveUp);
                giveUp.addActionListener(new GiveUpListener());
                giveUp.setText("Give Up");
                giveUp.setBackground(Color.red);
                attemptLabel.setMinimumSize(new Dimension(120,-1));
                gui.addText(attemptLabel);
                finalScore.setMinimumSize(new Dimension(120,-1));
                finalTime.setMinimumSize(new Dimension(120,-1));
                gui.addText2(finalScore);
                gui.addText2(finalTime);
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private class TimeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideCard(currentI);
            board[currentI].setEnabled(true);
            hideCard(nextI);
            board[nextI].setEnabled(true);
            gameTimer.stop();
        }
    }
    private class ImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameTimer.isRunning()) return;
            if (clicks == 0) stopWatch = new GameDurationTimer();
            clicks++;

            for (int i = 0; i < board.length; i++) {
                if (e.getSource() == board[i]) {
                    showCard(i);
                    currentI = i;
                }
            }

            if (clicks % 2 == 0) {
                if (currentI == nextI) {
                    duplicateClicks--;
                }
                if (board[currentI].getFront().getDescription().equals((board[nextI].getFront().getDescription())) &&
                board[currentI] != board[nextI]) {
                    board[currentI].setEnabled(false);
                    board[nextI].setEnabled(false);
                    board[currentI].setBackground(Color.GREEN);
                    board[nextI].setBackground(Color.GREEN);
                    points += 100;
                } else {
                    gameTimer.start();
                }
            } else {
                nextI = currentI;
            }
            attemptLabel.setText(((clicks + duplicateClicks)/2) + "");
            if (points == 800) {
                endGame();
            }
        }
    }
    private static class GiveUpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Card card : board) {
                if (card.isEnabled()) {
                    card.setBackground(Color.RED);
                    card.setEnabled(false);
                    card.setDisabledIcon(card.getFront());
                }
            }
        }
    }
    private void setDone() {
        this.finished = true;
    }
    public boolean isDone() {
        return finished;
    }
    private void endGame() {
        setDone();
        stopWatch.stop();
        int totalTime = (int) (stopWatch.getTotal());
        Score gameScore = new Score(totalTime, points);
        GameWon won = new GameWon(totalTime, gameScore.getScore());
        finalScore.setText(won.getFinalScore());
        finalTime.setText(won.getTime());
    }
}
