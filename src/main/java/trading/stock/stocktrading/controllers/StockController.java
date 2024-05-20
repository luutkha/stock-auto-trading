package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.StockDetailResponseDTO;
import trading.stock.stocktrading.facades.StockFacade;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;

@RestController
@RequestMapping("/stock")
@Log4j2
public class StockController {

    private final StockFacade stockFacade;
    private final StockService stockService;

    public StockController(StockFacade stockFacade, StockService stockService) {
        this.stockFacade = stockFacade;
        this.stockService = stockService;
    }

    @GetMapping()
    public ResponseEntity<StockDetailResponseDTO> getStockDetailByCodeAndTime(@RequestParam String symbol, @RequestParam(required = false) Long from, @RequestParam(required = false) Long to) throws IOException {

        StockDetailResponseDTO response = stockFacade.getStockDetailByTime(symbol, from, to);
        return ResponseEntity.ok(response);

    }


    @GetMapping("/async")
    public Mono<ResponseEntity<StockDetailResponseDTO>> getStockDetailByCodeAndTimeAsync(@RequestParam String symbol, @RequestParam(required = false) Long from, @RequestParam(required = false) Long to) throws IOException {


        Mono<String> detailMono;
        detailMono = stockService.getStockDetailByCodeAndTime(symbol, from, to);

        return detailMono.map(detail -> {
            try {
                StockDetailDTO stockDetail = StockDetailDTO.fromJson(detail);
                StockDetailResponseDTO response = StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
