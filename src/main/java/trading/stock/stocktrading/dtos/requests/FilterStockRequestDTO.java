package trading.stock.stocktrading.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

        public Filter(String dbFilterCode, String condition, String value) {
            this.dbFilterCode = dbFilterCode;
            this.condition = condition;
            this.value = value;
        }
    }

}
