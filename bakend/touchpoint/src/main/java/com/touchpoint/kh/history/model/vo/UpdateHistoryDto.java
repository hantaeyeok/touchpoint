package com.touchpoint.kh.history.model.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateHistoryDto {

    private int historyNo; // 수정할 게시글 번호
    private String storeName; // 매장명
    private String storeAddress; // 매장 주소
    private String modelName; // 모델명
    private String historyContent; // 게시글 내용

}
