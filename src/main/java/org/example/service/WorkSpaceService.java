package org.example.service;

import org.example.model.dto.workspace.WorkSpaceCreateDTO;
import org.example.model.dto.workspace.WorkSpaceDTO;
import org.example.model.dto.workspace.WorkSpaceUpdateDTO;
import org.example.model.entity.WorkSpace;

import java.util.List;
import java.util.Optional;

public interface WorkSpaceService {
    WorkSpace save(WorkSpaceCreateDTO dto);
    WorkSpaceDTO saveWorkSpaceAndGetDto(WorkSpaceCreateDTO creationDTO);
    List<WorkSpaceDTO> findAll();
    Optional<WorkSpace> findById(Long id);
    Optional<WorkSpaceDTO> findDtoById(Long id);
    WorkSpaceDTO update(WorkSpaceUpdateDTO dto);
    void removeWorkSpace(Long id);
}
