package com.touchpoint.kh.history.model.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHistoryDto {

    private int historyNo; // 수정할 게시글 번호
    private String storeName; // 매장명
    private String storeAddress; // 매장 주소
    private String modelName; // 모델명
    private String historyContent; // 게시글 내용

    private MultipartFile newMainImage; // 새로 선택된 메인 이미지 (optional)
    private List<MultipartFile> newSubImages; // 새로 선택된 서브 이미지들 (optional)

    private List<String> deleteSubImages; // 삭제할 서브 이미지의 파일 이름 리스트
    private List<String> existingImages; // 수정 후에도 유지할 기존 이미지의 파일 이름 리스트

}
