package org.example.service;

import org.example.model.dto.user.UserProfileCreateDTO;
import org.example.model.entity.UserProfile;

public interface UserProfileService {

    UserProfile save(UserProfileCreateDTO dto);

}
