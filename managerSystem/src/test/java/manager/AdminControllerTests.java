package manager;

import manager.controller.AdminController;
import manager.service.AccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTests {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AccessService accessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUserSuccessForAdmin() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", 123456);
        requestBody.put("endpoint", Arrays.asList("resource A", "resource B", "resource C"));

        ResponseEntity<?> response = adminController.addUser("admin:adminpass", requestBody);

        verify(accessService).addUserAccess(123456, Arrays.asList("resource A", "resource B", "resource C"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Access granted to user.", response.getBody());
    }

    @Test
    void addUserFailureForNonAdmin() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", 123456);
        requestBody.put("endpoint", Arrays.asList("resource A", "resource B", "resource C"));

        ResponseEntity<?> response = adminController.addUser("user:userpass", requestBody);

        verify(accessService, never()).addUserAccess(anyInt(), anyList());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access Denied: Only admins can perform this action.", response.getBody());
    }
}