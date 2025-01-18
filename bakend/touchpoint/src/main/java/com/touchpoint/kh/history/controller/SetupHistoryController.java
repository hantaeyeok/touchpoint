package com.touchpoint.kh.history.controller;

import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.service.SetupHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/history")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class SetupHistoryController {

    @Autowired
    private SetupHistoryService setupHistoryService;

    /*
    // 생성자 주입 (현재 미사용)
    private final SetupHistoryService setupHistoryService;

    public SetupHistoryController(SetupHistoryService setupHistoryService) {
        this.setupHistoryService = setupHistoryService;
    }
    */

    // 기존 GET 메서드: 설치 이력 조회
    @GetMapping
    public List<SetupHistory> getSetupHistory() {
        return setupHistoryService.getSetupHistory();
    }

    // POST 메서드: 설치 이력 데이터 추가
    @PostMapping("/add")
    public String addSetupHistory(
            @RequestParam("storeName") String storeName,
            @RequestParam("storeAddress") String storeAddress,
            @RequestParam("modelName") String modelName,
            @RequestParam("historyContent") String historyContent,
            @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestParam(value = "subImages", required = false) List<MultipartFile> subImages) {

        // 로그로 입력 데이터 확인
        log.info("Store Name: {}", storeName);
        log.info("Store Address: {}", storeAddress);
        log.info("Model Name: {}", modelName);
        log.info("History Content: {}", historyContent);

        // 메인이미지 로그 출력
        if (mainImage != null) {
            log.info("Main Image: {}", mainImage.getOriginalFilename());
        } else {
            log.info("No Main Image provided.");
        }

        // 서브이미지 로그 출력
        if (subImages != null && !subImages.isEmpty()) {
            subImages.forEach(image -> log.info("Sub Image: {}", image.getOriginalFilename()));
        } else {
            log.info("No Sub Images provided.");
        }

        // 서비스 계층으로 데이터 전달 (예: DB 저장)
        // setupHistoryService.saveSetupHistory(storeName, storeAddress, modelName, historyContent, mainImage, subImages);

        return "Data received successfully";
    }
}
