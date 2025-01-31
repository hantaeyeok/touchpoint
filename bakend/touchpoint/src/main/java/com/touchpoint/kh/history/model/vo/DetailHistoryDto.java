package com.touchpoint.kh.history.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailHistoryDto {
    // 게시글 정보
    private SetupHistory setupHistory;

    // 이미지 정보 (HistoryImage 테이블의 필드 리스트)
    private List<HistoryImage> images;
}
