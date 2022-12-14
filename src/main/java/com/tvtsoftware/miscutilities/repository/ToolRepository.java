package com.tvtsoftware.miscutilities.repository;

import com.tvtsoftware.miscutilities.entity.ToolsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepository extends CrudRepository<ToolsEntity, Long> {

    List<ToolsEntity> findByName(String name);

    @Query(nativeQuery = true, value = "select * from tools order by rand() limit 5;")
    List<ToolsEntity> findRandomTools();
}
