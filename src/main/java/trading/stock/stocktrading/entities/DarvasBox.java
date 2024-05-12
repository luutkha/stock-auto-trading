package trading.stock.stocktrading.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DarvasBox {

    private Double lowPrice;
    private Double highPrice;

    /**
     *
     **/
    public static List<DarvasBox> analysisDarvasBoxByPrices(List<Double> prices) {
        List<DarvasBox> darvasBoxes = new ArrayList<>();
        double[] arrayOfPrices = prices.stream().mapToDouble(Double::doubleValue).toArray();
//        double maxPrice = Stock.statisticsPrice(arrayOfPrices).getMax();
//        double minPrice = Stock.statisticsPrice(arrayOfPrices).getMin();
        if (!prices.isEmpty()) {
            double lowPrice = prices.get(0);
            double highPrice = prices.get(0);
            for (Double price : prices) {
                // if current- price lower than  min-price, create a new box
                int TOLERANCE = 2;
                if (price < lowPrice) {
                    if (price < lowPrice - TOLERANCE) {
                        highPrice = lowPrice;
                        lowPrice = price;
                        darvasBoxes.add(new DarvasBox(lowPrice, highPrice));
                    } else {
                        lowPrice = price;
                    }
                }
                if (price > highPrice) {
                    if (price >= highPrice + TOLERANCE) {
                        lowPrice = highPrice;
                        highPrice = price;
                        darvasBoxes.add(new DarvasBox(lowPrice, highPrice));
                    } else {
                        highPrice = price;
                    }
                }
            }
        }

        return darvasBoxes;


    }
}
