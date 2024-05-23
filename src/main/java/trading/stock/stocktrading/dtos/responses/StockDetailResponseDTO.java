package trading.stock.stocktrading.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.StockInfoDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDetailResponseDTO {

    private String status;
    private List<StockInfoDTO> infoByTimes;
    private List<Double> prices;

    public static StockDetailResponseDTO fromStockDetailDTO(StockDetailDTO stockDetailDTO) {
        List<StockInfoDTO> infoByTimes = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        for (int i = 0; i < stockDetailDTO.getT().size(); i++) {
            Double price = stockDetailDTO.getC().get(i);

            Long time = stockDetailDTO.getT().get(i);
            LocalDate date = Instant.ofEpochMilli(time * 1000).atZone(ZoneId.systemDefault()).toLocalDate();
            StockInfoDTO stockInfo = StockInfoDTO.builder()
                    .price(price)
                    .time(time)
                    .volume(stockDetailDTO.getV().get(i))
                    .date(date)
                    .build();
            infoByTimes.add(stockInfo);
            prices.add(price);
        }
        return new StockDetailResponseDTO(stockDetailDTO.getS(), infoByTimes, prices);

    }

    public static boolean compareInfoByTimes(List<StockInfoDTO> infoByTimes1, List<StockInfoDTO> infoByTimes2) {
        if (infoByTimes1.size() != infoByTimes2.size()) return false;
        for (int i = 0; i < infoByTimes1.size(); i++) {
            StockInfoDTO stockInfo2 = infoByTimes2.get(i);
            StockInfoDTO stockInfo1 = infoByTimes1.get(i);
            boolean equalConditions = stockInfo1.getTime().equals(stockInfo2.getTime()) && stockInfo1.getPrice().equals(stockInfo2.getPrice()) && stockInfo1.getVolume().equals(stockInfo2.getVolume());
            if (!equalConditions) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDetailResponseDTO secondObject = (StockDetailResponseDTO) o;
        return status.equals(secondObject.status) && compareInfoByTimes(infoByTimes, secondObject.infoByTimes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, infoByTimes);
    }
}
