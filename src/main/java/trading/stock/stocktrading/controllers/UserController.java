package trading.stock.stocktrading.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import trading.stock.stocktrading.dtos.requests.LoginRequest;
import trading.stock.stocktrading.dtos.responses.ValidateTokenResponse;
import trading.stock.stocktrading.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class UserController {
    // Các thành viên Autowired khác có thể được thêm vào tại đây
//    @Autowired
//    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Helllllllllllo");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return jwtUtils.generateToken(loginRequest.getEmail());
    }

    @GetMapping("validate")
    public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {
        jwtUtils.extractUsername(token);
        ValidateTokenResponse bodyOfResponse = ValidateTokenResponse.builder().isValid(true).build();
        return ResponseEntity.ok(bodyOfResponse);
    }

//    @GetMapping("/user/{email}")
//    public ResponseEntity<User> getUserById(@PathVariable("email") String email) {
//        System.out.println("CATCH");
//        return ResponseEntity.ok(userRepository.findByEmail(email));
//    }

}
