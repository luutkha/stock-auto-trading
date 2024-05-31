package trading.stock.stocktrading.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {

    private String email;
    private String userName;
    private String password;
}
