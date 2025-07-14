package org.example.repository;

import org.example.model.entity.UserProfile;
import org.example.model.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserProfileRepositoryTest {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    void save_happyPath() {
        // Given
        String username = "testUsername";
        String password = "testPassword";
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        UserRole defaultRole = UserRole.ROLE_USER;

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        userProfile.setPassword(encodedPassword);
        userProfile.setRole(defaultRole);

        // When
        UserProfile result = userProfileRepository.save(userProfile);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(username, result.getUsername()),
                () -> assertEquals(encodedPassword, result.getPassword()),
                () -> assertEquals(defaultRole, result.getRole())
        );
    }

    @Test
    void findById_happyPath() {
        // Given
        String username = "testUsername";
        String password = "testPassword";
        UserRole defaultRole = UserRole.ROLE_USER;

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        userProfile.setPassword(bCryptPasswordEncoder.encode(password));
        userProfile.setRole(defaultRole);

        UserProfile dbUserProfile = userProfileRepository.save(userProfile);

        // When
        Optional<UserProfile> result = userProfileRepository.findById(dbUserProfile.getId());

        // Then
        assertTrue(result.isPresent());
    }

    @ParameterizedTest()
    @ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
    void findById_whenIdNotExists_returnsEmptyOptional(Long id) {
        // Given
        // When
        Optional<UserProfile> result = userProfileRepository.findById(id);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void findByUsername_happyPath() {
        // Given
        String username = "testUsername";
        String password = "testPassword";
        UserRole defaultRole = UserRole.ROLE_USER;

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(username);
        userProfile.setPassword(bCryptPasswordEncoder.encode(password));
        userProfile.setRole(defaultRole);

        UserProfile dbUserProfile = userProfileRepository.save(userProfile);

        // When
        Optional<UserProfile> result = userProfileRepository.findByUsername(username);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void findById_whenIdNotExists_returnsEmptyOptional() {
        // Given
        String fakeUsername = "fakeusername1357";

        // When
        Optional<UserProfile> result = userProfileRepository.findByUsername(fakeUsername);

        // Then
        assertTrue(result.isEmpty());
    }

}
