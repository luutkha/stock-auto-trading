package trading.stock.stocktrading.repositories.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import trading.stock.stocktrading.dtos.responses.FilterStockRawResponseDTO;

import java.util.Optional;

@Repository
public interface FilterStockRawResponseRepository extends CrudRepository<FilterStockRawResponseDTO, String> {

    Optional<FilterStockRawResponseDTO> findByRedisId(String redisId);
}
