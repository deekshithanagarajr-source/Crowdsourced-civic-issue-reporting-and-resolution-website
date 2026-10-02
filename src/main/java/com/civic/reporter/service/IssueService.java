package com.civic.reporter.service;
import com.civic.reporter.dto.IssueRequest;
import com.civic.reporter.exception.ApiException;
import com.civic.reporter.model.Issue;
import com.civic.reporter.model.User;
import com.civic.reporter.repository.IssueRepository;
import com.civic.reporter.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
 
@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
 
    public IssueService(IssueRepository issueRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }
 
    public Issue create(IssueRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
        Issue issue = new Issue();
        issue.setTitle(req.getTitle());
        issue.setDescription(req.getDescription());
        issue.setUser(user);
        issue.setLatitude(req.getLatitude());
        issue.setLongitude(req.getLongitude());
        issue.setPhoto(req.getPhoto());
        return issueRepository.save(issue);
    }
 
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }
 
    public long count() {
        return issueRepository.count();
    }
 
    public Issue updateStatus(Long id, String status) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Issue not found"));
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}