package trading.stock.stocktrading.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.constants.VnDirectConstants;
import trading.stock.stocktrading.dtos.requests.FilterStockRequestDTO;
import trading.stock.stocktrading.dtos.responses.FilterStockRawResponseDTO;
import trading.stock.stocktrading.services.StockService;

import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class StockServiceImpl implements StockService {
    private final WebClient.Builder webClientBuilder;

    public StockServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public Mono<String> getStockDetailByCodeInCurrentTime(String stockCode) {

        long currentTime = System.currentTimeMillis() / 1000L;

        int MILI_OF_2_DAYS = 60 * 60 * 24 * 2;
        String url = String.format(VnDirectConstants.STOCK_DETAIL_URL, stockCode, currentTime - MILI_OF_2_DAYS, currentTime + 100);
        log.info("[URI] = " + url);

        return webClientBuilder.build().get().uri(url).retrieve().bodyToMono(String.class);
    }

    @Override
    public Mono<String> getStockDetailByCodeAndTime(String stockCode, Long from, Long to) {
        String url = String.format(VnDirectConstants.STOCK_DETAIL_URL, stockCode, from, to);
        log.info("[URI] = " + url);

        return webClientBuilder.build().get().uri(url).retrieve().bodyToMono(String.class);
    }

    @Override
    public CompletableFuture<FilterStockRawResponseDTO> filterStock(FilterStockRequestDTO req) {


        return webClientBuilder
                .build()
                .post()
                .uri(VnDirectConstants.STOCK_SEARCH_URL)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(FilterStockRawResponseDTO.class)
                .doOnSuccess(response -> log.info("[Received response] size = {}", response.getData().size()))
                .onErrorResume((e) -> {
                    log.error("Error occurred while filtering stock", e);
                    return Mono.empty();
                })
                .toFuture();
    }


}
