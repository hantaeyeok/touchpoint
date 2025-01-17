package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.vo.HistoryImage;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.vo.SetupHistoryDto;

import java.util.List;

public interface SetupHistoryService {

	//전체 설치사례 불러오기
    List<SetupHistory> getSetupHistory();
    //게시글 등록
	int insertSetupHistory(SetupHistoryDto setupHistoryDto);
}

