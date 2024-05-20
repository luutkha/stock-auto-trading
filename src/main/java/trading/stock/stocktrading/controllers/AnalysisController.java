package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trading.stock.stocktrading.dtos.StockDetailResponseDTO;
import trading.stock.stocktrading.dtos.StockInfoDTO;
import trading.stock.stocktrading.entities.DarvasBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/analysis")
@Log4j2
public class AnalysisController {
    private final StockController stockController;

    public AnalysisController(StockController stockController) {
        this.stockController = stockController;
    }

    @GetMapping("/darvas")
    public ResponseEntity<List<DarvasBox>> detectDarvasBoxOfStock(@RequestParam String symbol, @RequestParam Long from, @RequestParam Long to) throws IOException {
        ResponseEntity<StockDetailResponseDTO> response = stockController.getStockDetailByCodeAndTime(symbol, from, to);
        List<Double> doubles = new ArrayList<>();
        List<StockInfoDTO> infoByTimes = response.getBody().getInfoByTimes();
        if (!Objects.deepEquals(infoByTimes, null)) {
            infoByTimes.forEach(e -> doubles.add(e.getPrice()));
        }
        List<DarvasBox> body = DarvasBox.analysisDarvasBoxByPrices(doubles);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public String analysisStock(@RequestParam String symbol, @RequestParam Long from, @RequestParam Long to, @RequestParam(required = false) Boolean darvas, @RequestParam(required = false) Boolean volume ){

        return "";
    }


}
