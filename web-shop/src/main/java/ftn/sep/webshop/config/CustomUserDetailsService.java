package ftn.sep.webshop.config;

import ftn.sep.webshop.dto.response.UserResponse;
import ftn.sep.webshop.exception.EntityNotFoundException;
import ftn.sep.webshop.security.UserPrinciple;
import ftn.sep.webshop.service.implementation.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
        public UserDetails loadUserByUsername(final String email) {
            UserResponse userDTO;
            try {
                userDTO = new UserResponse(userService.getVerifiedUser(email));
            } catch (EntityNotFoundException e) {
                return null;
            }

            return new UserPrinciple(userDTO);
        }


}
