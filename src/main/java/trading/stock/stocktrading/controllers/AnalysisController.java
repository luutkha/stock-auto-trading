package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
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

    @GetMapping
    public ResponseEntity<List<DarvasBox>> findDarvasBoxOfStock(@RequestParam String symbol, @RequestParam Long from, @RequestParam Long to) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ResponseEntity<StockDetailResponseDTO> response = stockController.getStockDetailByCodeInCurrentTime(symbol, from, to);

        stopWatch.stop();
        log.info("[PROCESSED TIME] = " + stopWatch.getTotalTimeSeconds());
        List<Double> doubles = new ArrayList<>();

        List<StockInfoDTO> infoByTimes = response.getBody().getInfoByTimes();
        if (!Objects.deepEquals(infoByTimes, null)) {
            infoByTimes.forEach(e -> doubles.add(e.getPrice()));
        }
        List<DarvasBox> body = DarvasBox.analysisDarvasBoxByPrices(doubles);
        return ResponseEntity.ok(body);

    }
}
