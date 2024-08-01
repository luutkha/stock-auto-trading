package trading.stock.stocktrading.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.constants.VnDirectConstants;
import trading.stock.stocktrading.dtos.requests.FilterStockRequestDTO;
import trading.stock.stocktrading.dtos.responses.FilterStockRawResponseDTO;
import trading.stock.stocktrading.repositories.redis.FilterStockRawResponseRepository;
import trading.stock.stocktrading.services.StockService;

import java.util.Optional;

@Service
@Log4j2
public class StockServiceImpl implements StockService {
    private final WebClient.Builder webClientBuilder;
    private final FilterStockRawResponseRepository filterStockRawResponseRepository;


    public StockServiceImpl(WebClient.Builder webClientBuilder, FilterStockRawResponseRepository filterStockRawResponseRepository) {
        this.webClientBuilder = webClientBuilder;
        this.filterStockRawResponseRepository = filterStockRawResponseRepository;
    }


    @Override
    public Mono<String> getStockDetailByCodeInCurrentTime(String stockCode) {

        long currentTime = System.currentTimeMillis() / 1000L;

        int miliOf2Days = 60 * 60 * 24 * 2;
        String url = String.format(VnDirectConstants.STOCK_DETAIL_URL, stockCode, currentTime - miliOf2Days, currentTime + 100);
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
    public FilterStockRawResponseDTO filterStock(FilterStockRequestDTO req) {
        String redisId = req.toString();
        FilterStockRawResponseDTO response;

        Optional<FilterStockRawResponseDTO> cacheResponseOpt = filterStockRawResponseRepository.findById(redisId);

        if (cacheResponseOpt.isPresent()) {
            return cacheResponseOpt.get();
        }

        response = webClientBuilder
                .build()
                .post()
                .uri(VnDirectConstants.STOCK_SEARCH_URL)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(FilterStockRawResponseDTO.class)
                .doOnSuccess(resp -> log.info("[Received response] size = {}", resp.getData().size()))
                .onErrorResume(e -> {
                    log.error("Error occurred while filtering stock", e);
                    return Mono.empty();
                })
                .toFuture().join();
        response.setRedisId(redisId);
        response.setExpiration(10L);
        filterStockRawResponseRepository.save(response);

        return response;

    }


}
