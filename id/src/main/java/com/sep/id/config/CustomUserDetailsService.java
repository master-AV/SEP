package com.sep.id.config;

import com.sep.id.dto.UserResponse;
import com.sep.id.security.UserPrinciple;
import com.sep.id.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
        public UserDetails loadUserByUsername(final String email) {
            UserResponse userDTO;
            try {
                userDTO = new UserResponse(userService.getVerifiedUser(email));
            } catch (Exception e) {// EntityNotFoundException
                return null;
            }

            return new UserPrinciple(userDTO);
        }


}
