package trading.stock.stocktrading.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApiService {

    @Qualifier("applicationTaskExecutor")
    private final Executor executorService;
    private final WebClient.Builder webClientBuilder;

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

    public <T> List<CompletableFuture<T>> getDataFromURLsPostMethod(List<String> urls, Class<T> responseType, Object body) {
        return urls.stream()
                .map(url -> webClientBuilder
                        .build()
                        .post()
                        .uri(url)
                        .bodyValue(body)
                        .retrieve()
                        .bodyToMono(responseType)
                        .toFuture())
                .map(future -> CompletableFuture.supplyAsync(future::join, executorService))
                .collect(Collectors.toList());
    }

}