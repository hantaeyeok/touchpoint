package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.dao.SetupHistoryMapper;
import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetupHistoryServiceImpl implements SetupHistoryService {

    private final SetupHistoryMapper setupHistoryMapper;
    private final HttpServletRequest request;
    
    // 전체 설치사례 게시글 보기 
    @Override
    public List<SetupHistory> getSetupHistory() {
        return setupHistoryMapper.selectSetupHistory();
    }

    // 설치 사례 및 이미지 저장
    @Override
    public int insertSetupHistory(SetupHistoryDto setupHistoryDto) {
        try {	
            // 1. DTO -> VO 변환
            SetupHistory setupHistory = SetupHistory.builder()
                    .storeName(setupHistoryDto.getStoreName())
                    .storeAddress(setupHistoryDto.getStoreAddress())
                    .modelName(setupHistoryDto.getModelName())
                    .historyContent(setupHistoryDto.getHistoryContent())
                    .userId("admin") // 고정된 userId
                    .build();

            // 2. 설치 사례 저장
            int result = setupHistoryMapper.insertSetupHistory(setupHistory);
            if (result <= 0) {
                throw new RuntimeException("설치 사례 저장 실패");
            }

            // 3. 이미지 처리 및 저장
            List<HistoryImage> images = insertImage(setupHistoryDto, setupHistory.getHistoryNo());
            for (HistoryImage image : images) {
                setupHistoryMapper.insertHistoryImage(image); // DB에 이미지 삽입
            }

            return result; // 성공 시 1 반환
        } catch (Exception e) {
            log.error("서비스 처리 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("서비스 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // 이미지 처리 메서드
    private List<HistoryImage> insertImage(SetupHistoryDto setupHistoryDto, int historyNo) throws IOException {
        List<HistoryImage> historyImage = new ArrayList<>();
        int order = 0;

        // 메인 이미지 처리
        if (setupHistoryDto.getMainImage() != null && !setupHistoryDto.getMainImage().isEmpty()) {
            String mainImagePath = saveFile(setupHistoryDto.getMainImage());
            historyImage.add(HistoryImage.builder()
                    .historyNo(historyNo)
                    .historyImageName(mainImagePath) // 저장된 파일명 설정
                    .historyImageOrder(order++) // 순서 설정
                    .build());
            log.info("메인 이미지 저장 성공: {}", mainImagePath);
        }

        // 서브 이미지 처리
        if (setupHistoryDto.getSubImages() != null && !setupHistoryDto.getSubImages().isEmpty()) {
            for (MultipartFile subImage : setupHistoryDto.getSubImages()) {
                if (!subImage.isEmpty()) {
                    String subImagePath = saveFile(subImage);
                    historyImage.add(HistoryImage.builder()
                            .historyNo(historyNo)
                            .historyImageName(subImagePath) // 저장된 파일명 설정
                            .historyImageOrder(order++) // 순서 설정
                            .build());
                    log.info("서브 이미지 저장 성공: {}", subImagePath);
                }
            }
        }

        return historyImage;
    }

    // 파일 저장 메서드
    public String saveFile(MultipartFile upfile) throws IOException {
        // 업로드 경로 설정
    	
        String savePath = request.getServletContext().getRealPath("/resources/uploadHistoryFiles/");

        // null일 경우 기본 경로 사용
        if (savePath == null) {
            savePath = "/path/to/your/upload/directory/"; // 외부 디렉토리 설정 가능
        }

        // 디렉토리 생성
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        // 원본 파일명 가져오기
        String originalFileName = upfile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
        }

        // 확장자 추출 및 파일명 생성
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "_" + (int) (Math.random() * 900 + 100)
                + "_" + newName + ext;

        // 파일 저장 경로
        File file = new File(savePath + newFileName);
        
        // 파일 저장
        upfile.transferTo(file);
        // 저장된 파일명 반환
        return  "/resources/uploadHistoryFiles/"+newFileName; // 파일명만 반환
    }
}
