package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.dao.SetupHistoryMapper;
import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;

import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetupHistoryServiceImpl implements SetupHistoryService {

    private final SetupHistoryMapper setupHistoryMapper;
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
                    .userId("admin") // 예제에서 고정된 userId 사용
                    .build();

            // 2. 설치 사례 저장
            int result = setupHistoryMapper.insertSetupHistory(setupHistory);
            if (result <= 0) {
                throw new RuntimeException("설치 사례 저장 실패");
            }

            // 3. 이미지 처리 및 저장
            List<HistoryImage> images = insertImage(setupHistoryDto, setupHistory.getHistoryNo());
            for (HistoryImage image : images) {
                setupHistoryMapper.insertHistoryImage(image);
            }

            return result; // 성공 시 1 반환

        } catch (Exception e) {
            throw new RuntimeException("서비스 처리 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // 이미지 처리 메서드
    private List<HistoryImage> insertImage(SetupHistoryDto setupHistoryDto, int historyNo) {
        List<HistoryImage> images = new ArrayList<>();
        int order = 0;

        // 메인 이미지 처리
        if (setupHistoryDto.getMainImage() != null && !setupHistoryDto.getMainImage().isEmpty()) {
            String mainImagePath = saveFile(setupHistoryDto.getMainImage());
            images.add(HistoryImage.builder()
                    .historyNo(historyNo)
                    .historyImageName(mainImagePath)
                    .historyImageOrder(order++)
                    .build());
        }

        // 서브 이미지 처리
        if (setupHistoryDto.getSubImages() != null && !setupHistoryDto.getSubImages().isEmpty()) {
            for (MultipartFile subImage : setupHistoryDto.getSubImages()) {
                if (!subImage.isEmpty()) {
                    String subImagePath = saveFile(subImage);
                    images.add(HistoryImage.builder()
                            .historyNo(historyNo)
                            .historyImageName(subImagePath)
                            .historyImageOrder(order++)
                            .build());
                }
            }
        }

        return images;
    }

    // 파일 저장 메서드
    private String saveFile(MultipartFile upfile) {
    	
        String uploadDir = "src/main/resources/static/uploadHistoryFiles/";
        String fileName = upfile.getOriginalFilename();

        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 유효하지 않습니다.");
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "_" + (int) (Math.random() * 900 + 100) + ext;

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            upfile.transferTo(new File(uploadDir + newFileName));
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
        }

        return "/uploadHistoryFiles/" + newFileName;
    }
}
