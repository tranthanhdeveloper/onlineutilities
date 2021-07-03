package net.onlineutilities.scheduled.task;

import lombok.extern.log4j.Log4j2;
import net.onlineutilities.entity.FileToken;
import net.onlineutilities.services.FileDownloadService;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Log4j2
public class FileCleanUp {
    private final FileDownloadService fileDownloadService;

    public FileCleanUp(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void cleanInvalidTempFile() {
        AtomicInteger cleanCounter = new AtomicInteger();
        log.info("Starting clean up file");
        List<FileToken> filesToDeleted = fileDownloadService.findRecordOlderThan15Minutes();
        filesToDeleted.parallelStream().forEach(fileToken -> {
            File file = new File(fileToken.getFilePath());
            if (file.exists()){
                deleteFile(file);
                log.info("Cleaned up file, name={}, path={}", file.getName(), file.getAbsolutePath());
                cleanCounter.getAndIncrement();
                fileDownloadService.delete(fileToken.getToken());
            }
        });
        log.info("Complete clean up file, {} was deleted", cleanCounter.get());
    }

    private void deleteFile(File file) {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            log.error("Unable to delete file, filename: {}, error: {}", file.getName(), e.getMessage());
        }
    }
}
