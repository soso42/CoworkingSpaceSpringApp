package org.example.repository;

import org.example.model.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Long> {
    WorkSpace save(WorkSpace workSpace);
    Optional<WorkSpace> findById(Long id);
    List<WorkSpace> findAll();
    void deleteById(Long id);
}
