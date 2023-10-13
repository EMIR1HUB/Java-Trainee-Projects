package io.ylab.intensive.lesson02.StatsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int count = 0;
    private double summa = 0;

    @Override
    public void add(int value) {
        if(min > value){
            min = value;
        } else if (max < value) {
            max = value;
        }
        summa += value;
        count++;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if(count == 0){
            return 0.0;
        }
        return summa/count;
    }
}
