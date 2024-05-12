package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.StockDetailResponseDTO;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/stock")
@Log4j2
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping()
    public ResponseEntity<StockDetailResponseDTO> getStockDetailByCodeInCurrentTime(@RequestParam String symbol, @RequestParam(required = false) Long from, @RequestParam(required = false) Long to) throws IOException {
        String stockDetailByCode;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("call external API");
        if (Objects.deepEquals(null, from) || Objects.deepEquals(null, to)) {
            stockDetailByCode = stockService.getStockDetailByCodeInCurrentTime(symbol).block();
        } else {
            stockDetailByCode = stockService.getStockDetailByCodeAndTime(symbol, from, to).block();
        }
        stopWatch.stop();
        log.info("[TIME] {} {}", stopWatch.getId(), stopWatch.getTotalTimeSeconds());

        StockDetailDTO stockDetail = StockDetailDTO.fromJson(stockDetailByCode);
        StockDetailResponseDTO response = StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/async")
    public Mono<ResponseEntity<StockDetailResponseDTO>> getStockDetailByCodeInCurrentTimeTest(@RequestParam String symbol, @RequestParam(required = false) Long from, @RequestParam(required = false) Long to) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("call external API");

        Mono<String> detailMono;
        if (from == null || to == null) {
            detailMono = stockService.getStockDetailByCodeInCurrentTime(symbol);
        } else {
            detailMono = stockService.getStockDetailByCodeAndTime(symbol, from, to);
        }

        return detailMono.map(detail -> {
            try {
                StockDetailDTO stockDetail = StockDetailDTO.fromJson(detail);
                StockDetailResponseDTO response = StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
                stopWatch.stop();
                log.info("[TIME] {} {}", stopWatch.getId(), stopWatch.getTotalTimeSeconds());
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
