package trading.stock.stocktrading.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInfoDTO {
    private Double price;
    private Long time;
    private Long volume;
}
