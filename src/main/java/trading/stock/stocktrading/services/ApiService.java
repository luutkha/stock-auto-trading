package trading.stock.stocktrading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ApiService {

    @Qualifier("applicationTaskExecutor")
    @Autowired
    Executor executorService;
    @Autowired
    StockService stockService;

    public List<CompletableFuture<String>> fetchDataAsync() {
        List<String> listStock = new ArrayList<>(Arrays.asList("MWG", "HPG", "SSI", "LPB", "AAA", "AAH", "AAS", "AAT", "HAH", "ZZZ", "VNG", "MSN", "VHC", "GVR", "VND", "VIX"));
        return IntStream.range(0, listStock.size() - 1).mapToObj(i -> {
            Mono<String> mono = stockService.getStockDetailByCodeInCurrentTime(listStock.get(i));
            return CompletableFuture.supplyAsync(mono::block, executorService);
        }).collect(Collectors.toList());
    }
}