package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.mockito.internal.matchers.Null;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.StockDetailResponseDTO;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;

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
        if (Null.NULL.matches(from) || Null.NULL.matches(to)) {
            String stockDetailByCode = stockService.getStockDetailByCodeInCurrentTime(symbol).block();
            StockDetailDTO stockDetail = StockDetailDTO.fromJson(stockDetailByCode);
            StockDetailResponseDTO response = StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
            return ResponseEntity.ok(response);
        } else {
            String stockDetailByCode = stockService.getStockDetailByCodeAndTime(symbol, from, to).block();
            StockDetailDTO stockDetail = StockDetailDTO.fromJson(stockDetailByCode);
            StockDetailResponseDTO response = StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
            return ResponseEntity.ok(response);
        }

    }


}
