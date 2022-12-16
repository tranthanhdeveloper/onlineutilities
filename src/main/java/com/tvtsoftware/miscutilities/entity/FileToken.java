package com.tvtsoftware.miscutilities.entity;

import jakarta.persistence.Column;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "creation_date")
    private Timestamp creationDate;

}
