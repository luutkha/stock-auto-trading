package trading.stock.stocktrading.services;

import trading.stock.stocktrading.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    User save(User user);

    void deleteById(String id);

    User findByUserName(String username);

    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
