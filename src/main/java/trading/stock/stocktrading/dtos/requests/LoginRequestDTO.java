package trading.stock.stocktrading.dtos.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequestDTO {
    private String email;
    private String password;

}