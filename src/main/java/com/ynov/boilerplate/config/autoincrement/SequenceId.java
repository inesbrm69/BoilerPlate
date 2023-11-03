package com.ynov.boilerplate.config.autoincrement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "counters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceId {
    @Id
    private String id;
    private int seq;
}