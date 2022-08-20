package com.dans.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dans.config.JwtTokenUtil;
import com.dans.model.dto.JwtDTO;
import com.dans.model.dto.UserDTO;
import com.dans.usecase.user.UserService;

@RestController
@CrossOrigin
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService,
            JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws Exception {
        Map<String, Object> responseMap = new HashMap<>();

        if (userDTO.getUsername() == "") {
            responseMap.put("error", true);
            responseMap.put("message", "Username can not be empty !!!");
            return ResponseEntity.status(400).body(responseMap);
        } else if (userDTO.getPassword() == "") {
            responseMap.put("error", true);
            responseMap.put("message", "Password can not be empty !!!");
            return ResponseEntity.status(400).body(responseMap);
        } else {
            authenticate(userDTO.getUsername(), userDTO.getPassword());
            final UserDetails userDetails = userService.loadUserByUsername(userDTO.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new JwtDTO(token));
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
