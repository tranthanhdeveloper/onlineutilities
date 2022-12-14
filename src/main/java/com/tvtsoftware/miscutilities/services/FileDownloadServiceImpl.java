package com.tvtsoftware.miscutilities.services;

import com.tvtsoftware.miscutilities.entity.FileToken;
import com.tvtsoftware.miscutilities.repository.FileDownloadRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

    private final FileDownloadRepository fileDownloadRepository;

    public FileDownloadServiceImpl(FileDownloadRepository fileDownloadRepository) {
        this.fileDownloadRepository = fileDownloadRepository;
    }

    @Override
    public FileToken findByToken(String token) {
        Optional<FileToken> optionalFileToken = fileDownloadRepository.findById(token);
        return  optionalFileToken.orElse(null);
    }

    @Override
    public void delete(String token) {
        fileDownloadRepository.deleteById(token);
    }

    @Override
    public String createNew(FileToken fileToken) {
        FileToken fileTokenToSave = FileToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withFileName(fileToken.getFileName())
                .withFilePath(fileToken.getFilePath())
                .withCreationDate(new Timestamp(new java.util.Date().getTime()))
                .build();

        FileToken savedToken = fileDownloadRepository.save(fileTokenToSave);
        return savedToken.getToken();
    }

    @Override
    public List<FileToken> findRecordOlderThan15Minutes() {
        Instant fifteenMinutesAgo = Instant.now().minus(15, ChronoUnit.MINUTES);
        return fileDownloadRepository.findTokenCreatedBefore(new Date(java.util.Date.from(fifteenMinutesAgo).getTime()));
    }
}
