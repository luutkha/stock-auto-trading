package trading.stock.stocktrading.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trading.stock.stocktrading.dtos.CompanyReportDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterStockRawResponseDTO {
    private List<CompanyReportDTO> data;
    private int size;
}
