package com.touchpoint.kh.history.contorller;
import lombok.RequiredArgsConstructor;
import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.history.model.service.SetupHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class SetupHistoryController {

    private final SetupHistoryService setupHistoryService;
    private final ResponseHandler responseHandler;
    
    @GetMapping
    public ResponseEntity<ResponseData> getSetupHistory() {
    	return null;
    }
}