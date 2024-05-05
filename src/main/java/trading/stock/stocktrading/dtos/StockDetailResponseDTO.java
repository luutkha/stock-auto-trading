package trading.stock.stocktrading.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailResponseDTO {

    private String status;
    private List<StockInfoDTO> infoByTimes;

    public static StockDetailResponseDTO  fromStockDetailDTO(StockDetailDTO stockDetailDTO) {
        List<StockInfoDTO> infoByTimes = new ArrayList<>();
        for (int i = 0; i < stockDetailDTO.getT().size(); i++) {
            StockInfoDTO stockInfo = StockInfoDTO.builder().price(stockDetailDTO.getC().get(i)).time(stockDetailDTO.getT().get(i)).volume(stockDetailDTO.getV().get(i)).build();
            infoByTimes.add(stockInfo);
        }
        return new StockDetailResponseDTO( stockDetailDTO.getS(), infoByTimes);

    }
}
