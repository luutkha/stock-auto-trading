package trading.stock.stocktrading.entities;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.LongSummaryStatistics;

public class Stock {
    public static LongSummaryStatistics statisticVolume(long[] volumes) {
        return Arrays.stream(volumes).summaryStatistics();
    }

    public static DoubleSummaryStatistics statisticsPrice(double[] volumes) {
        return Arrays.stream(volumes).summaryStatistics();
    }

}
