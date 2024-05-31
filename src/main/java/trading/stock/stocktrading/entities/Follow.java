package trading.stock.stocktrading.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Follow {

    private String stockCode;
    private String quantity;

}
