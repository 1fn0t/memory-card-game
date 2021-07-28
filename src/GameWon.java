public class GameWon {
    private int pointsEarned;
    private int gameDuration;
    public GameWon(int gameDuration, int pointsEarned) {
        this.pointsEarned = pointsEarned;
        this.gameDuration = gameDuration;
    }
    public String getFinalScore() {
        return "Final Score: " + this.pointsEarned;
    }
    public String getTime() {
        return "Total Time: " + this.gameDuration;
    }
}
