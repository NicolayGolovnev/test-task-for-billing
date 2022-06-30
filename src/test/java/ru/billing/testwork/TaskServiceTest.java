package ru.billing.testwork;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.billing.testwork.entity.TagEntity;
import ru.billing.testwork.entity.TaskEntity;
import ru.billing.testwork.model.TaskModel;
import ru.billing.testwork.repository.TagRepository;
import ru.billing.testwork.repository.TaskRepository;
import ru.billing.testwork.service.impl.TaskServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Testing a crud-method save()")
    @Transactional
    void testSave() {
        TagEntity tag = TagEntity.builder()
                .title("tag-test")
                .build();
        tag = tagRepository.save(tag);

        List<TaskModel> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TaskModel model = TaskModel.builder()
                    .name("test" + i)
                    .description("description")
                    .tagId(tag.getId())
                    .build();
            model.setId(taskService.save(model));
            tasks.add(model);
        }
        tasks.get(3).setName("test-update1");
        taskService.save(tasks.get(3));
        tasks.get(5).setName("test-update2");
        taskService.save(tasks.get(5));
        tasks.get(7).setName("test-update3");
        taskService.save(tasks.get(7));

        for (int i = 0; i < 10; i++) {
            Long id = tasks.get(i).getId();
            assertTrue(repository.findById(id).isPresent());
            if (i == 3)
                assertEquals("test-update1", repository.findById(id).get().getName());
            else if (i == 5)
                assertEquals("test-update2", repository.findById(id).get().getName());
            else if (i == 7)
                assertEquals("test-update3", repository.findById(id).get().getName());
            repository.deleteById(id);
        }
    }

    @Test
    @DisplayName("Testing a crud-method delete()")
    @Transactional
    void testDelete() {
        TagEntity tag = TagEntity.builder()
                .title("tag-test")
                .build();
        tag = tagRepository.save(tag);

        TaskEntity entity = TaskEntity.builder()
                .name("deleting")
                .description("deleting")
                .tag(tag)
                .build();
        entity = repository.save(entity);

        Optional<TaskEntity> findingTask = repository.findById(entity.getId());
        assertTrue(findingTask.isPresent());
        assertDoesNotThrow(() -> taskService.delete(findingTask.get().getId()));
        assertThrows(Exception.class, () -> taskService.delete(findingTask.get().getId()));
    }

    @Test
    @DisplayName("Testing a crud-method getAllTasks()")
    @Transactional
    void testFind() {
        TagEntity tag = TagEntity.builder()
                .title("tag-test")
                .build();
        tag = tagRepository.save(tag);

        repository.deleteAll();
        for (int i = 0; i < 10; i++) {
            TaskEntity entity = TaskEntity.builder()
                    .name("123" + i)
                    .description("123")
                    .tag(tag)
                    .build();
            repository.save(entity);
        }

        List<TaskModel> allTasks = taskService.getAllTasks();
        assertEquals(allTasks.size(), 10);
        for (int i = 0; i < 10; i++)
            assertEquals(allTasks.get(i).getName(), "123" + i);
    }
}
