public class Score {
    private int time;
    private int points;
    Score (int time, int points) {
        this.time = time;
        this.points = points;
    }
    public int getScore() {
        int total = this.points;
        while ((this.time-10) > 0) {
            total -= 40;
            this.time -= 10;
        }
        return total;
    }
}
