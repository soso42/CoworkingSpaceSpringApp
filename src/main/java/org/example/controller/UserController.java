package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.user.AuthRequestDTO;
import org.example.model.dto.user.UserProfileCreateDTO;
import org.example.security.jwt.JwtUtil;
import org.example.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserProfileService userProfileService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthRequestDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = ((List) userDetails.getAuthorities()).getFirst().toString();
        String token = JwtUtil.generateToken(username, role);

        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Validated UserProfileCreateDTO dto) {

        if (userProfileService.existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>(Map.of("message", "Username is already in use"), HttpStatus.BAD_REQUEST);
        }

        userProfileService.save(dto);

        return ResponseEntity.ok().build();
    }

}
