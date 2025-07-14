package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.workspace.WorkSpaceCreateDTO;
import org.example.model.dto.workspace.WorkSpaceDTO;
import org.example.model.dto.workspace.WorkSpaceUpdateDTO;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.service.WorkSpaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workspaces")
@RequiredArgsConstructor
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkSpaceDTO createWorkSpace(@Validated WorkSpaceCreateDTO dto) {
        return workSpaceService.saveWorkSpaceAndGetDto(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Optional<WorkSpaceDTO> result = workSpaceService.findDtoById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkSpaceDTO> findAll() {
        return workSpaceService.findAll();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public WorkSpaceDTO update(@Validated WorkSpaceUpdateDTO dto, @PathVariable("id") Long id) {
        try {
            return workSpaceService.update(id, dto);
        } catch (WorkSpaceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        try {
            workSpaceService.removeWorkSpace(id);
        } catch (WorkSpaceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workspace not found", e);
        }
    }

}
