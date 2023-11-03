package com.ynov.boilerplate.config.autoincrement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;


@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int getNextSequence(String user_sequence) {
        Query query = new Query(Criteria.where("id").is(user_sequence));
        Update update = new Update().inc("seq", 1);
        SequenceId sequence = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        SequenceId.class);
        return !Objects.isNull(sequence) ? sequence.getSeq() : 1;
    }
}
