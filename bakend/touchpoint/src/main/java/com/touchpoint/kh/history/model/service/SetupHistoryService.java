package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.vo.DetailHistoryDto;
import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;
import com.touchpoint.kh.history.model.vo.UpdateHistoryDto;

import java.util.List;

public interface SetupHistoryService {

	//전체 설치사례 불러오기
    List<SetupHistory> getSetupHistory();
    //게시글 등록
	int insertSetupHistory(SetupHistoryDto setupHistoryDto);
	//게시글삭제
	int deleteHistory(List<Integer> historyNos);
	//게시글 상세보기
	DetailHistoryDto detailHistoryById(int historyNo);
	//게시글 수정
	int updateSetupHistory(UpdateHistoryDto updateHistoryDto);
}

