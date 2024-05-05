package trading.stock.stocktrading.test.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trading.stock.stocktrading.services.StockService;


@SpringBootTest
public class StockServiceTest {


    @Autowired
    StockService stockService;


    @Test
    public void StockService_getStockDetailByCodeInCurrentTime() {
        String hpg = stockService.getStockDetailByCodeInCurrentTime("HPG").block();
        System.out.println(hpg);
        Assertions.assertNotEquals(hpg.length(), 0);
        Assertions.assertTrue(hpg.length() > 0);
    }
}
