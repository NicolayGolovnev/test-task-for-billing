package ru.billing.testwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.billing.testwork.model.TagModel;
import ru.billing.testwork.service.impl.TagServiceImpl;

@RestController
public class TagController {
    @Autowired
    private TagServiceImpl tagService;

    @PostMapping("/tag")
    public ResponseEntity<TagModel> createTag(@RequestBody TagModel tagModel) {
        return new ResponseEntity<>(tagService.save(tagModel), HttpStatus.CREATED);
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<TagModel> getAllTasks(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tagService.getAllTaskByTag(id), HttpStatus.OK);
    }

    @DeleteMapping("/tag/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        tagService.delete(id);
        return new ResponseEntity<>("Tag was successful deleted!", HttpStatus.OK);
    }
}
