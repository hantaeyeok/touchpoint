package com.touchpoint.kh.history.model.dao;

import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
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
	SetupHistory detailHistory(@Param("historyNo")int historyNo);
	List<HistoryImage> detailHistoryImage(int historyNo);
	
	//게시글삭제
	int deleteHistoryImage(@Param("historyNos") List<Integer> historyNos); // @Param 추가
    int deleteHistory(@Param("historyNos") List<Integer> historyNos); // @Param 추가
		
	

}
