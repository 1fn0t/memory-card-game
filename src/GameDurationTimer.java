public class GameDurationTimer {
    private final long start;
    private double totalGameDuration;
    //Game timer uses System.nanoTime to keep track of the amount of time the user takes through out the match
    //from the moment the first card is flipped
    public GameDurationTimer() {
        start = System.nanoTime();
    }
    public void stop() {
        totalGameDuration = split();
    }
    //The purpose of split is to give us the time in seconds + decimals
    public double split() {
        long current = System.nanoTime();
        return (current - start)/1000000000.0;
    }
    public double getTotal() {
        return totalGameDuration;
    }
}
