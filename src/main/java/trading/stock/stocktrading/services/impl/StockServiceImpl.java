package trading.stock.stocktrading.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.services.StockService;

@Service
@Log4j2
public class StockServiceImpl implements StockService {
    public static final String URL = "https://dchart-api.vndirect.com.vn/dchart/history?symbol=%s&resolution=D&from=%d&to=%d";
    private final WebClient.Builder webClientBuilder;

    public StockServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }


    @Override
    public Mono<String> getStockDetailByCodeInCurrentTime(String stockCode) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("getStockDetailByCodeInCurrentTime");

        long currentTime = System.currentTimeMillis() / 1000L;

        int MILI_OF_2_DAYS = 60 * 60 * 24 * 2;
        String url = String.format(URL,
                stockCode, currentTime - MILI_OF_2_DAYS, currentTime + 100);
        log.info("[URI] = " + url);

        Mono<String> result = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        stopWatch.stop();
        log.info("[TIME - getStockDetailByCodeInCurrentTime] = " + stopWatch.getTotalTimeSeconds());
        return result;
    }

    @Override
    public Mono<String> getStockDetailByCodeAndTime(String stockCode, Long from, Long to) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("getStockDetailByCodeInCurrentTime");

        String url = String.format(URL,
                stockCode, from, to);
        log.info("[URI] = " + url);

        Mono<String> result = webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);

        stopWatch.stop();
        log.info("[TIME - getStockDetailByCodeAndTime] = " + stopWatch.getTotalTimeSeconds());

        return result;
    }


}
