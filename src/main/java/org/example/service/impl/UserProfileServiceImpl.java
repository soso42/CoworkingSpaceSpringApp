package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.user.UserProfileCreateDTO;
import org.example.model.entity.UserProfile;
import org.example.model.enums.UserRole;
import org.example.repository.UserProfileRepository;
import org.example.service.UserProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;


    @Override
    public UserProfile save(UserProfileCreateDTO dto) {
        UserProfile userProfile = modelMapper.map(dto, UserProfile.class);
        userProfile.setPassword(encoder.encode(dto.getPassword()));
        userProfile.setRole(UserRole.ROLE_USER);

        return repository.save(userProfile);
    }

}
