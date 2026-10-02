package com.civic.reporter.controller;

import com.civic.reporter.dto.LoginRequest;
import com.civic.reporter.dto.RegisterRequest;
import com.civic.reporter.dto.UserResponse;
import com.civic.reporter.model.User;
import com.civic.reporter.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String test() {
        return "Auth API working 🚀";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        User u = userService.register(req);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Registration successful",
                "user", UserResponse.from(u)
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User u = userService.login(req);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login successful",
                "user", UserResponse.from(u)
        ));
    }
}