package io.ylab.intensive.lesson02.StatsAccumulator;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulator s = new StatsAccumulatorImpl();
        s.add(1);
        s.add(2);
        System.out.println("Среднее арифметическое = " + s.getAvg());
        s.add(0);
        System.out.println("Минимальное из переданных значений = " + s.getMin());
        s.add(3);
        s.add(8);
        System.out.println("Максимальные из переданных значений = " + s.getMax());
        System.out.println("Количество из переданных значений = " + s.getCount());
    }
}
