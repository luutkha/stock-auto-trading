package trading.stock.stocktrading.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import trading.stock.stocktrading.dtos.DefaultMovingAverageDTO;
import trading.stock.stocktrading.entities.DarvasBox;

import java.util.List;

@Data
@AllArgsConstructor
public class AnalysisStockDetailDTO {


    private List<DarvasBox> darvasBoxes;
    private DefaultMovingAverageDTO movingAverages;
    private StockDetailResponseDTO stock;
}
