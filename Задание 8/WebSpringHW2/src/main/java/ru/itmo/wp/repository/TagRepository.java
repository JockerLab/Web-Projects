package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    int countByName(String name);

    @Query(value = "SELECT * FROM tag WHERE name=?1", nativeQuery = true)
    Tag findByName(String name);
}
