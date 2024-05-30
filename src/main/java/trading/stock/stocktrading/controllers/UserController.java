package trading.stock.stocktrading.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import trading.stock.stocktrading.dtos.requests.LoginRequestDTO;
import trading.stock.stocktrading.dtos.requests.RegisterRequestDTO;
import trading.stock.stocktrading.dtos.responses.ValidateTokenResponseDTO;
import trading.stock.stocktrading.models.User;
import trading.stock.stocktrading.services.UserService;
import trading.stock.stocktrading.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Helllllllllllo");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequest) throws AuthenticationException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return jwtUtils.generateToken(loginRequest.getEmail());
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequestDTO body) throws AuthenticationException {

        return userService.save(new User(null, body.getEmail(), body.getUserName(), passwordEncoder.encode(body.getPassword())));
    }

    @GetMapping("validate")
    public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {
        jwtUtils.extractUsername(token);
        ValidateTokenResponseDTO bodyOfResponse = ValidateTokenResponseDTO.builder().isValid(true).build();
        return ResponseEntity.ok(bodyOfResponse);
    }

//    @GetMapping("/user/{email}")
//    public ResponseEntity<User> getUserById(@PathVariable("email") String email) {
//        System.out.println("CATCH");
//        return ResponseEntity.ok(userRepository.findByEmail(email));
//    }

}
