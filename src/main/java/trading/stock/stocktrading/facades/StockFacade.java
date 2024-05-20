package trading.stock.stocktrading.facades;

import trading.stock.stocktrading.dtos.StockDetailResponseDTO;

import java.io.IOException;

public interface StockFacade {
     StockDetailResponseDTO getStockDetailByTime(String symbol, Long from, Long to) throws IOException;
}