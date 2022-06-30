package ru.billing.testwork.service;

import ru.billing.testwork.model.TaskModel;

import java.util.List;

/**
 * Доменный сервис задач
 */
public interface TaskService {
    /**
     * Сохранения задачи
     * @param task модель данных задачи
     * @return идентификатор сохраненной задачи
     */
    Long save(TaskModel task);

    /**
     * Удаление задачи
     * @param id идентификатор задачи
     */
    void delete(Long id);

    /**
     * Получения списка всех существующих задач
     * @return список задач
     */
    List<TaskModel> getAllTasks();
}
