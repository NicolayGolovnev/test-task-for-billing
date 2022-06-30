package ru.billing.testwork.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Модель данных задачи для работы со стороны клиента
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskModel {
    private Long id = null;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate taskDate;

    private Long tagId;
}
