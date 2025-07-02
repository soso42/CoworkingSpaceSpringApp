package org.example.service;

import org.example.model.dto.WorkSpaceCreationDTO;
import org.example.model.entity.WorkSpace;

import java.util.List;
import java.util.Optional;

public interface WorkSpaceService {
    WorkSpace save(WorkSpaceCreationDTO dto);
    void removeWorkSpace(Long id);
    List<WorkSpace> findAll();
    Optional<WorkSpace> findById(Long id);
}
