package trading.stock.stocktrading.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import trading.stock.stocktrading.dtos.CompanyReportDTO;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("FilterStockRawResponseDTO")
public class FilterStockRawResponseDTO implements Serializable {
    private List<CompanyReportDTO> data;
    private int size;
    @Id
    @JsonIgnore
    private String redisId;
    @TimeToLive(unit = TimeUnit.MINUTES)
    @Value("${stock.trade.cache.expire}")
    private Long expiration;
}
