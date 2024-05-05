package trading.stock.stocktrading.test.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import trading.stock.stocktrading.dtos.StockDetailResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testControllerEndpoint() {
        StockDetailResponseDTO response = restTemplate.getForObject("http://localhost:" + port + "/stock?symbol=HPG", StockDetailResponseDTO.class);

        Assertions.assertTrue(response.getStatus().equals("ok"));
        Assertions.assertTrue(response.getInfoByTimes().size() > 0);

    }
}

