package trading.stock.stocktrading.controllers;

import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class HomeController {

    @PermitAll
    @GetMapping("/check")
    public ResponseEntity<String> checkStatus() {
        return ResponseEntity.ok("ok");
    }
}
