package trading.stock.stocktrading.services;

import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.requests.FilterStockRequestDTO;
import trading.stock.stocktrading.dtos.responses.FilterStockRawResponseDTO;

public interface StockService {
    Mono<String> getStockDetailByCodeInCurrentTime(String stockCode);

    Mono<String> getStockDetailByCodeAndTime(String stockCode, Long from, Long to);

    FilterStockRawResponseDTO filterStock(FilterStockRequestDTO req);
}
