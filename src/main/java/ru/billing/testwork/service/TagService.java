package ru.billing.testwork.service;

import ru.billing.testwork.model.TagModel;

/**
 * Доменный сервис справочника тегов
 */
public interface TagService {
    /**
     * Сохранениe тега
     *
     * @param tag модель данных тега
     * @return идентификатор сохраненного тега
     */
    TagModel save(TagModel tag);

    /**
     * Каскадное удаление тега со всеми прикрепленными к нему задачами
     *
     * @param id идентификатор тега
     */
    void delete(Long id);

    /**
     * Получения данных о теге со всеми его задачами
     *
     * @param id идентификатор тега
     * @return модель данных тега
     */
    TagModel getAllTaskByTag(Long id);
}
