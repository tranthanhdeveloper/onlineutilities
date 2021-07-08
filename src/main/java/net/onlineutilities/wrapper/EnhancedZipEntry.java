package net.onlineutilities.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.zip.ZipEntry;

@Getter
@AllArgsConstructor
public class EnhancedZipEntry {
    private ZipEntry zipEntry;
    private byte[] bytes;
}
