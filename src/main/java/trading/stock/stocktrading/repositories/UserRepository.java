package trading.stock.stocktrading.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import trading.stock.stocktrading.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    User findByUserName(String username);

    User findByEmailAndPassword(String email, String password);
}
