package com.civic.reporter.controller;

import com.civic.reporter.dto.IssueRequest;
import com.civic.reporter.dto.IssueResponse;
import com.civic.reporter.dto.StatusUpdateRequest;
import com.civic.reporter.model.Issue;
import com.civic.reporter.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/issues")
@CrossOrigin(origins = "*")
public class IssueController {

    private final IssueService issueService;
    public IssueController(IssueService issueService) { this.issueService = issueService; }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody IssueRequest req) {
        Issue saved = issueService.create(req);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Issue created",
                "issue", IssueResponse.from(saved)
        ));
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        List<IssueResponse> list = issueService.findAll().stream().map(IssueResponse::from).toList();
        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", list.size(),
                "issues", list
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity.ok(Map.of("success", true, "count", issueService.count()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest req) {
        Issue updated = issueService.updateStatus(id, req.getStatus());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Status updated",
                "issue", IssueResponse.from(updated)
        ));
    }
}
