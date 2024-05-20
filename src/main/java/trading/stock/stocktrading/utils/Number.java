package trading.stock.stocktrading.utils;

import java.util.List;

public class Number {
    public static double[] convertListToArray(List<Double> list) {
        return list.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }
}
