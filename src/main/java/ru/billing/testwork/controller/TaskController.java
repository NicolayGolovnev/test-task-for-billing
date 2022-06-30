package ru.billing.testwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.billing.testwork.model.TaskModel;
import ru.billing.testwork.service.impl.TaskServiceImpl;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Контроллер с crud операциями по задачам
 */
@RestController
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * GET-запрос на получение данных обо всех существующих задачах
     *
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     */
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    /**
     * POST-запрос на добавление/редактирование задачи
     *
     * @param taskModel     модель данных задачи
     * @param bindingResult ошибки валидации
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     */
    @PostMapping("/task")
    public ResponseEntity<?> createTask(
            @RequestBody @Valid TaskModel taskModel,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        else
            return new ResponseEntity<>(taskService.save(taskModel), HttpStatus.CREATED);
    }

    /**
     * DELETE-запрос на удаление задачи по идентификатору
     *
     * @param id идентификатор задачи
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     */
    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return new ResponseEntity<>("Task was successful deleted!", HttpStatus.OK);
    }

    /**
     * POST-запрос на загрузку файла по конкретной задачи на сервер
     *
     * @param id   идентификатор задачи
     * @param file загружаемый файл
     * @return ответ-сущность со статусом обработки и соответствующим сообщением
     * @throws IOException
     */
    @PostMapping("/task/{id}/upload")
    public ResponseEntity<String> uploadFile(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();

            String filename = "task-" + id + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadDir.getAbsolutePath() + "/" + filename));
            return new ResponseEntity<>("File " + filename + " was upload!", HttpStatus.OK);
        } else
            return new ResponseEntity<>("File for upload not found!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
