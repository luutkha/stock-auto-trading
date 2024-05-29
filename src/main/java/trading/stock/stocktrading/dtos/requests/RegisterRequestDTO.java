package trading.stock.stocktrading.dtos.requests;

import lombok.Getter;

@Getter
public class RegisterRequestDTO {

    private String email;
    private String userName;
    private String password;
}
