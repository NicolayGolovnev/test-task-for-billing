package ru.billing.testwork.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskModel {
    private Long id = null;

    private String name;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskDate;

    private Long tagId;
}
