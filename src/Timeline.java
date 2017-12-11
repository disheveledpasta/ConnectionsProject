public class Timeline {

    long beginning;
    long end;

    public Timeline(long beginning, long end) {
        if (end > beginning) {
            this.beginning = beginning;
            this.end = end;
        }
    }
}
