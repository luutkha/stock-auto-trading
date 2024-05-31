package trading.stock.stocktrading.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterStockRequestDTO {
    private String fields;
    private List<Filter> filters;
    private String sort;

    @Data
    @NoArgsConstructor
    public static class Filter {
        private String dbFilterCode;
        private String condition;
        private String value; // For EQUAL condition
        private Long firstValue; // For BETWEEN condition
        private Long lastValue; // For BETWEEN condition
    }
}
