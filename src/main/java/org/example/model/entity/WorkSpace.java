package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.enums.WorkSpaceType;

@Entity
@Table(name = "workspace")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkSpaceType type;

    private Integer price;

    private Boolean available = true;

}
