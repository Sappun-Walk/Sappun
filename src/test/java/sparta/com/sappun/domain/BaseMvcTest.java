package sparta.com.sappun.domain;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static sparta.com.sappun.test.UserTest.TEST_USER;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sparta.com.sappun.global.security.UserDetailsImpl;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
public class BaseMvcTest {

    @Autowired protected ObjectMapper objectMapper;
    protected MockMvc mockMvc;
    protected Principal mockPrincipal;

    @Autowired private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .apply(springSecurity(new MockSpringSecurityFilter()))
                        .build();
        mockUserSetup();
    }

    private void mockUserSetup() {
        UserDetails testUserDetails = new UserDetailsImpl(TEST_USER);
        mockPrincipal =
                new UsernamePasswordAuthenticationToken(
                        testUserDetails, "", testUserDetails.getAuthorities());
    }
}
