package trading.stock.stocktrading.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.dtos.requests.FilterStockRequestDTO;
import trading.stock.stocktrading.dtos.responses.FilterStockRawResponseDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.facades.StockFacade;
import trading.stock.stocktrading.repositories.redis.FilterStockRawResponseRepository;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stocks")
@Log4j2
public class StockController {

    private final StockFacade stockFacade;
    private final StockService stockService;
    private final FilterStockRawResponseRepository filterStockRawResponseRepository;

    public StockController(StockFacade stockFacade, StockService stockService, FilterStockRawResponseRepository filterStockRawResponseRepository) {
        this.stockFacade = stockFacade;
        this.stockService = stockService;
        this.filterStockRawResponseRepository = filterStockRawResponseRepository;
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

    @PostMapping("/filter")
    public FilterStockRawResponseDTO filterStock() {
        FilterStockRequestDTO.Filter filter = new FilterStockRequestDTO.Filter("floor", "EQUAL", "HNX,HOSE");
        FilterStockRequestDTO requestDTO = new FilterStockRequestDTO("code,companyNameVi,floor,priceCr,quarterReportDate,annualReportDate,industrylv2,marketCapCr", List.of(filter), "code:asc");
        return stockService.filterStock(requestDTO);
    }
}


