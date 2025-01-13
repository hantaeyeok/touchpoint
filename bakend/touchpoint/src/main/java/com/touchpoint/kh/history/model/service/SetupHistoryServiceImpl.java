package com.touchpoint.kh.history.model.service;

import com.touchpoint.kh.history.model.dao.SetupHistoryMapper;
import com.touchpoint.kh.history.model.vo.SetupHistory;
import com.touchpoint.kh.history.model.service.SetupHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetupHistoryServiceImpl implements SetupHistoryService {

    private final SetupHistoryMapper setupHistoryMapper;

    public SetupHistoryServiceImpl(SetupHistoryMapper setupHistoryMapper) {
        this.setupHistoryMapper = setupHistoryMapper;
    }

    @Override
    public List<SetupHistory> getSetupHistory() {
        return setupHistoryMapper.selectSetupHistory();
    }
}
