package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.vo.SetupHistory;
import java.util.List;

public interface SetupHistoryService {
    //전체 설치사례 불러오기
    List<SetupHistory> getSetupHistory();
}