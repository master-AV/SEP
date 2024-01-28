package ftn.sep.webshop.controller;


import ftn.sep.dto.request.LogRequest;
import ftn.sep.dto.response.LogResponse;
import ftn.sep.webshop.service.interfaces.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("log")
public class LogController {
    @Autowired
    private ILogService logService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAuthority('READ_LOGS')")
    public List<LogResponse> getAllLogs(){
        return logService.getAllLogs();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void generateLog(@RequestBody LogRequest logRequest){
        logService.generateLog(logRequest.getLogMessage(), logRequest.getLogLevel());
    }

}
