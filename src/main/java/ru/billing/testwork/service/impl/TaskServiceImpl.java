package ru.billing.testwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.billing.testwork.entity.TagEntity;
import ru.billing.testwork.entity.TaskEntity;
import ru.billing.testwork.model.TaskModel;
import ru.billing.testwork.repository.TaskRepository;
import ru.billing.testwork.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository repository;

    @Override
    public Long save(TaskModel task) {
        TaskEntity entity = TaskEntity.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .taskDate(task.getTaskDate())
                .tag(TagEntity.builder()
                        .id(task.getTagId())
                        .build())
                .build();

        return repository.save(entity).getId();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TaskModel> getAllTasks() {
        List<TaskEntity> entityList = repository.findAll();

        List<TaskModel> tasks = new ArrayList<>();
        for (TaskEntity task : entityList)
            tasks.add(TaskModel.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .taskDate(task.getTaskDate())
                    .tagId(task.getTag().getId())
                    .build());
        return tasks;
    }
}
