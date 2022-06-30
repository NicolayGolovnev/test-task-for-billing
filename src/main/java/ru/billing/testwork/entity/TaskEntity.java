package ru.billing.testwork.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "task")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "task_date")
    private LocalDate taskDate;

    @ManyToOne
    @JoinColumn(name = "uid_tag", nullable = false, foreignKey = @ForeignKey(name = "fk_task_taguid"))
    private TagEntity tag;
}
