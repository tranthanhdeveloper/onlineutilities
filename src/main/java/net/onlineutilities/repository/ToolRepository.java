package net.onlineutilities.repository;

import net.onlineutilities.entity.ToolsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends CrudRepository<ToolsEntity, Long> {

    Optional<ToolsEntity> findByName(String name);
}
