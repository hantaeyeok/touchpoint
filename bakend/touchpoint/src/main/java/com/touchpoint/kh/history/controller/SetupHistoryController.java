package com.touchpoint.kh.history.controller;

import com.touchpoint.kh.history.model.vo.DeleteHistoryDto;
import com.touchpoint.kh.history.model.vo.DetailHistoryDto;
import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;
import com.touchpoint.kh.history.model.vo.UpdateHistoryDto;
import com.touchpoint.kh.history.model.vo.UpdateHistoryImageDto;
import com.touchpoint.kh.history.model.vo.UpdatedImageDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@CrossOrigin(origins = "http://localhost:3000")
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
    @GetMapping("/detail/{historyNo}")
    public ResponseEntity<ResponseData> getDetailSetupHistory(@PathVariable("historyNo") int historyNo) {

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
            @RequestPart("data") UpdateHistoryDto updateHistoryDto,  // 게시글 데이터 DTO
            @RequestPart(value = "updatedImages", required = false) List<MultipartFile> updatedImages, // 수정된 파일
            @RequestPart(value = "updatedImageInfo", required = false) String updatedImageInfoJson, // 수정된 이미지 정보 JSON
            @RequestPart(value = "addedImages", required = false) List<MultipartFile> addedImages, // 추가된 파일
            @RequestPart(value = "addedImageInfo", required = false) String addedImageInfoJson // 추가된 이미지 정보 JSON
    ) {
        try {
            log.info("===== UPDATE 요청 도착=====");
            log.info("DTO: {}", updateHistoryDto);
            log.info("수정된 파일 개수: {}", (updatedImages != null) ? updatedImages.size() : "없음");
            log.info("수정된 이미지 정보 (JSON): {}", updatedImageInfoJson);
            log.info("추가된 파일 개수: {}", (addedImages != null) ? addedImages.size() : "없음");
            log.info("추가된 이미지 정보 (JSON): {}", addedImageInfoJson);

            ObjectMapper objectMapper = new ObjectMapper();

            // 🚀 수정된 이미지 정보 변환
            List<UpdatedImageDto> updatedImageInfo = objectMapper.readValue(
                    updatedImageInfoJson, new TypeReference<List<UpdatedImageDto>>() {});

            // 🚀 추가된 이미지 정보 변환
            List<UpdatedImageDto> addedImageInfo = objectMapper.readValue(
                    addedImageInfoJson, new TypeReference<List<UpdatedImageDto>>() {});

            // 🚀 이미지 데이터 매핑
            UpdateHistoryImageDto updateHistoryImageDto = UpdateHistoryImageDto.builder()
                    .updatedImages(updatedImages)
                    .updatedImageInfo(updatedImageInfo)
                    .addedImages(addedImages) // ✅ 추가된 이미지 추가
                    .addedImageInfo(addedImageInfo) // ✅ 추가된 이미지 정보 추가
                    .build();

            if (updateHistoryDto == null || updateHistoryImageDto == null) {
                log.error("요청 데이터가 null입니다!");
                return responseHandler.createResponse("잘못된 요청 데이터입니다.", false, HttpStatus.BAD_REQUEST);
            }

            // 서비스 호출
            int result = setupHistoryService.updateSetupHistory(updateHistoryDto, updateHistoryImageDto);

            if (result == 1) {
                return responseHandler.createResponse(
                        HistoryMessage.HISTORY_UPDATE_SUCCESS, true, HttpStatus.OK);
            } else {
                return responseHandler.createResponse(
                        HistoryMessage.HISTORY_UPDATE_FAILURE, false, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception during update: ", e);
            return responseHandler.handleException(HistoryMessage.SERVER_ERROR, e);
        }
    }




}
