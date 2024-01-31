package ftn.sep.webshop.repository;

import ftn.sep.db.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<Log, Long> {
    List<Log> findAllByDateTimeNotNullOrderByDateTimeDesc();
}
