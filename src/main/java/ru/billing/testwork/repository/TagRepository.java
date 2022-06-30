package ru.billing.testwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.billing.testwork.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
