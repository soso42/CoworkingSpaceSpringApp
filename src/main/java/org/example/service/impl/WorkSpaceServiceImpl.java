package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.WorkSpaceCreationDTO;
import org.example.model.entity.WorkSpace;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;
import org.example.service.WorkSpaceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkSpaceServiceImpl implements WorkSpaceService {

    private final WorkSpaceRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public WorkSpace save(WorkSpaceCreationDTO dto) {
        WorkSpace workSpace = modelMapper.map(dto, WorkSpace.class);
        return repository.save(workSpace);
    }

    @Override
    public void removeWorkSpace(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new WorkSpaceNotFoundException("Work Space Not Found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<WorkSpace> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        return repository.findById(id);
    }

}
