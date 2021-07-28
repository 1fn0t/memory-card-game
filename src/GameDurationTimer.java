public class GameDurationTimer {
    private final long start;
    private double totalGameDuration;
    public GameDurationTimer() {
        start = System.nanoTime();
    }
    public void stop() {
        totalGameDuration = split();
    }
    public double split() {
        long current = System.nanoTime();
        return (current - start)/1000000000.0;
    }
    public double getTotal() {
        return totalGameDuration;
    }
}
