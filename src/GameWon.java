public class GameWon {
    private int pointsEarned;
    private int gameDuration;
    public GameWon(int gameDuration, int pointsEarned) {
        this.pointsEarned = pointsEarned;
        this.gameDuration = gameDuration;
    }
    //Game won just holds the matches data in the form of a string to be displayed as a JLabel
    public String getFinalScore() {
        return "Final Score: " + this.pointsEarned;
    }
    public String getTime() {
        return "Total Time: " + this.gameDuration;
    }
}
