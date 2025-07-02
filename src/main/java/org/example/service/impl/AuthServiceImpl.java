package org.example.service.impl;

import org.example.enums.AccessLevel;
import org.example.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AccessLevel accessLevel = AccessLevel.NONE;


    @Override
    public AccessLevel getAccessLevel() {
        return this.accessLevel;
    }

    @Override
    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

}
