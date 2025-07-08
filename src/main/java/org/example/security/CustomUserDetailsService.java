package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.UserProfile;
import org.example.repository.UserProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserProfile> optUser = userProfileRepository.findByUsername(username);

        return optUser.map(SecurityUser::new).orElse(null);
    }

}
