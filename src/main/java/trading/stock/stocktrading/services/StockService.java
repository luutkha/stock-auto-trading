package trading.stock.stocktrading.services;

import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.requests.FilterStockRequestDTO;
import trading.stock.stocktrading.dtos.responses.FilterStockRawResponseDTO;

import java.util.concurrent.CompletableFuture;

public interface StockService {
    Mono<String> getStockDetailByCodeInCurrentTime(String stockCode);

    Mono<String> getStockDetailByCodeAndTime(String stockCode, Long from, Long to);

    CompletableFuture<FilterStockRawResponseDTO> filterStock(FilterStockRequestDTO req);
}
