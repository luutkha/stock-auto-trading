package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.facades.StockFacade;

import java.io.IOException;

@RestController
@RequestMapping("/stock")
@Log4j2
public class StockController {

    private final StockFacade stockFacade;

    public StockController(StockFacade stockFacade) {
        this.stockFacade = stockFacade;
    }

    @GetMapping()
    public ResponseEntity<StockDetailResponseDTO> getStockDetailByCodeAndTime(@RequestParam String symbol, @RequestParam(required = false) Long from, @RequestParam(required = false) Long to) throws IOException {

        StockDetailResponseDTO response = stockFacade.getStockDetailByTime(symbol, from, to);
        return ResponseEntity.ok(response);

    }


    // TODO: Spring security block async controller, for now, we enable async controller for any request
    @GetMapping("/async")
    public Mono<ResponseEntity<StockDetailResponseDTO>> getStockDetailByCodeAndTimeAsync(@RequestParam String symbol, @RequestParam(required = false) Long from, @RequestParam(required = false) Long to) throws Exception {
        return stockFacade.getStockDetailByTimeAsync(symbol, from, to).map(ResponseEntity::ok).onErrorResume(e -> {
            log.error("Error fetching stock details", e);
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        });
    }


}
