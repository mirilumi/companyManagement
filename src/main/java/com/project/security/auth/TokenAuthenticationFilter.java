package com.project.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.exceptions.CustomError;
import com.project.exceptions.CustomException;
import com.project.exceptions.ErrorCode;
import com.project.exceptions.MessageKey;
import com.project.security.TokenHelper;
import com.project.service.MessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final Log logger = LogFactory.getLog(this.getClass());

    private TokenHelper tokenHelper;

    private UserDetailsService userDetailsService;
    @Autowired
    MessageService messageService;

    @Autowired
    ObjectMapper objectMapper;

    public TokenAuthenticationFilter(TokenHelper tokenHelper, UserDetailsService userDetailsService) {
        this.tokenHelper = tokenHelper;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String username;
        String authToken = tokenHelper.getToken(request);

        if (authToken != null) {
            // get username from token
            username = tokenHelper.getUsernameFromToken(authToken);
            if (username != null) {
                // get user
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (tokenHelper.validateToken(authToken, userDetails)) {
                        // create authentication
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                catch (CustomException e) {
                    writeErrorResponse(response, e.getErrorCode(), e.getMessageKey(), e.getMessageTokens());
                    return;
                } catch (UsernameNotFoundException e) {
                    writeErrorResponse(response, ErrorCode.BAD_REQUEST, MessageKey.bad_credentials);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
    private void writeErrorResponse(HttpServletResponse response, ErrorCode errorCode, MessageKey messageKey, Object... messageTokens) {
        CustomException aie = new CustomException(errorCode,messageKey, messageTokens);
        CustomError ableIdentityError = CustomError.get(aie, messageService);
        ableIdentityError.writeAsResponse(response, objectMapper);
    }
}

