package com.touchpoint.kh.history.model.dao;

import com.touchpoint.kh.history.model.vo.SetupHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetupHistoryMapper {

    // 게시글 전체 조회 (이미지 포함)
    List<SetupHistory> selectAllSetupHistory();

}
