package trading.stock.stocktrading.services;

import reactor.core.publisher.Mono;

public interface StockService {
    Mono<String> getStockDetailByCodeInCurrentTime(String stockCode);

    Mono<String> getStockDetailByCodeAndTime(String stockCode, Long from, Long to);
}
