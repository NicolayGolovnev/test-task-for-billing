package ru.billing.testwork.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TagModel {
    private Long id = null;

    @NotBlank
    @Size(max = 255)
    private String title;

    private List<TaskModel> tasks = new ArrayList<>();
}
