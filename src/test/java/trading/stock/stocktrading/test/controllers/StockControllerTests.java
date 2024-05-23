package trading.stock.stocktrading.test.controllers;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import trading.stock.stocktrading.controllers.StockController;
import trading.stock.stocktrading.dtos.StockDetailDTO;
import trading.stock.stocktrading.dtos.responses.StockDetailResponseDTO;
import trading.stock.stocktrading.facades.StockFacade;
import trading.stock.stocktrading.services.StockService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Log4j2
public class StockControllerTests {

    @MockBean
    private StockService stockServiceMock;
    @MockBean
    private StockFacade stockFacade;

    @Test
    void testGetStockDetailByCodeAndTime() throws IOException {
        // Arrange
        String symbol = "HPG";
        long from = 1714635915L;
        long to = 1714895215L;

        String jsonDetail = "{\"t\":[1714608000, 1714694400],\"c\":[28.35, 28.65],\"o\":[28.4, 28.35],\"h\":[28.4, 29.1],\"l\":[28.05, 28.35],\"v\":[7322100, 16714800],\"s\":\"ok\"}";
        Mockito.when(stockServiceMock.getStockDetailByCodeAndTime(symbol, from, to)).thenReturn(Mono.just(jsonDetail));

        StockDetailDTO stockDetailDTO = StockDetailDTO.fromJson(jsonDetail);
        StockDetailResponseDTO expectedResponse = StockDetailResponseDTO.fromStockDetailDTO(stockDetailDTO);

        StockController stockController = new StockController(stockFacade,stockServiceMock);

        // Act

        ResponseEntity<StockDetailResponseDTO> responseEntity = stockController.getStockDetailByCodeAndTime(symbol, from, to);
        StockDetailResponseDTO actualResponse = responseEntity.getBody();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, actualResponse);
    }

}

