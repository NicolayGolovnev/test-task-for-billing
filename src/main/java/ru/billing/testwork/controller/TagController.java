package ru.billing.testwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.billing.testwork.model.TagModel;
import ru.billing.testwork.service.impl.TagServiceImpl;

import javax.validation.Valid;

/**
 * Контроллер с crud операциями по тегам
 */
@RestController
public class TagController {
    @Autowired
    private TagServiceImpl tagService;

    /**
     * POST-запрос на добавление/редактирование тега
     * @param tagModel модель с данными тега
     * @param bindingResult ошибки валидации
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     */
    @PostMapping("/tag")
    public ResponseEntity<?> createTag(
            @RequestBody @Valid TagModel tagModel,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(tagService.save(tagModel), HttpStatus.CREATED);
    }

    /**
     * GET-запрос на вывод данных тега по идентификатору
     * @param id идентификатор тега
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     */
    @GetMapping("/tag/{id}")
    public ResponseEntity<TagModel> getAllTasks(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tagService.getAllTaskByTag(id), HttpStatus.OK);
    }

    /**
     * DELETE-запрос на удаление тега из БД по идентификатору
     * @param id идентификатор тега
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     */
    @DeleteMapping("/tag/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        tagService.delete(id);
        return new ResponseEntity<>("Tag was successful deleted!", HttpStatus.OK);
    }
}
