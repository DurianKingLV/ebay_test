package manager.controller;

import manager.service.AccessService;
import manager.utility.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccessService accessService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestHeader(value = "Authorization") String header, @RequestBody Map<String, Object> request) {
        try {
            if (!HeaderUtil.isAdmin(header)) {
                return ResponseEntity.status(403).body("Access Denied: Only admins can perform this action.");
            }

            int userId = (Integer) request.get("userId");
            @SuppressWarnings("unchecked")
            List<String> endpoints = (List<String>) request.get("endpoint");
            accessService.addUserAccess(userId, endpoints);
            return ResponseEntity.ok("Access granted to user.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }
}