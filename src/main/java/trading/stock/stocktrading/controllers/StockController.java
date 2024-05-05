package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<StockDetailResponseDTO> getStockDetailByCodeInCurrentTime(@RequestParam String symbol) throws IOException {
        String stockDetailByCode = stockService.getStockDetailByCodeInCurrentTime(symbol).block();
        StockDetailDTO stockDetail = StockDetailDTO.fromJson(stockDetailByCode);
        StockDetailResponseDTO response = StockDetailResponseDTO.fromStockDetailDTO(stockDetail);
        return ResponseEntity.ok(response);
    }
}
