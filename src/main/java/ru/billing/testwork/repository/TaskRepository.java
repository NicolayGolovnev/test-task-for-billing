package ru.billing.testwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.billing.testwork.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
