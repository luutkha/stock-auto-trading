package trading.stock.stocktrading.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailDTO {
    /**
     * time in timestamp
     **/
    private List<Long> t;
    /**
     * price  ATC
     **/
    private List<Double> c;
    /**
     * price  ATO
     **/
    private List<Double> o;
    /**
     * high price
     **/
    private List<Double> h;
    /**
     * low price
     **/
    private List<Double> l;
    /**
     * volume of stock
     **/
    private List<Long> v;
    /**
     * status
     **/
    private String s;

    public static StockDetailDTO fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, StockDetailDTO.class);
    }

}
