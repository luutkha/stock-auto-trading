package trading.stock.stocktrading.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ValidateTokenResponseDTO {
    private boolean isValid;
    private String message;
}
