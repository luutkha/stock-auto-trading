package trading.stock.stocktrading.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegisterRequestDTO {
    @NotNull
    @NotBlank
    private String email;
    private String userName;
    @Size(min = 6, max = 20, message = "Password length : 6 - 20 characters")
    private String password;
    private List<String> roles;
}
