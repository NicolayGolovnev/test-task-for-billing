package ru.billing.testwork;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.billing.testwork.entity.TagEntity;
import ru.billing.testwork.entity.TaskEntity;
import ru.billing.testwork.model.TagModel;
import ru.billing.testwork.repository.TagRepository;
import ru.billing.testwork.service.impl.TagServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TagServiceTest {
    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagRepository repository;

    @Test
    @DisplayName("Testing a crud-method save()")
    @Transactional
    void testSave() {
        List<TagModel> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TagModel model = TagModel.builder()
                    .title("test" + i)
                    .build();
            model = tagService.save(model);
            tags.add(model);
        }
        tags.get(3).setTitle("test-update");
        tagService.save(tags.get(3));

        for (int i = 0; i < 10; i++) {
            Long id = tags.get(i).getId();
            assertTrue(repository.findById(id).isPresent());
            if (i == 3)
                assertEquals("test-update", repository.findById(id).get().getTitle());
            repository.deleteById(id);
        }
    }

    @Test
    @DisplayName("Testing a crud-method delete()")
    @Transactional
    void testDelete() {
        TagEntity entity = TagEntity.builder()
                .title("deleting")
                .tasks(new ArrayList<>())
                .build();
        entity = repository.save(entity);

        Optional<TagEntity> findingTag = repository.findById(entity.getId());
        assertTrue(findingTag.isPresent());
        assertDoesNotThrow(() -> tagService.delete(findingTag.get().getId()));
        assertThrows(Exception.class, () -> tagService.delete(findingTag.get().getId()));
    }

    @Test
    @DisplayName("Testing a crud-method getAllTaskByTag()")
    @Transactional
    void testFind() {
        TagEntity entity;
        entity = TagEntity.builder()
                .title("finding")
                .tasks(new ArrayList<>())
                .build();
        entity.getTasks().add(TaskEntity.builder()
                .name("123")
                .description("123")
                .tag(entity)
                .build());
        entity = repository.save(entity);

        final TagModel[] findingTag = new TagModel[1];
        TagEntity finalEntity = entity;
        assertDoesNotThrow(() -> findingTag[0] = tagService.getAllTaskByTag(finalEntity.getId()));
        assertEquals(findingTag[0].getTasks().size(), 1);
        repository.deleteById(entity.getId());
    }
}
