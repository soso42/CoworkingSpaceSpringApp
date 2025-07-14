package org.example.repository;

import jakarta.transaction.Transactional;
import org.example.model.entity.WorkSpace;
import org.example.model.enums.WorkSpaceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class WorkSpaceRepositoryTest {

    @Autowired
    private WorkSpaceRepository repository;


    @Test
    void save_workspace_happyPath() {
        // Given
        WorkSpace workSpace = new WorkSpace(null, WorkSpaceType.FLEXIBLE_DESK, 999, true);

        // When
        WorkSpace savedWorkSpace = repository.save(workSpace);

        // Then
        assertNotNull(savedWorkSpace);
        assertAll(
                () -> assertEquals(workSpace.getType(), savedWorkSpace.getType()),
                () -> assertEquals(workSpace.getPrice(), savedWorkSpace.getPrice()),
                () -> assertEquals(workSpace.getAvailable(), savedWorkSpace.getAvailable())
        );
    }

    @Test
    void findById_happyPath() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .type(WorkSpaceType.CONFERENCE_ROOM)
                .price(111)
                .available(true)
                .build();
        Long id = repository.save(workSpace).getId();

        // When
        Optional<WorkSpace> optWorkSpace = repository.findById(id);

        // Then
        assertTrue(optWorkSpace.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {Long.MAX_VALUE, Long.MAX_VALUE})
    void findById_whenIdNotExists_returnsEmptyOptional(Long id) {
        // Given
        // When
        Optional<WorkSpace> optWorkSpace = repository.findById(id);

        // Then
        assertTrue(optWorkSpace.isEmpty());
    }

    @Test
    void findAll_happyPath() {
        // Given
        int initialSize = repository.findAll().size();
        WorkSpace workSpace = WorkSpace.builder()
                        .type(WorkSpaceType.FLEXIBLE_DESK)
                        .price(123)
                        .available(true)
                        .build();
        repository.save(workSpace);

        // When
        int finalSize = repository.findAll().size();

        // Then
        assertEquals(initialSize + 1, finalSize);
    }

    @Test
    void deleteById_happyPath() {
        // Given
        WorkSpace workSpace = WorkSpace.builder()
                .type(WorkSpaceType.CONFERENCE_ROOM)
                .price(111)
                .available(true)
                .build();
        Long id = repository.save(workSpace).getId();

        // When
        repository.deleteById(id);

        // Then
        assertTrue(repository.findById(id).isEmpty());
    }

}
