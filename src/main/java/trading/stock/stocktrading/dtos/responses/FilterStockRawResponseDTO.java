package trading.stock.stocktrading.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import trading.stock.stocktrading.dtos.CompanyReportDTO;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
public class FilterStockRawResponseDTO implements Serializable {
    private transient List<CompanyReportDTO> data;
    private int size;
    @Id
    @JsonIgnore
    private String redisId;

    @TimeToLive
    private Long expiration;
}
