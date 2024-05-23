package trading.stock.stocktrading.facades.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.facades.StockFacade;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;
import java.util.Objects;

@Component
@Log4j2
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

    @Override
    public Mono<StockDetailResponseDTO> getStockDetailByTimeAsync(String symbol, Long from, Long to) {
        return stockService.getStockDetailByCodeAndTime(symbol, from, to)
                .map(detail -> {
                    StockDetailDTO stockDetail = StockDetailDTO.fromJson(detail);
                    return StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
                })
                .onErrorResume(e -> {
                    log.error("[IOException] {}", e.getMessage());
                    return Mono.error(new RuntimeException(e));
                });
    }

}
