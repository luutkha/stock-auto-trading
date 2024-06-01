package trading.stock.stocktrading.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {

    private String email;
    private String userName;
    private String password;
    private List<String> roles;

}
