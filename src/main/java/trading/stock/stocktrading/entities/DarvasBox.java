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
//        double[] arrayOfPrices = prices.stream().mapToDouble(Double::doubleValue).toArray();

        if (!prices.isEmpty()) {
            double lowPrice = prices.get(0);
            double highPrice = prices.get(0);
            for (Double price : prices) {

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
