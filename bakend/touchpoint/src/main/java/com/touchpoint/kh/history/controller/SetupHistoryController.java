package com.touchpoint.kh.history.controller;

import com.touchpoint.kh.history.model.vo.DeleteHistoryDto;
import com.touchpoint.kh.history.model.vo.DetailHistoryDto;
import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;
import com.touchpoint.kh.history.model.vo.UpdateHistoryDto;


import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.common.message.HistoryMessage;
import com.touchpoint.kh.history.model.service.SetupHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
@Slf4j 
public class SetupHistoryController {

    @Autowired
    private SetupHistoryService setupHistoryService;
    @Autowired
    private ResponseHandler responseHandler;
    
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
        List<SetupHistory> setupHistories = setupHistoryService.getSetupHistory();
        log.info("setupHistory 로그찍기: {}", setupHistories);
        return setupHistories;
    }

    // POST 메서드: 설치 이력 데이터 추가
    @PostMapping("/add")
    public ResponseEntity<ResponseData> addSetupHistory(
            @RequestPart("data") SetupHistoryDto setupHistoryDto,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage, 
            @RequestPart(value = "subImages", required = false) List<MultipartFile> subImages) { 
    	
        try {
        	 log.info("Received SetupHistoryDto: {}", setupHistoryDto);
             log.info("Main Image: {}", mainImage != null ? mainImage.getOriginalFilename() : "No main image provided");
             if (subImages != null && !subImages.isEmpty()) {
                 for (int i = 0; i < subImages.size(); i++) {
                     log.info("Sub Image [{}]: {}", i, subImages.get(i).getOriginalFilename());
                 }
             } else {
                 log.info("No sub images provided");
             }
        	
        	
            // DTO에 파일 데이터 추가 파일데이터는 MultipartFile 객체로 자동으로 Dto에 매핑되지 않음
            setupHistoryDto.setMainImage(mainImage);
            setupHistoryDto.setSubImages(subImages);

            log.info("DTO: {}", setupHistoryDto);

            // 서비스 계층 호출
            int result = setupHistoryService.insertSetupHistory(setupHistoryDto);

            if (result == 1) {
                // 성공 응답
                return responseHandler.createResponse(
                    HistoryMessage.HISTORY_INSERT_SUCCESS, true, HttpStatus.OK);
            } else {
                // 실패 응답
                return responseHandler.createResponse(
                    HistoryMessage.GENERAL_FAILURE, false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // 예외 발생 처리
            return responseHandler.handleException(HistoryMessage.SERVER_ERROR, e);
        }
    }
    
    // POST 메서드: 데이터 삭제
    @PostMapping("/delete")
    public ResponseEntity<ResponseData> deleteHistory(@RequestBody DeleteHistoryDto deleteHistoryDto) {
        List<Integer> historyNos = deleteHistoryDto.getHistoryNo();
        
        log.info("historyNos나오냐:{}",historyNos);
        // 서비스 호출
        int result = setupHistoryService.deleteHistory(historyNos);

        if (result > 0) {
            return responseHandler.createResponse(
                HistoryMessage.HISTORY_DELETE_SUCCESS,
                true,
                HttpStatus.OK
            );
        } else {
            return responseHandler.createResponse(
                HistoryMessage.HISTORY_DELETE_NULL,
                null,
                HttpStatus.OK
            );
        }
    }
    
    //Get 메서드:데이터 상새보기
    @GetMapping("/{historyNo}")
    public ResponseEntity<ResponseData> getDetailSetupHistory(@PathVariable("historyNo") int historyNo) {
        log.info("사용자로부터 받아온 게시글번호: {}", historyNo);

        // 서비스 호출
        DetailHistoryDto detailHistory = setupHistoryService.detailHistoryById(historyNo);

        if (detailHistory != null) {
            // 상세보기 성공
            return responseHandler.createResponse(
                HistoryMessage.DETAIL_HISTORY_SUCCESS,
                detailHistory,
                HttpStatus.OK
            );	
        } else {
            // 데이터가 없을 경우
            return responseHandler.createResponse(
                HistoryMessage.DETAIL_HISTORY_FAILURE,
                false,
                HttpStatus.NOT_FOUND
            );
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseData> updateSetupHistory(
            @RequestPart("data") UpdateHistoryDto updateHistoryDto, // UpdateHistoryDto 사용
            @RequestPart(value = "newMainImage", required = false) MultipartFile newMainImage,
            @RequestPart(value = "newSubImages", required = false) List<MultipartFile> newSubImages,
            @RequestPart(value = "deleteSubImages", required = false) List<String> deleteSubImages) {

        try {
            // 로그 확인
            if (newMainImage != null) {
                log.info("New Main Image: {}", newMainImage.getOriginalFilename());
            } else {
                log.info("No new main image provided");
            }

            if (newSubImages != null && !newSubImages.isEmpty()) {
                log.info("New Sub Images:");
                newSubImages.forEach(image -> log.info(" - {}", image.getOriginalFilename()));
            } else {
                log.info("No new sub images provided");
            }

            if (deleteSubImages != null && !deleteSubImages.isEmpty()) {
                log.info("Delete Sub Images:");
                deleteSubImages.forEach(image -> log.info(" - {}", image));
            } else {
                log.info("No delete sub images provided");
            }

            // DTO에 파일 데이터와 삭제 이미지 리스트 설정
            updateHistoryDto.setNewMainImage(newMainImage);
            updateHistoryDto.setNewSubImages(newSubImages);
            updateHistoryDto.setDeleteSubImages(deleteSubImages);

            // 서비스 계층 호출
            int result = setupHistoryService.updateSetupHistory(updateHistoryDto);

            if (result == 1) {
                // 성공 응답
                return responseHandler.createResponse(
                    HistoryMessage.HISTORY_UPDATE_SUCCESS, true, HttpStatus.OK);
            } else {
                // 실패 응답
                return responseHandler.createResponse(
                    HistoryMessage.HISTORY_UPDATE_FAILURE, false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // 예외 발생 처리
            log.error("Exception during update: ", e);
            return responseHandler.handleException(HistoryMessage.SERVER_ERROR, e);
        }
    }


}
