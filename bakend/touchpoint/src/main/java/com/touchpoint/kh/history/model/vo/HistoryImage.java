package com.touchpoint.kh.history.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryImage {

    private int imageNo;              // 이미지 번호
    private int historyNo;            // 게시글 번호 (외래 키)
    private String historyImageName;  // 이미지 파일명
    private int historyImageOrder;    // 이미지 순서
}
