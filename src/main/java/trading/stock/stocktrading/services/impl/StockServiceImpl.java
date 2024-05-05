package trading.stock.stocktrading.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.services.StockService;

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

        String url = String.format("https://dchart-api.vndirect.com.vn/dchart/history?symbol=%s&resolution=D&from=%d&to=%d",
                stockCode, currentTime - 60 * 60 * 24 * 2, currentTime + 100);
        log.info("[URI] = " + url);
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }
}
