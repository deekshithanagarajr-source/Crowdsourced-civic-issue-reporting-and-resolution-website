package com.civic.reporter.dto;

import com.civic.reporter.model.Issue;
import java.time.LocalDateTime;

public class IssueResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String username;
    private LocalDateTime createdAt;

    public static IssueResponse from(Issue i) {
        IssueResponse r = new IssueResponse();
        r.id = i.getId();
        r.title = i.getTitle();
        r.description = i.getDescription();
        r.status = i.getStatus();
        r.username = i.getUser() != null ? i.getUser().getUsername() : null;
        r.createdAt = i.getCreatedAt();
        return r;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getUsername() { return username; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
