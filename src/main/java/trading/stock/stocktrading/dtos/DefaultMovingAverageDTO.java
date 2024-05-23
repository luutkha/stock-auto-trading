package trading.stock.stocktrading.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultMovingAverageDTO {

    private int[] defaultConfig;
    private double[] values;

}
