package trading.stock.stocktrading.facades;

import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.responses.AnalysisStockDetailDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface StockFacade {
    StockDetailResponseDTO getStockDetailByTime(String symbol, Long from, Long to) throws IOException;

    Mono<StockDetailResponseDTO> getStockDetailByTimeAsync(String symbol, Long from, Long to) throws IOException;

    StockDetailResponseDTO convertRawStringToStockDetailDTO(String rawString);

    StockDetailResponseDTO convertRawStringToStockDetailDTO(CompletableFuture<String> futureString);
    AnalysisStockDetailDTO analysisStock(StockDetailResponseDTO stockDetail);
}
