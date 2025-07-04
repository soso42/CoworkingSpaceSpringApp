package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.workspace.WorkSpaceCreateDTO;
import org.example.model.dto.workspace.WorkSpaceDTO;
import org.example.model.dto.workspace.WorkSpaceUpdateDTO;
import org.example.model.entity.WorkSpace;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;
import org.example.service.WorkSpaceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkSpaceServiceImpl implements WorkSpaceService {

    private final WorkSpaceRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public WorkSpace save(WorkSpaceCreateDTO dto) {
        WorkSpace workSpace = modelMapper.map(dto, WorkSpace.class);
        return repository.save(workSpace);
    }

    @Override
    public WorkSpaceDTO saveWorkSpaceAndGetDto(WorkSpaceCreateDTO creationDTO) {
        return modelMapper.map(save(creationDTO), WorkSpaceDTO.class);
    }


    @Override
    public List<WorkSpaceDTO> findAll() {
        return repository.findAll().stream()
                .map(o -> modelMapper.map(o, WorkSpaceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<WorkSpace> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<WorkSpaceDTO> findDtoById(Long id) {
        return repository.findById(id)
                .map(workspace -> modelMapper.map(workspace, WorkSpaceDTO.class));
    }


    @Transactional
    @Override
    public WorkSpaceDTO update(WorkSpaceUpdateDTO dto) {
        Optional<WorkSpace> optWorkSpace = repository.findById(dto.getId());
        if (optWorkSpace.isEmpty()) {
            throw new WorkSpaceNotFoundException("WorkSpace not found with the Id "  + dto.getId());
        }
        WorkSpace workSpace = optWorkSpace.get();
        modelMapper.map(dto, workSpace);

        WorkSpace updated = repository.save(workSpace);
        return modelMapper.map(updated, WorkSpaceDTO.class);
    }


    @Override
    public void removeWorkSpace(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new WorkSpaceNotFoundException("Work Space Not Found with id: " + id);
        }
        repository.deleteById(id);
    }

}
