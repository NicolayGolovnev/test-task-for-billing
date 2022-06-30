package ru.billing.testwork.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность, описывающая задачу. Связана с одноименной таблицей
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "task")
public class TaskEntity {
    /**
     * Идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование задачи
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание задачи
     */
    @Column(name = "description")
    private String description;

    /**
     * Дата задачи
     */
    @Column(name = "task_date")
    private LocalDate taskDate;

    /**
     * Идентификатор тега задачи. Связана при помощи внешнего ключа с таблицей тегов
     */
    @ManyToOne
    @JoinColumn(name = "uid_tag", nullable = false, foreignKey = @ForeignKey(name = "fk_task_taguid"))
    private TagEntity tag;
}
