package ru.billing.testwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @CachePut(cacheNames = "tagsCache", key = "#tag.id")
    @Override
    public TagModel save(TagModel tag) {
        TagEntity tagEntity = TagEntity.builder()
                .title(tag.getTitle())
                .tasks(new ArrayList<>())
                .build();

        if (tag.getId() != null) {
            Optional<TagEntity> optionalTagEntity = repository.findById(tag.getId());
            if (optionalTagEntity.isPresent()) {
                tagEntity.setTitle(tag.getTitle());
                tagEntity.setTasks(optionalTagEntity.get().getTasks());
            }
        }
        tagEntity = repository.save(tagEntity);

        tag.setId(tagEntity.getId());
        List<TaskModel> tasks = new ArrayList<>();
        for (TaskEntity task : tagEntity.getTasks())
            tasks.add(TaskModel.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .taskDate(task.getTaskDate())
                    .tagId(tagEntity.getId())
                    .build());
        tag.setTasks(tasks);

        return tag;
    }

    @CacheEvict(cacheNames = "tagsCache", key = "#id")
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Cacheable(cacheNames = "tagsCache", key = "#id")
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
