package trading.stock.stocktrading.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInfoDTO {
    private Double price;
    private Long time;
    private Long volume;
    private LocalDate date;
}
