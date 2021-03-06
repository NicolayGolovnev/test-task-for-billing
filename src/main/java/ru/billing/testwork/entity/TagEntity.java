package ru.billing.testwork.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность, описывающая тег. Связана с одноименной таблицей
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tag")
public class TagEntity {
    /**
     * Идентификатор тега
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Заголовок тега
     */
    @Column(name = "title")
    private String title;

    /**
     * Связь с таблицей задач в отношении один ко многим
     */
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEntity> tasks;
}
