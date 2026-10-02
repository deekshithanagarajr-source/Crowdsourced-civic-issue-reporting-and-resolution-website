package com.civic.reporter.service;

import com.civic.reporter.dto.LoginRequest;
import com.civic.reporter.dto.RegisterRequest;
import com.civic.reporter.exception.ApiException;
import com.civic.reporter.model.User;
import com.civic.reporter.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already registered");
        }
        User u = new User(req.getUsername().trim(), req.getEmail().trim(), req.getPassword());
        return userRepository.save(u);
    }

    public User login(LoginRequest req) {
        User u = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        if (!u.getPassword().equals(req.getPassword())) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return u;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
