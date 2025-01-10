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
public class SetupHistory {

    private int historyNo;          // 게시글 번호
    private String storeName;       // 매장명
    private String storeAddress;    // 매장 주소
    private String modelName;       // 모델명
    private String historyContent;  // 내용
    private LocalDate historyDate;  // 등록일자
    private String userId;          // 게시글 작성자
    private String mainImage;       // 메인이미지
    private List<HistoryImage> subImages; // 서브이미지 리스트
}

