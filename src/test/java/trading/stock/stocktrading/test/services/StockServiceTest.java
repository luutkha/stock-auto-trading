package trading.stock.stocktrading.test.services;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trading.stock.stocktrading.services.StockService;


@SpringBootTest
@Log4j2
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Test
    public void StockService_getStockDetailByCodeInCurrentTime() {
        String hpg = stockService.getStockDetailByCodeInCurrentTime("HPG").block();
        assert hpg != null;
        log.info("[RESPONSE] = " + hpg);
        Assertions.assertNotEquals(0, hpg.length());
        Assertions.assertFalse(hpg.isEmpty());
    }
}
