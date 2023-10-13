package io.ylab.intensive.lesson02.RateLimitedPrinter;

public class RateLimitedPrinterImpl implements RateLimitedPrinter {
    private final int interval;
    private long currentTime;
    public RateLimitedPrinterImpl(int interval) {
        this.interval = interval;
        this.currentTime = System.currentTimeMillis();
    }

    public void print(String message) {
        long subsequentTime = System.currentTimeMillis();
        if(subsequentTime - currentTime > interval){
            System.out.println(message);
            currentTime = subsequentTime;
        }
    }
}
