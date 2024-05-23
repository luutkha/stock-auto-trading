package trading.stock.stocktrading.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trading.stock.stocktrading.dtos.DefaultMovingAverageDTO;
import trading.stock.stocktrading.exceptions.InvalidInputException;
import trading.stock.stocktrading.utils.Number;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String name;
    private String symbol;

    public static LongSummaryStatistics statisticVolume(long[] volumes) {
        return Arrays.stream(volumes).summaryStatistics();
    }

    public static DoubleSummaryStatistics statisticsPrice(double[] prices) {
        return Arrays.stream(prices).summaryStatistics();
    }

    public static double calculateMovingAverage(List<Double> prices, int size) {
        if (prices.size() < size) {
            throw new InvalidInputException("[size] is invalid");
        }
        List<Double> movingAverage = prices.subList(prices.size() - size, prices.size() - 1);
        return Arrays.stream(Number.convertListToArray(movingAverage)).summaryStatistics().getAverage();
    }

    public static DefaultMovingAverageDTO calculateDefaultMovingAverage(List<Double> prices) {
        DefaultMovingAverageDTO defaultMovingAverageDTO = new DefaultMovingAverageDTO();
        int[] maSize = {20, 50, 200};
        double[] values = {0,0,0};
        int index = -1;
        for (int size : maSize) {
            index++;
            if (prices.size() >= size) {
                List<Double> movingAverage = prices.subList(prices.size() - size, prices.size() - 1);
                double maValue = Arrays.stream(Number.convertListToArray(movingAverage)).summaryStatistics().getAverage();
                values[index] = maValue;
            }
            defaultMovingAverageDTO.setDefaultConfig(maSize);
            defaultMovingAverageDTO.setValues(values);
        }

        return defaultMovingAverageDTO;
    }
}
