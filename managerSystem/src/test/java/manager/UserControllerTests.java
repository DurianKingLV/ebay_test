package manager;

import manager.controller.UserController;
import manager.service.AccessService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @Mock
    private AccessService accessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void accessGrantedForUser() throws Exception {
        when(accessService.checkAccess(123456, "resource A")).thenReturn(true);

        ResponseEntity<?> response = userController.checkAccess("Basic user-encoded-base64", "resource A");

        verify(accessService).checkAccess(123456, "resource A");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Access granted to resource: resource A", response.getBody());
    }

    @Test
    void accessDeniedForUser() throws Exception {
        when(accessService.checkAccess(123456, "resource B")).thenReturn(false);

        ResponseEntity<?> response = userController.checkAccess("Basic user-encoded-base64", "resource B");

        verify(accessService).checkAccess(123456, "resource B");
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access denied to resource: resource B", response.getBody());
    }
}
