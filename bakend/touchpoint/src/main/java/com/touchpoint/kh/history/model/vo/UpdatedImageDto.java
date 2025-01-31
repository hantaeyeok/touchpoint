package com.touchpoint.kh.history.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatedImageDto {
    private int imageOrder; // 이미지 순서
    private String fileName; // 프론트에서 보낸 파일 이름 (매칭용)
}
