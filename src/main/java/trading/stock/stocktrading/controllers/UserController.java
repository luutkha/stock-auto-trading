package trading.stock.stocktrading.controllers;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import trading.stock.stocktrading.constants.SecurityConstants;
import trading.stock.stocktrading.dtos.requests.LoginRequestDTO;
import trading.stock.stocktrading.dtos.requests.RegisterRequestDTO;
import trading.stock.stocktrading.dtos.responses.ValidateTokenResponseDTO;
import trading.stock.stocktrading.models.User;
import trading.stock.stocktrading.services.UserService;
import trading.stock.stocktrading.utils.JwtUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping()
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello!");
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequest) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return jwtUtils.generateToken(loginRequest.getEmail());
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequestDTO body) throws AuthenticationException {
        List<String> roles = body.getRoles();
        roles = roles.stream().map(role -> SecurityConstants.ROLE_PREFIX + role).collect(Collectors.toList());

        return userService.save(new User(null, body.getEmail(), body.getUserName(), passwordEncoder.encode(body.getPassword()), roles));
    }

    @GetMapping("validate")
    public ResponseEntity<Object> validateToken(@RequestParam("token") String token) {
        jwtUtils.extractUsername(token);
        ValidateTokenResponseDTO bodyOfResponse = ValidateTokenResponseDTO.builder().isValid(true).build();
        return ResponseEntity.ok(bodyOfResponse);
    }


}
