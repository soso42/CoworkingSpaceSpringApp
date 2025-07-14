package org.example.service;

import org.example.model.dto.user.UserProfileCreateDTO;
import org.example.model.entity.UserProfile;

import java.util.Optional;

public interface UserProfileService {

    UserProfile save(UserProfileCreateDTO dto);
    boolean existsByUsername(String username);

}
