package trading.stock.stocktrading.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import trading.stock.stocktrading.utils.Number;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Data
@AllArgsConstructor
@Log4j2
public class DarvasBox {

    private Double lowPrice;
    private Double highPrice;
    private int count;

    /**
     * @param prices list prices of stock, sort by time asc
     **/

    public static List<DarvasBox> analysisDarvasBoxByPrices(List<Double> prices) {
        List<DarvasBox> darvasBoxes = new ArrayList<>();
        // default gap is 2% of price. moving to props later
        double GAP_OF_PRICE = prices.get(0) / 100 * 2;
        int GAP_OF_TIME = 10;
        double[] prices_array;
        DoubleSummaryStatistics statisticsOf10FirstPrices;
        if (prices.size() < GAP_OF_TIME) return darvasBoxes;
        do {
            prices_array = Number.convertListToArray(prices.subList(0, GAP_OF_TIME));
            statisticsOf10FirstPrices = Stock.statisticsPrice(prices_array);
            if (prices.size() > GAP_OF_TIME + 1) {
                GAP_OF_TIME += 1;
            } else {
                return darvasBoxes;
            }
        } while (statisticsOf10FirstPrices.getMax() - statisticsOf10FirstPrices.getMin() <= GAP_OF_PRICE);

        DarvasBox baseOfDarvasBox = new DarvasBox(statisticsOf10FirstPrices.getMin(), statisticsOf10FirstPrices.getMax(), GAP_OF_TIME - 1);
        Result initialBase = new Result(GAP_OF_TIME, baseOfDarvasBox);

        if (initialBase.baseOfDarvasBox().getHighPrice() > 0) {
            darvasBoxes.add(initialBase.baseOfDarvasBox());
        }

        do {
            if (!prices.isEmpty()) {
                double lowReferencePrice = initialBase.baseOfDarvasBox().getLowPrice();
                double highReferencePrice = initialBase.baseOfDarvasBox().getHighPrice();

                int count = 0;
                for (int i = initialBase.GAP_OF_TIME() - 1; i < prices.size(); i++) {
                    Double price = prices.get(i);
                    GAP_OF_PRICE = price / 100 * 2;
                    count++;
                    if (!(price <= highReferencePrice) || !(price >= lowReferencePrice)) {
                        if (price > (highReferencePrice + GAP_OF_PRICE)) {
                            if ((count > 10 || i == prices.size() - 1)) {
                                log.info("/ price / lowRef / highRef / =  {} / {} / {}", price, lowReferencePrice, highReferencePrice);
                                darvasBoxes.add(new DarvasBox(highReferencePrice, price, count));

                                lowReferencePrice = highReferencePrice;
                                highReferencePrice = price;

                                count = 0;
                            }
                        }

                        if (price < (lowReferencePrice - GAP_OF_PRICE)) {

                            if ((count > 10 || i == prices.size() - 1)) {
                                log.info("/ price / lowRef / highRef / =  {} / {} / {}", price, lowReferencePrice, highReferencePrice);
                                darvasBoxes.add(new DarvasBox(price, lowReferencePrice, count));
                                highReferencePrice = lowReferencePrice;
                                lowReferencePrice = price;
                                count = 0;
                            }
                        }

                    }

                }

            }
        } while (darvasBoxes.isEmpty());

        return darvasBoxes;

    }

    private record Result(int GAP_OF_TIME, DarvasBox baseOfDarvasBox) {
    }

}
