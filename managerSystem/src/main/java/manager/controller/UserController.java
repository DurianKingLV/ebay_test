package manager.controller;

import manager.service.AccessService;
import manager.utility.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AccessService accessService;

    @GetMapping("/{resource}")
    public ResponseEntity<?> checkAccess(@RequestHeader(value = "Authorization") String header, @PathVariable String resource) {
        try {
            int userId = HeaderUtil.extractUserId(header);
            if (accessService.checkAccess(userId, resource)) {
                return ResponseEntity.ok("Access granted to resource: " + resource);
            } else {
                return ResponseEntity.status(403).body("Access denied to resource: " + resource);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }
}