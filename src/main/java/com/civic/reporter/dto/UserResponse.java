package com.civic.reporter.dto;

import com.civic.reporter.model.User;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    public static UserResponse from(User u) {
        UserResponse r = new UserResponse();
        r.id = u.getId(); r.username = u.getUsername(); r.email = u.getEmail();
        return r;
    }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
