package com.touchpoint.kh.history.model.dao;

import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.UpdateHistoryDto;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SetupHistoryMapper {

    // 게시글 전체 조회 (이미지 포함)
    List<SetupHistory> selectSetupHistory();
    
    //게시글추가 
    int insertSetupHistory(SetupHistory setupHistory);

	int insertHistoryImage(HistoryImage HistoryImage);
	
	//게시글 상세조회하기
	SetupHistory detailHistory(int historyNo);
	List<HistoryImage> detailHistoryImage(int historyNo);
	
	//게시글삭제
	int deleteHistoryImage(@Param("historyNos") List<Integer> historyNos); // @Param 추가
    int deleteHistory(@Param("historyNos") List<Integer> historyNos); // @Param 추가
    
    //게시글수정
    // 1. 게시글 기본 정보 업데이트
    int updateHistory(UpdateHistoryDto updateHistoryDto);

    // 2. 삭제 이미지 처리
    int updateDeleteImages(@Param("deleteImages") List<HistoryImage> deleteImages);

    // 3. 새로운 이미지 추가 처리
    int updateAddNewImage(HistoryImage newImage);

    // 4. 수정 이미지 처리
    int updateUpdateHistoryImage(HistoryImage updatedImage);

	int updateInsertHistoryImage(HistoryImage newImage);

	

}
