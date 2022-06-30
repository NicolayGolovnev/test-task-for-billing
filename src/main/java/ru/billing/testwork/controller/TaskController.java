package ru.billing.testwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.billing.testwork.model.TaskModel;
import ru.billing.testwork.service.impl.TaskServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<Long> createTask(@RequestBody TaskModel taskModel) {
        return new ResponseEntity<>(taskService.save(taskModel), HttpStatus.CREATED);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);
        return new ResponseEntity<>("Task was successful deleted!", HttpStatus.OK);
    }

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
        }
        else
            return new ResponseEntity<>("File for upload not found!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
