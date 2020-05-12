package com.weatherstation.help;

import java.util.ArrayList;

public class StatisticsOperations {


    public double computeStandardDeviation(ArrayList<Double> arrayList) {

        double result = 0;
        double deviation = 0;
        double mean = computeMean(arrayList);


        for (Double value : arrayList) {
            deviation += Math.pow(value - mean, 2);
        }
        result = Math.sqrt(deviation / (arrayList.size() - 1));
        result *= 100000;
        result = Math.round(result);
        return result / 100000;

    }

    public double computeMean(ArrayList<Double> arrayList) {

        double sum = arrayList.stream()
                .mapToDouble(a -> a)
                .sum();
        return sum / arrayList.size();
    }

    public double minValue(ArrayList<Double> arrayList) {
        return arrayList.stream()
                .mapToDouble(a -> a).min().getAsDouble();
    }
    public double maxValue(ArrayList<Double> arrayList) {
        return arrayList.stream()
                .mapToDouble(a -> a).max().getAsDouble();
    }
}
