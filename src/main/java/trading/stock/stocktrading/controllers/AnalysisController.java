package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trading.stock.stocktrading.constants.VnDirectConstants;
import trading.stock.stocktrading.dtos.StockInfoDTO;
import trading.stock.stocktrading.dtos.requests.AnalysisStockRequestBodyDTO;
import trading.stock.stocktrading.dtos.responses.AnalysisStockDetailDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.entities.DarvasBox;
import trading.stock.stocktrading.facades.StockFacade;
import trading.stock.stocktrading.services.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
@Log4j2
public class AnalysisController {
    private final StockFacade stockFacade;
    private final ApiService apiService;

    public AnalysisController(StockFacade stockFacade, ApiService apiService) {
        this.stockFacade = stockFacade;
        this.apiService = apiService;
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
        return stockFacade.analysisStock(stockDetailByTime);
    }

    @PostMapping()
    public List<AnalysisStockDetailDTO> analysisStocks(@RequestBody AnalysisStockRequestBodyDTO body) {
        List<String> listStock = body.getSymbols().stream().map(code -> String.format(VnDirectConstants.STOCK_DETAIL_URL, code, body.getFrom(), body.getTo())).collect(Collectors.toList());
        List<CompletableFuture<String>> futures = apiService.getDataFromURLs(listStock, String.class);
        List<AnalysisStockDetailDTO> results = new ArrayList<>();
        futures.forEach(stringCompletableFuture -> {
            StockDetailResponseDTO stockDetail = stockFacade.convertRawStringToStockDetailDTO(stringCompletableFuture);
            if (stockDetail != null) {
                results.add(stockFacade.analysisStock(stockDetail));
            }
        });
        return results;
    }

}
