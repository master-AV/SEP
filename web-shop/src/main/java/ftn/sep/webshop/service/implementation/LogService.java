package ftn.sep.webshop.service.implementation;

import ftn.sep.db.Log;
import ftn.sep.dto.response.LogResponse;
import ftn.sep.webshop.repository.LogRepository;
import ftn.sep.webshop.service.interfaces.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService implements ILogService {

    @Autowired
    private LogRepository logRepository;
    @Override
    public void generateLog(String logMessage, LogLevel logLevel) {
        String loggerName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log log = new Log(LocalDateTime.now(), logLevel, loggerName, logMessage);
        logRepository.save(log);
    }

    @Override
    public List<LogResponse> getAllLogs() {
        return logRepository.findAllByDateTimeNotNullOrderByDateTimeDesc()
                .stream().map(LogResponse::new).collect(Collectors.toList());
    }
}
