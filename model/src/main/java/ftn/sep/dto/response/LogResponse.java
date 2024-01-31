package ftn.sep.dto.response;

import ftn.sep.db.Log;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LogResponse {

    private LocalDateTime dateTime;

    private LogLevel logLevel;

    private String loggerName;

    private String logMessage;

    public LogResponse(Log log){
        this.dateTime = log.getDateTime();
        this.logLevel = log.getLogLevel();
        this.loggerName = log.getLoggerName();
        this.logMessage = log.getLogMessage();
    }
}
