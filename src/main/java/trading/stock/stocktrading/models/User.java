package trading.stock.stocktrading.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//@Table(name = "user_master")
//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String userName;
    private String password;


}
