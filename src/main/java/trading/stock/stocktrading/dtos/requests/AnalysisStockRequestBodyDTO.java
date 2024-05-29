package trading.stock.stocktrading.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AnalysisStockRequestBodyDTO {
    private Set<String> symbols;
    private Long from;
    private Long to;
}
