package com.touchpoint.kh.history.controller;

import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.service.SetupHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class SetupHistoryController {

    
    @Autowired
    private SetupHistoryService setupHistoryService;

    /*
    // 생성자 주입안쓸래,,
    private final SetupHistoryService setupHistoryService;

    public SetupHistoryController(SetupHistoryService setupHistoryService) {
        this.setupHistoryService = setupHistoryService;
    }
    */

    @GetMapping
    public List<SetupHistory> getSetupHistory() {
        return setupHistoryService.getSetupHistory();
        
        

    
    }
}    
