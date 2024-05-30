package trading.stock.stocktrading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class ApiService {

    @Qualifier("applicationTaskExecutor")
    @Autowired
    Executor executorService;
    @Autowired
    StockService stockService;

    @Autowired
    WebClient.Builder webClientBuilder;

    public <T> List<CompletableFuture<T>> getDataFromURLs(List<String> urls, Class<T> responseType) {
        return urls.stream()
                .map(url -> webClientBuilder
                        .build()
                        .get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(responseType)
                        .toFuture())
                .map(future -> CompletableFuture.supplyAsync(future::join, executorService))
                .collect(Collectors.toList());
    }

}