package com.tvtsoftware.miscutilities.services;

import com.tvtsoftware.miscutilities.entity.FileToken;

import java.util.List;

public interface FileDownloadService {

    FileToken findByToken(String token);
    void delete(String token);
    String createNew(FileToken fileToken);

    List<FileToken> findRecordOlderThan15Minutes();
}
