package ru.billing.testwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.billing.testwork.entity.TagEntity;
import ru.billing.testwork.entity.TaskEntity;
import ru.billing.testwork.model.TagModel;
import ru.billing.testwork.model.TaskModel;
import ru.billing.testwork.repository.TagRepository;
import ru.billing.testwork.service.TagService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository repository;

    @Override
    public Long save(TagModel tag) {
        TagEntity entity = TagEntity.builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .build();

        List<TaskEntity> tasks = new ArrayList<>();
        for (TaskModel task : tag.getTasks())
            tasks.add(TaskEntity.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .taskDate(task.getTaskDate())
                    .tag(entity)
                    .build());
        entity.setTasks(tasks);

        return repository.save(entity).getId();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public TagModel getAllTaskByTag(Long id) {
        Optional<TagEntity> tagEntity = repository.findById(id);
        if (tagEntity.isPresent()) {
            List<TaskModel> tasks = new ArrayList<>();
            for (TaskEntity task : tagEntity.get().getTasks())
                tasks.add(TaskModel.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .taskDate(task.getTaskDate())
                        .tagId(tagEntity.get().getId())
                        .build());

            return TagModel.builder()
                    .id(tagEntity.get().getId())
                    .title(tagEntity.get().getTitle())
                    .tasks(tasks)
                    .build();
        }
        else
            throw new EntityNotFoundException("Tag[id=" + id + "] not found in database!");

    }
}
