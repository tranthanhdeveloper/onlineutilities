package com.tvtsoftware.miscutilities.repository;

import com.tvtsoftware.miscutilities.entity.FileToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface FileDownloadRepository extends CrudRepository<FileToken, String> {
    @Query("from FileToken as ft where ft.creationDate < :date")
    List<FileToken> findTokenCreatedBefore(Date date);
}
