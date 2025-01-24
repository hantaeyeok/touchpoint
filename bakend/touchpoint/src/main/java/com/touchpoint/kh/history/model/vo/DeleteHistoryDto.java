	package com.touchpoint.kh.history.model.vo;
	
	import lombok.AllArgsConstructor;
	import lombok.Builder;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	import java.time.LocalDate;
	import java.util.List;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class DeleteHistoryDto {
	
		 private List<Integer> historyNo; //체크박스로 받아온 HistoryNo 리스트
	}
