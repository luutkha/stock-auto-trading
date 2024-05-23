package trading.stock.stocktrading.facades.impl;

import org.springframework.stereotype.Component;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.facades.StockFacade;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;
import java.util.Objects;

@Component
public class StockFacadeImpl implements StockFacade {
    private final StockService stockService;

    public StockFacadeImpl(StockService stockService) {
        this.stockService = stockService;
    }

    public StockDetailResponseDTO getStockDetailByTime(String symbol, Long from, Long to) throws IOException {
        String stockDetailByCode;
        if (Objects.deepEquals(null, from) || Objects.deepEquals(null, to)) {
            stockDetailByCode = stockService.getStockDetailByCodeInCurrentTime(symbol).block();
        } else {
            stockDetailByCode = stockService.getStockDetailByCodeAndTime(symbol, from, to).block();
        }

        StockDetailDTO stockDetail = StockDetailDTO.fromJson(stockDetailByCode);
        return StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
    }
}
