package ru.billing.testwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.billing.testwork.model.TaskModel;
import ru.billing.testwork.service.impl.TaskServiceImpl;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

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
}
