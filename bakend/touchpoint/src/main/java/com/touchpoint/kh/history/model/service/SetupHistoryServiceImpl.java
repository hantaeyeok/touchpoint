package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.dao.SetupHistoryMapper;
import com.touchpoint.kh.history.model.vo.DetailHistoryDto;
import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;
import com.touchpoint.kh.history.model.vo.UpdateHistoryDto;
import com.touchpoint.kh.history.model.vo.UpdateHistoryImageDto;
import com.touchpoint.kh.history.model.vo.UpdatedImageDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.servlet.http.HttpServlet;
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

    @Override
    @Transactional //매서드 처리로직에서도 작동가능
    public int deleteHistory(List<Integer> historyNos) {
        // 1. 이미지 삭제
        int deletedImages = setupHistoryMapper.deleteHistoryImage(historyNos);
        if (deletedImages == 0) {
            throw new RuntimeException("이미지 삭제에 실패했습니다.");
        }
        
        // 2. 히스토리 삭제
        int deletedHistories = setupHistoryMapper.deleteHistory(historyNos);
        if (deletedHistories == 0) {
            throw new RuntimeException("히스토리 삭제에 실패했습니다.");
        }

        return deletedHistories; // 삭제된 히스토리 수 반환
    }
    //게시글 상세조회
	@Override
	public DetailHistoryDto detailHistoryById(int historyNo) {
        // 기본 이력 정보 조회
        SetupHistory detailHistory = setupHistoryMapper.detailHistory(historyNo);
        if (detailHistory == null) {
            return null; // 해당 이력 정보가 없을 경우 null 반환
        }
        // 연관된 이미지 리스트 조회
        List<HistoryImage> detailImages = setupHistoryMapper.detailHistoryImage(historyNo);
        // 조회된 데이터를 DetailHistoryDto로 매핑하여 반환
        return DetailHistoryDto.builder()
                .setupHistory(detailHistory) // SetupHistory 객체 전체 전달
                .images(detailImages)      // 이미지 리스트 설정
                .build();
    }
	@Override
	@Transactional
	public int updateSetupHistory(UpdateHistoryDto updateHistoryDto, UpdateHistoryImageDto updateHistoryImageDto) {
	    try {
	        // 📌 1. 게시글 기본 정보 업데이트
	        int updateResult = setupHistoryMapper.updateHistory(updateHistoryDto);
	        if (updateResult == 0) {
	            throw new RuntimeException("🚨 게시글 정보 업데이트 실패");
	        }

	        // 📌 2. 이미지 수정 처리 (updatedImages)
	        List<MultipartFile> updatedImages = updateHistoryImageDto.getUpdatedImages();
	        List<UpdatedImageDto> updatedImageInfo = updateHistoryImageDto.getUpdatedImageInfo();

	        if (updatedImages != null && !updatedImages.isEmpty()) {
	            for (int i = 0; i < updatedImages.size(); i++) {
	                try {
	                    MultipartFile imageFile = updatedImages.get(i);
	                    UpdatedImageDto imageInfo = updatedImageInfo.get(i);

	                    // 파일 저장 (새 이미지 저장)
	                    String savedPath = saveFile(imageFile);

	                    // DB 업데이트 (기존 이미지의 같은 위치에 새로운 이미지 업데이트)
	                    HistoryImage updatedImage = HistoryImage.builder()
	                            .historyNo(updateHistoryDto.getHistoryNo())
	                            .historyImageOrder(imageInfo.getImageOrder()) // 기존 이미지 순서 유지 
	                            .historyImageName(savedPath) // 새 파일 경로   
	                            .build();

	                    log.info("업데이트된 이미지 - historyNo: {}, imageOrder: {}, imageName: {}",
	                            updatedImage.getHistoryNo(), updatedImage.getHistoryImageOrder(), updatedImage.getHistoryImageName());

	                    setupHistoryMapper.updateUpdateHistoryImage(updatedImage);
	                } catch (IOException e) {
	                    throw new RuntimeException("🚨 이미지 업데이트 실패", e);
	                }
	            }
	        }

	        // 📌 3. 추가된 이미지 처리 (addedImages)
	        List<MultipartFile> addedImages = updateHistoryImageDto.getAddedImages();
	        List<UpdatedImageDto> addedImageInfo = updateHistoryImageDto.getAddedImageInfo();

	        if (addedImages != null && !addedImages.isEmpty()) {
	            for (int i = 0; i < addedImages.size(); i++) {
	                try {
	                    MultipartFile imageFile = addedImages.get(i);
	                    UpdatedImageDto imageInfo = addedImageInfo.get(i);

	                    // 파일 저장 (새로운 파일 저장)
	                    String savedPath = saveFile(imageFile);

	                    // DB에 새 이미지 추가
	                    HistoryImage newImage = HistoryImage.builder()
	                            .historyNo(updateHistoryDto.getHistoryNo()) // 기존 게시글 번호
	                            .historyImageOrder(imageInfo.getImageOrder()) // 추가된 이미지 순서
	                            .historyImageName(savedPath) // 새 파일 경로
	                            .build();

	                    log.info("추가된 이미지 - historyNo: {}, imageOrder: {}, imageName: {}",
	                            newImage.getHistoryNo(), newImage.getHistoryImageOrder(), newImage.getHistoryImageName());

	                    setupHistoryMapper.updateInsertHistoryImage(newImage);
	                } catch (IOException e) {
	                    throw new RuntimeException("추가된 이미지 저장 실패", e);
	                }
	            }
	        }

	        log.info("모든 업데이트 완료! 정상적으로 return 1 실행");
	        return 1; // 성공적으로 업데이트 완료

	    } catch (Exception e) {
	        throw new RuntimeException("🚨 게시글 수정 중 오류 발생", e);
	    }
	}








}

