package com.touchpoint.kh.history.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailHistoryDto {
    private int historyNo;          // 게시글 번호
    private String storeName;       // 매장명
    private String storeAddress;    // 매장 주소
    private String modelName;       // 모델명
    private String historyContent;  // 내용
    private LocalDate historyDate;  // 등록일자
    private String userId;          // 게시글 작성자

    private List<HistoryImage> images; // 연관된 이미지 리스트
}
