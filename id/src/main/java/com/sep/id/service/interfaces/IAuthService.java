package com.sep.id.service.interfaces;

import com.sep.id.dto.response.LoginResponse;
import com.sep.id.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface IAuthService {
    void logout(HttpServletRequest request);
    LoginResponse login(final String email, final String password, final HttpServletRequest request, final HttpServletResponse response) throws UserLockedException, EntityNotFoundException, InvalidCredsException;
}
