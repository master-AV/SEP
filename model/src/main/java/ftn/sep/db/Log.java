package ftn.sep.db;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.logging.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Document(collection = "logs")
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private LocalDateTime dateTime;

    private LogLevel logLevel;

    private String loggerName;

    private String logMessage;

    public Log(LocalDateTime dateTime, LogLevel logLevel, String loggerName, String logMessage) {
        this.dateTime = dateTime;
        this.logLevel = logLevel;
        this.loggerName = loggerName;
        this.logMessage = logMessage;
    }
}
