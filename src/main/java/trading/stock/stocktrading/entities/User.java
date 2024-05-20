package trading.stock.stocktrading.entities;

import lombok.*;

//@Table(name = "user_master")
//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    //    @Id
    private String email;
    private String password;
}
