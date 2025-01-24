package com.touchpoint.kh.common.message;

public class HistoryMessage {
	
	public static final String HISTORY_INSERT_SUCCESS = "히스토리 등록 성공";
	public static final String GENERAL_FAILURE = "요청 처리 중 오류가 발생했습니다.";
    public static final String SERVER_ERROR = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
    
    //삭제
    public static final String HISTORY_DELETE_SUCCESS = "설치사례 삭제 성공";
    public static final String HISTORY_DELETE_FAILURE = "설치사례 삭제 실패";
    public static final String HISTORY_DELETE_NULL = "삭제할 설치사례가 없습니다";
    
    //상세보기
    public static final String DETAIL_HISTORY_SUCCESS = "상세보기 성공";
    public static final String DETAIL_HISTORY_FAILURE = "상세보기 실패";
    //게시글수정
	public static final String HISTORY_UPDATE_SUCCESS = "업데이트에 성공";
	public static final String HISTORY_UPDATE_FAILURE = "업데이트 실패";
}
