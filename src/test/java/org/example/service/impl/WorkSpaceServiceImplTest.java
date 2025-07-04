package org.example.service.impl;

import org.example.model.dto.workspace.WorkSpaceCreateDTO;
import org.example.model.entity.WorkSpace;
import org.example.model.enums.WorkSpaceType;
import org.example.model.exceptions.WorkSpaceNotFoundException;
import org.example.repository.WorkSpaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest
class WorkSpaceServiceImplTest {

    @Mock
    private WorkSpaceRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private WorkSpaceServiceImpl workSpaceService;

    @BeforeEach
    void setUp() {
        this.workSpaceService = new WorkSpaceServiceImpl(this.repository, this.modelMapper);
    }


    @Test
    void save() {
        // Given
        WorkSpaceType type = WorkSpaceType.FLEXIBLE_DESK;
        Integer price = 999;
        Boolean availability = true;
        WorkSpace savedWorkSpace = new WorkSpace(1L, type, price, availability);
        when(repository.save(any(WorkSpace.class))).thenReturn(savedWorkSpace);

        // When
        WorkSpace result = workSpaceService.save(new WorkSpaceCreateDTO(type, price, availability));

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(savedWorkSpace.getId(), result.getId()),
                () -> assertEquals(savedWorkSpace.getType(), result.getType()),
                () -> assertEquals(savedWorkSpace.getPrice(), result.getPrice()),
                () -> assertEquals(savedWorkSpace.getAvailable(), result.getAvailable())
        );
    }

    @Test
    void removeWorkSpace_happyPath() {
        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.of(new WorkSpace()));

        // When
        // Then
        assertDoesNotThrow(() -> workSpaceService.removeWorkSpace(anyLong()));
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MIN_VALUE, Long.MAX_VALUE})
    void removeWorkSpace_whenIdNotExists(Long id) {
        // Given
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThrows(WorkSpaceNotFoundException.class, () -> {
            workSpaceService.removeWorkSpace(id);
        });
    }

    @Test
    void findAll() {
        // Given
        when(repository.findAll()).thenReturn(List.of());

        // When
        // Then
        assertEquals(0, workSpaceService.findAll().size());
    }

    @Test
    void findById_happyPath() {
        // Given
        Long workingId = 1L;
        when(repository.findById(workingId)).thenReturn(Optional.of(new WorkSpace()));

        // When
        Optional<WorkSpace> result = workSpaceService.findById(workingId);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void findById_whenIdNotExists() {
        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<WorkSpace> result = workSpaceService.findById(1L);

        // Then
        assertTrue(result.isEmpty());
    }

}
