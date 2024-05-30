package trading.stock.stocktrading.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import trading.stock.stocktrading.models.User;
import trading.stock.stocktrading.services.UserService;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
        } else {
            log.error("User with email {} not exist", username);
            throw new UsernameNotFoundException("User not exist");
        }
    }

}
