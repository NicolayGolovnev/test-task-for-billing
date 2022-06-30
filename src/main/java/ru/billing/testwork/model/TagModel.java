package ru.billing.testwork.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TagModel {
    private Long id = null;

    private String title;

    private List<TaskModel> tasks = new ArrayList<>();
}
