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
public class UpdateHistoryImageDto {

    // 새로 추가할 이미지 파일 리스트 + 순서 정보 (JSON으로 관리)
    private List<MultipartFile> addedImages;
    private List<UpdatedImageDto> addedImageInfo;  // 추가된 이미지 정보 (오더 + 파일명)

    // 수정할 이미지 파일 리스트 + 순서 정보 (JSON으로 관리)
    private List<MultipartFile> updatedImages;
    private List<UpdatedImageDto> updatedImageInfo;  // 수정된 이미지 정보 (오더 + 파일명)

    // 삭제할 이미지 리스트 (파일이 아니라 메타데이터만 포함)
    private List<HistoryImage> deleteImages;
}
