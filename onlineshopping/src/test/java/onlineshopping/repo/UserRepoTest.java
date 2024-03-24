package onlineshopping.repo;

import onlineshopping.model.AuthResponse;
import onlineshopping.model.UserDto;
import onlineshopping.service.impl.BaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserRepoTest {

    @Mock
    private UserRepo repo;

    @InjectMocks
    private BaseServiceImpl baseService;

    @Test
    public void saveUser(){
        UserDto testUser = new UserDto(
                "Muddy Fakih", "muddyfakih98@gmail.com", "0717611117",
                "backend","ADMIN"
        );

        ResponseEntity<AuthResponse> checkResponse = baseService.createAccount(testUser);
        assertEquals(HttpStatus.OK, checkResponse.getStatusCode());
        assertEquals(checkResponse, checkResponse.getBody());
    }

}