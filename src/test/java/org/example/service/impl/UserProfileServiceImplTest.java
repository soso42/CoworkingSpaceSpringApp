package org.example.service.impl;

import org.example.model.dto.user.UserProfileCreateDTO;
import org.example.repository.UserProfileRepository;
import org.example.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileService userProfileService;


    @Test
    void save_happyPath() {
        UserProfileCreateDTO dto = new UserProfileCreateDTO();
        dto.setUsername("jemo");
        dto.setPassword("1");

        userProfileService.save(dto);
    }

}
