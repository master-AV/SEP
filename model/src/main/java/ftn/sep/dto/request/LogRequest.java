package ftn.sep.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {
    private String logMessage;
    private LogLevel logLevel;
}
