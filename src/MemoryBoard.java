import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MemoryBoard {
    private static String[] arrP = {"fly", "bird", "dolphin", "mana", "panther", "sail", "bass", "gator", "fly",
            "bird", "dolphin", "mana", "panther", "sail", "bass", "gator"};
    //The Card array that holds all the cards
    private static Card[] board = new Card[16];
    public static MainFrame gui;
    //Extra Button
    private static JButton giveUp = new JButton();
    //Display labels
    private static JLabel attemptLabel = new JLabel("");
    private static JLabel finalScore = new JLabel("");
    private static JLabel finalTime = new JLabel("");
    private static GameDurationTimer stopWatch;
    private int currentI;
    private int nextI;
    private Timer gameTimer;
    private int clicks;
    private int points;
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
    //This is where the cards are initialized and where the picture paths are put together. The array element is
    //is also kept to be used as a description
    private void createCards() {
        for (int i = 0; i < board.length; i++) {
            String temp = "src/Images/" + arrP[i] + ".jpg";
            board[i] = new Card(temp, arrP[i]);
        }
    }
    //Shuffle is the first method that executes and it's purpose is to randomly swap the indexes of the array
    private void shuffle() {
        Random randomizer = new Random();
        for (int i = 0; i < arrP.length; i++) {
            int pick = i + randomizer.nextInt(arrP.length-i);
            String temp = arrP[pick];
            arrP[pick] = arrP[i];
            arrP[i] = temp;
        }
    }
    //The showCard method is in charge of setting each Icon to the front
    private void showCard(int n) {
        EventQueue.invokeLater(() -> {
            try {
                board[n].setIcon(board[n].getFront());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    //The hideCard method is in charge of setting each Icon to the back
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
        //This event queue is need tp make changes to the gui as it won't run without it
        EventQueue.invokeLater(() -> {
            try {
                gui = new MainFrame();
                //Here is where the cards are added to the board and are prepared for the start of the game
                for (Card card : board) {
                    gui.setupGame(card);
                    card.setIcon(card.getBack());
                    card.addActionListener(new ImageListener());
                    card.setBorder(BorderFactory.createEmptyBorder());
                    card.setBackground(Color.WHITE);
                }
                gui.extraSetup(giveUp);
                giveUp.addActionListener(new GiveUp());
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
            //After two different cards have been selected they need to be flipped back over so that the back of
            //of the card is now visible to the users
            hideCard(currentI);
            board[currentI].setEnabled(true);
            hideCard(nextI);
            board[nextI].setEnabled(true);
            //The timer is stopped as the cards have just recently been flipped so that the back of the card is
            //visible
            gameTimer.stop();
        }
    }
    private class ImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameTimer.isRunning()) return;
            //If the timer is still running there is no need for any action to be performed
            //Here clicks are added up and the stopWatch object is created
            if (clicks == 0) stopWatch = new GameDurationTimer();
            clicks++;
            //This loop is the one that is in charge of flipping the cards over so that the front is being displayed
            for (int i = 0; i < board.length; i++) {
                if (e.getSource() == board[i]) {
                    showCard(i);
                    currentI = i;
                }
            }
            if (clicks % 2 == 0) {
                if (currentI == nextI) {
                    //This increments every time the user clicks on the same card twice
                    duplicateClicks--;
                }
                // This uses the description I made when adding the Icons for the the card buttons
                if (board[currentI].getFront().getDescription().equals((board[nextI].getFront().getDescription())) &&
                board[currentI] != board[nextI]) {
                    //Here I disable the cards that have been matched so that the user is unable to try to flip
                    //them once again
                    board[currentI].setEnabled(false);
                    board[nextI].setEnabled(false);
                    //Changing the background color is a indicator that the user got the two pairs
                    board[currentI].setBackground(Color.GREEN);
                    board[nextI].setBackground(Color.GREEN);
                    //Accumulator for the points system
                    points += 100;
                } else {
                    //Once the incorrect cards have been flipped the Timer prepares itself for the next user
                    //attempt
                    gameTimer.start();
                }
            } else {
                //This is used when only one card is flipped nextI has to acquire currentI's value so that
                //It can have a value to match for the next flip
                nextI = currentI;
            }
            //This is the number that displays as the number of attempts taken by the user
            attemptLabel.setText(((clicks + duplicateClicks)/2) + "");
            //Once eight matches(100 points for every match) are made ths game is over
            if (points == 800) {
                endGame();
            }
        }
    }
    private static class GiveUp implements ActionListener {
        //GiveUp purpose is if the use no longer wants to continue playing and just wants to know were all the
        //cards were located during the game
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Card card : board) {
                if (card.isEnabled()) {
                    card.setBackground(Color.RED);
                    //This disables all the cards that the user was not able to match
                    card.setEnabled(false);
                    //This allows the user to be able to view the icon of the unmatched cards
                    card.setDisabledIcon(card.getFront());

                }
            }
        }
    }
    private void endGame() {
        //Once the game in done the stopwatch doesn't need to continue running in the background as the stopwatch's
        //goal is completed
        stopWatch.stop();
        //I turn the time I got with the stopwatch into an integer as the extra precision is unnecessary
        int totalTime = (int) (stopWatch.getTotal());
        //The Score class is then used to create a object to calculate the score of the game
        Score gameScore = new Score(totalTime, points);
        //GameWon turns the data gathered from the stopwatch and the calculation of actual points from the score
        //class into Strings
        GameWon won = new GameWon(totalTime, gameScore.getScore());
        //Final Score and Time are not set to be displayed onto the screen
        finalScore.setText(won.getFinalScore());
        finalTime.setText(won.getTime());
    }
}
