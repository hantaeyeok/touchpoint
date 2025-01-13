package com.touchpoint.kh.history.contrtoller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.history.model.service.SetupHistoryService;
import com.touchpoint.kh.history.model.vo.SetupHistory;

@RestController
@RequestMapping("api/history")
@CrossOrigin(origins = "http://localhost:3000")
public class HistoryController {

    private final SetupHistoryService setupHistoryService;

    public HistoryController(SetupHistoryService setupHistoryService) {
        this.setupHistoryService = setupHistoryService;
    }

    @GetMapping
    public List<SetupHistory> getSetupHistory() {
        return setupHistoryService.getSetupHistory();
    }
}

