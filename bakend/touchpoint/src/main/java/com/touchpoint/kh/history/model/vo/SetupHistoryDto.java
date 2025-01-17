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
public class SetupHistoryDto {
	 private String storeName;
	    private String storeAddress;
	    private String modelName;
	    private String historyContent;
	    private MultipartFile mainImage; // 메인 이미지
	    private List<MultipartFile> subImages; // 서브 이미지 리스트
}
