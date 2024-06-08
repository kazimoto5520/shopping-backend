package onlineshopping.jwt.google;

import lombok.RequiredArgsConstructor;
import onlineshopping.constants.UserRole;
import onlineshopping.entity.Customer;
import onlineshopping.repo.UserRepo;
import onlineshopping.service.impl.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserDetailsService userDetailsService;
    private final UserRepo userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            OAuth2User oauth2User = super.loadUser(userRequest);

            // Extract user info
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String mobile = oauth2User.getAttribute("mobile");

            Customer customer = userRepo.findByEmail(email);
            if (customer == null) {
                Customer googleCustomer = new Customer();
                googleCustomer.setName(name);
                googleCustomer.setEmail(email);
                googleCustomer.setMobile(mobile);
                googleCustomer.setEnrollNumber(AuthService.generateUniqueNumber());
                googleCustomer.setRole(UserRole.CUSTOMER);
                userRepo.save(googleCustomer);
            }

            assert customer != null;

            UserDetails userDetails = userDetailsService.loadUserByUsername(customer.getEmail());

            return new DefaultOAuth2User(
                    oauth2User.getAuthorities(),
                    oauth2User.getAttributes(),
                    "name");
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("Error: Failed to process user details"+e.getMessage());
        }
    }
}
