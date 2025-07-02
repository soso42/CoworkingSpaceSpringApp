package org.example.service;

import org.example.model.enums.AccessLevel;

public interface AuthService {
    AccessLevel getAccessLevel();
    void setAccessLevel(AccessLevel accessLevel);
}
