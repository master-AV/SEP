package ftn.sep.webshop.service.interfaces;

import ftn.sep.dto.response.LogResponse;
import org.springframework.boot.logging.LogLevel;

import java.util.List;

public interface ILogService {
    void generateLog(String logMessage, LogLevel logLevel);

    List<LogResponse> getAllLogs();

}
