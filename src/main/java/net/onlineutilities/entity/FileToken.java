package net.onlineutilities.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Builder(toBuilder = true, setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "file_token")
public class FileToken {

    @Id
    private String token;

    private String fileName;

    private String filePath;

    private Timestamp creationDate;

}
