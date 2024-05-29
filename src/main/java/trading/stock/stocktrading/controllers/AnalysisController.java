package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trading.stock.stocktrading.dtos.DefaultMovingAverageDTO;
import trading.stock.stocktrading.dtos.StockInfoDTO;
import trading.stock.stocktrading.dtos.responses.AnalysisStockDetailDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.entities.DarvasBox;
import trading.stock.stocktrading.entities.Stock;
import trading.stock.stocktrading.facades.StockFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/analysis")
@Log4j2
public class AnalysisController {
    private final StockController stockController;
    private final StockFacade stockFacade;

    public AnalysisController(StockController stockController, StockFacade stockFacade) {
        this.stockController = stockController;
        this.stockFacade = stockFacade;
    }

    private static List<DarvasBox> analysisDarvasBox(StockDetailResponseDTO response) {
        List<Double> doubles = new ArrayList<>();
        List<StockInfoDTO> infoByTimes = response.getInfoByTimes();
        if (!Objects.deepEquals(infoByTimes, null)) {
            infoByTimes.forEach(e -> doubles.add(e.getPrice()));
        }
        return DarvasBox.analysisDarvasBoxByPrices(doubles);
    }

    @GetMapping("/darvas")
    public ResponseEntity<List<DarvasBox>> detectDarvasBoxOfStock(@RequestParam String symbol, @RequestParam Long from, @RequestParam Long to) throws IOException {
        StockDetailResponseDTO response = stockFacade.getStockDetailByTime(symbol, from, to);
        List<DarvasBox> body = DarvasBox.analysisDarvasBoxByPrices(response.getPrices());
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public AnalysisStockDetailDTO analysisStock(@RequestParam String symbol, @RequestParam Long from, @RequestParam Long to, @RequestParam(required = false) Boolean darvas, @RequestParam(required = false) Boolean volume) throws IOException {
        StockDetailResponseDTO stockDetailByTime = stockFacade.getStockDetailByTime(symbol, from, to);
        List<DarvasBox> darvasBoxes = DarvasBox.analysisDarvasBoxByPrices(stockDetailByTime.getPrices());
        DefaultMovingAverageDTO defaultMovingAverage = Stock.calculateDefaultMovingAverage(stockDetailByTime.getPrices());
        DefaultMovingAverageDTO volumes = Stock.calculateDefaultVolume(stockDetailByTime.getPrices());
        AnalysisStockDetailDTO analysisStockDetailDTO = AnalysisStockDetailDTO.builder()
                .darvasBoxes(darvasBoxes)
                .volumeAnalysis(volumes)
                .movingAverages(defaultMovingAverage)
                .stock(stockDetailByTime)
                .build();

        return analysisStockDetailDTO;
    }

}
